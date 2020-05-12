package com.egemsoft.rickandmorty.initialization.batchservice;


import com.egemsoft.core.entity.Character;
import com.egemsoft.core.entity.CharacterType;
import com.egemsoft.core.entity.Kind;
import com.egemsoft.rickandmorty.convert.impl.RemoteCharacterConverter;
import com.egemsoft.rickandmorty.model.dto.CharacterDto;
import com.egemsoft.rickandmorty.repository.CharacterRepository;
import com.egemsoft.rickandmorty.repository.CharacterTypeRepository;
import com.egemsoft.rickandmorty.repository.KindRepository;
import com.google.common.collect.MapDifference;
import com.google.common.collect.Maps;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class CharacterBatchService {

    private final CharacterRepository characterRepository;
    private final RemoteCharacterConverter remoteCharacterConverter;
    private final KindRepository kindRepository;
    private final CharacterTypeRepository characterTypeRepository;

    @Transactional
    public void execute(List<CharacterDto> characterDtos) {
        List<Character> remoteCharacters = remoteCharacterConverter.convert(characterDtos);
        List<Character> localCharacters = characterRepository.findAll();
        Map<String, Kind> kindMap = kindRepository.findAll()
                .stream()
                .collect(Collectors.toMap(k -> k.getName().toUpperCase(), k -> k));
        Map<String, CharacterType> characterTypeMap = characterTypeRepository.findAll()
                .stream()
                .collect(Collectors.toMap(CharacterType::getName, k -> k));

        for (Character remoteCharacter : remoteCharacters) {
            if (!StringUtils.isEmpty(remoteCharacter.getKind().getName()))
                remoteCharacter.setKind(kindMap.get(remoteCharacter.getKind().getName()));
            if (!StringUtils.isEmpty(remoteCharacter.getType().getName()))
                remoteCharacter.setType(characterTypeMap.get(remoteCharacter.getType().getName()));
        }


        Map<Long, Character> localMap = toCharacterMap(localCharacters);
        Map<Long, Character> remoteMap = toCharacterMap(remoteCharacters);

        MapDifference<Long, Character> mapDifference = Maps.difference(localMap, remoteMap);
        Map<Long, Character> deleteMap = mapDifference.entriesOnlyOnLeft();

        if (!CollectionUtils.isEmpty(deleteMap.values())) {
            deleteCharacter(deleteMap.values());
        }


        // getRemoteEpisodes
        // link, list<episode>


        Map<Long, Character> createMap = mapDifference.entriesOnlyOnRight();
        Map<Long, MapDifference.ValueDifference<Character>> updateMap = mapDifference.entriesDiffering();

        if (!CollectionUtils.isEmpty(createMap.values())) {
            createCharacter(createMap.values());
        }
        if (!CollectionUtils.isEmpty(updateMap.values())) {
            updateCharacter(updateMap.values());
        }
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
                    if (!ObjectUtils.isEmpty(remoteCharacter.getKind()) && !StringUtils.isEmpty(remoteCharacter.getKind().getName()))
                        localCharacter.setKind(remoteCharacter.getKind());
                    if (!ObjectUtils.isEmpty(remoteCharacter.getType()) && !StringUtils.isEmpty(remoteCharacter.getType().getName()))
                        localCharacter.setType(remoteCharacter.getType());
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