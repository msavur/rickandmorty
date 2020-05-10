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

    private final CharacterRepository characterRepository;

    @Transactional
    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        initialInsertEpisode();
    }

    private void initialInsertEpisode() {
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
        recursionCharacter(pageSize, 1, remoteCharacterDtos);
        RemoteCharacterConverter remoteEpisodeConverter = new RemoteCharacterConverter();
        List<Character> remoteCharacters = remoteEpisodeConverter.convert(remoteCharacterDtos);
        List<Character> localCharacters = characterRepository.findAll();

        Map<Long, Character> localMap = toCharacterMap(localCharacters);
        Map<Long, Character> remoteMap = toCharacterMap(remoteCharacters);

        MapDifference<Long, Character> mapDifference = Maps.difference(localMap, remoteMap);
        Map<Long, Character> createMap = mapDifference.entriesOnlyOnRight();
        Map<Long, Character> deleteMap = mapDifference.entriesOnlyOnLeft();
        Map<Long, MapDifference.ValueDifference<Character>> updateMap = mapDifference.entriesDiffering();

        createEpisodes(createMap.values());
        deleteEpisodes(deleteMap.values());
        updateEpisodes(updateMap.values());
    }

    private void createEpisodes(Collection<Character> episodes) {
        characterRepository.saveAll(episodes);
    }

    private void deleteEpisodes(Collection<Character> episodes) {
        characterRepository.deleteInBatch(episodes);
    }

    private void updateEpisodes(Collection<MapDifference.ValueDifference<Character>> values) {
        List<Character> updates = values.stream()
                .map(valuesDifference -> {
                    Character localEpisode = valuesDifference.leftValue();
                    Character remoteEpisode = valuesDifference.rightValue();
                    localEpisode.setUrl(remoteEpisode.getUrl());
                    localEpisode.setName(remoteEpisode.getName());
                    localEpisode.setCreated(remoteEpisode.getCreated());
                    return localEpisode;
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