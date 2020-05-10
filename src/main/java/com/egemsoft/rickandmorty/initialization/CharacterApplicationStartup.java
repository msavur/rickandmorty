package com.egemsoft.rickandmorty.initialization;


import com.egemsoft.rickandmorty.convert.impl.RemoteCharacterConverter;
import com.egemsoft.rickandmorty.convert.impl.endpoint.ApiEndpoint;
import com.egemsoft.rickandmorty.entity.Character;
import com.egemsoft.rickandmorty.model.dto.CharacterDto;
import com.egemsoft.rickandmorty.model.response.GetAllCharacter;
import com.egemsoft.rickandmorty.repository.CharacterRepository;
import com.google.common.collect.MapDifference;
import com.google.common.collect.Maps;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class CharacterApplicationStartup implements ApplicationListener<ApplicationReadyEvent> {

    private final static int COUNT_PAGE = 2;

    private final CharacterRepository characterRepository;

    @Transactional
    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        initialInsertCharacterTable();
    }

    private void initialInsertCharacterTable() {
        RestTemplate restTemplate = new RestTemplate();
        List<CharacterDto> remoteCharacterDtos = new ArrayList<>();
        GetAllCharacter getAllCharacter = null;
        try {
            getAllCharacter = restTemplate.getForObject(ApiEndpoint.PAGEABLE_CHARACTER_URL + 1, GetAllCharacter.class);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return;
        }
        int pageSize = getAllCharacter.getInfo().getPages();
        remoteCharacterDtos.addAll(getAllCharacter.getResults());
        recursionCharacter(pageSize, COUNT_PAGE, remoteCharacterDtos);
        RemoteCharacterConverter remoteCharacterConverter = new RemoteCharacterConverter();
        List<Character> remoteCharacters = remoteCharacterConverter.convert(remoteCharacterDtos);
        List<Character> localCharacters = characterRepository.findAll();

        Map<Long, Character> localMap = toCharacterMap(localCharacters);
        Map<Long, Character> remoteMap = toCharacterMap(remoteCharacters);

        MapDifference<Long, Character> mapDifference = Maps.difference(localMap, remoteMap);
        Map<Long, Character> createMap = mapDifference.entriesOnlyOnRight();
        Map<Long, Character> deleteMap = mapDifference.entriesOnlyOnLeft();
        Map<Long, MapDifference.ValueDifference<Character>> updateMap = mapDifference.entriesDiffering();

        createCharacter(createMap.values());
        deleteCharacter(deleteMap.values());
        updateCharacter(updateMap.values());
    }

    private void createCharacter(Collection<Character> characters) {
        characterRepository.saveAll(characters);
    }

    private void deleteCharacter(Collection<Character> characters) {
        characterRepository.deleteInBatch(characters);
    }

    private void updateCharacter(Collection<MapDifference.ValueDifference<Character>> values) {
        List<Character> updates = values.stream()
                .map(valuesDifference -> {
                    Character localCharacter = valuesDifference.leftValue();
                    Character remoteCharacter = valuesDifference.rightValue();
                    localCharacter.setUrl(remoteCharacter.getUrl());
                    localCharacter.setName(remoteCharacter.getName());
                    localCharacter.setCreated(remoteCharacter.getCreated());
                    return localCharacter;
                })
                .collect(Collectors.toList());
        characterRepository.saveAll(updates);
    }

    private Map<Long, Character> toCharacterMap(List<Character> characters) {
        return characters.stream()
                .collect(Collectors.toMap(Character::getRemoteId, r -> r));
    }


    public int recursionCharacter(int pageSize, int pageCount, List<CharacterDto> remoteCharacterDtos) {
        RestTemplate restTemplate = new RestTemplate();
        if (pageSize >= pageCount) {
            GetAllCharacter getAllCharacter = restTemplate.getForObject(ApiEndpoint.PAGEABLE_CHARACTER_URL + pageCount, GetAllCharacter.class);
            remoteCharacterDtos.addAll(getAllCharacter.getResults());
            return recursionCharacter(pageSize, pageCount + 1, remoteCharacterDtos);
        }
        return pageSize;

    }
}