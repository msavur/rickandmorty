package com.egemsoft.rickandmorty.initialization.batchservice;


import com.egemsoft.rickandmorty.convert.impl.RemoteCharacterConverter;
import com.egemsoft.rickandmorty.entity.Character;
import com.egemsoft.rickandmorty.entity.Episode;
import com.egemsoft.rickandmorty.entity.Kind;
import com.egemsoft.rickandmorty.initialization.common.CommonRestRequest;
import com.egemsoft.rickandmorty.model.dto.CharacterDto;
import com.egemsoft.rickandmorty.repository.CharacterRepository;
import com.egemsoft.rickandmorty.repository.KindRepository;
import com.google.common.collect.MapDifference;
import com.google.common.collect.Maps;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class CharacterBatchService {

    private final CharacterRepository characterRepository;
    private final RemoteCharacterConverter remoteCharacterConverter;
    private final KindRepository kindRepository;

    public void execute() {
        List<Character> remoteCharacters = remoteCharacterConverter.convert(CommonRestRequest.getAllCharacter());
        List<Character> localCharacters = characterRepository.findAll();

        Map<String, Kind> kindMap = kindRepository.findAll().stream().collect(Collectors.toMap(k -> k.getName().toUpperCase(), k -> k));

        for (Character remoteCharacter : remoteCharacters) {
            Kind kind = kindMap.get(remoteCharacter.getKind().getName());
            remoteCharacter.setKind(kind);
        }

        Map<Long, Character> localMap = toCharacterMap(localCharacters);
        Map<Long, Character> remoteMap = toCharacterMap(remoteCharacters);

        MapDifference<Long, Character> mapDifference = Maps.difference(localMap, remoteMap);
        Map<Long, Character> deleteMap = mapDifference.entriesOnlyOnLeft();

        if (CollectionUtils.isEmpty(deleteMap.values())) {
            deleteCharacter(deleteMap.values());
        }



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
                    localCharacter.setKind(remoteCharacter.getKind());
                    return localCharacter;
                })
                .collect(Collectors.toList());
        characterRepository.saveAll(updates);
    }

    private Map<Long, Character> toCharacterMap(List<Character> characters) {
        return characters.stream()
                .collect(Collectors.toMap(Character::getRemoteId, r -> r));
    }
}