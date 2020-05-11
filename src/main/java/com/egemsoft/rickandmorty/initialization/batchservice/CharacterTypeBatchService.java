package com.egemsoft.rickandmorty.initialization.batchservice;


import com.egemsoft.rickandmorty.convert.impl.RemoteCharacterTypeConverter;
import com.egemsoft.rickandmorty.entity.CharacterType;
import com.egemsoft.rickandmorty.initialization.common.CommonRestRequest;
import com.egemsoft.rickandmorty.repository.CharacterTypeRepository;
import com.google.common.collect.MapDifference;
import com.google.common.collect.Maps;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class CharacterTypeBatchService {

    private final RemoteCharacterTypeConverter remoteCharacterTypeConverter;
    private final CharacterTypeRepository characterTypeRepository;

    public void execute() {
        Map<String, CharacterType> remoteMap = remoteCharacterTypeConverter.convert(CommonRestRequest.getAllCharacter());
        Map<String, CharacterType> localMap = characterTypeRepository.findAll()
                .stream()
                .collect(Collectors.toMap(CharacterType::getName, r -> r));

        MapDifference<String, CharacterType> mapDifference = Maps.difference(localMap, remoteMap);
        Map<String, CharacterType> createMap = mapDifference.entriesOnlyOnRight();
        Map<String, CharacterType> deleteMap = mapDifference.entriesOnlyOnLeft();
        Map<String, MapDifference.ValueDifference<CharacterType>> updateMap = mapDifference.entriesDiffering();


        if (!CollectionUtils.isEmpty(createMap.values())) {
            createCharacterType(createMap.values());
        }
        if (!CollectionUtils.isEmpty(updateMap.values())) {
            updateCharacterType(updateMap.values());
        }
        if (!CollectionUtils.isEmpty(deleteMap.values())) {
            deleteCharacterType(deleteMap.values());
        }
    }

    private void createCharacterType(Collection<CharacterType> characterTypes) {
        characterTypeRepository.saveAll(characterTypes);
    }

    private void deleteCharacterType(Collection<CharacterType> characterTypes) {
        characterTypeRepository.deleteInBatch(characterTypes);
    }

    private void updateCharacterType(Collection<MapDifference.ValueDifference<CharacterType>> values) {
        List<CharacterType> updates = values.stream()
                .map(valuesDifference -> {
                    CharacterType localCharacterType = valuesDifference.leftValue();
                    CharacterType remoteCharacterType = valuesDifference.rightValue();
                    localCharacterType.setUrl(remoteCharacterType.getUrl());
                    localCharacterType.setName(remoteCharacterType.getName());
                    localCharacterType.setCreated(remoteCharacterType.getCreated());
                    return localCharacterType;
                })
                .collect(Collectors.toList());
        characterTypeRepository.saveAll(updates);
    }
}