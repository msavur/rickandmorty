package com.egemsoft.rickandmorty.initialization;


import com.egemsoft.rickandmorty.convert.impl.RemoteCharacterConverter;
import com.egemsoft.rickandmorty.entity.Character;
import com.egemsoft.rickandmorty.entity.Episode;
import com.egemsoft.rickandmorty.initialization.common.CommonRestRequest;
import com.egemsoft.rickandmorty.model.dto.CharacterDto;
import com.egemsoft.rickandmorty.repository.CharacterRepository;
import com.google.common.collect.MapDifference;
import com.google.common.collect.Maps;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class CharacterApplicationStartup implements ApplicationListener<ApplicationReadyEvent> {

    private final CharacterRepository characterRepository;
    private final RemoteCharacterConverter remoteCharacterConverter;

    @Transactional
    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        initialInsertCharacterTable();
    }

    private void initialInsertCharacterTable() {
        List<Character> remoteCharacters = remoteCharacterConverter.convert(CommonRestRequest.getAllCharacter());
        List<Character> localCharacters = characterRepository.findAll();

        Map<Long, Character> localMap = toCharacterMap(localCharacters);
        Map<Long, Character> remoteMap = toCharacterMap(remoteCharacters);

        MapDifference<Long, Character> mapDifference = Maps.difference(localMap, remoteMap);
        Map<Long, Character> deleteMap = mapDifference.entriesOnlyOnLeft();

        if (CollectionUtils.isEmpty(deleteMap.values())){
            deleteCharacter(deleteMap.values());
        }



        List<CharacterDto> allCharacter = CommonRestRequest.getAllCharacter();

        // getRemoteEpisodes
        // link, list<episode>

        Map<Long, Character> createMap = mapDifference.entriesOnlyOnRight();
        Map<Long, MapDifference.ValueDifference<Character>> updateMap = mapDifference.entriesDiffering();

        createCharacter(createMap.values());
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

    private void setCharacterEpisodes(Character character, List<String> episodes) {
        for (String episode : episodes) {
            episode = episode.substring(episode.lastIndexOf("/") + 1);
            Episode ep = new Episode();
            // ep.setName();
            // ep.set
            ep.setCharacters(new HashSet<>());
            ep.getCharacters().add(character);
            character.getEpisodes().add(ep);
        }


    }

    private void getCharacter(Character character, Map<Long, Episode> characterMap, List<String> characterList) {
        Set<Episode> episodes = new HashSet<>();
        characterList.forEach(characterUrl->{
            characterUrl = characterUrl.substring(characterUrl.lastIndexOf("/") + 1);
            Episode episode = characterMap.get(characterUrl);
            //  episode
            episodes.add(episode);
        });
    }
}