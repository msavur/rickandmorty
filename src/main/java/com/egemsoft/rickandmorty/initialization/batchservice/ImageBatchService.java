package com.egemsoft.rickandmorty.initialization.batchservice;


import com.egemsoft.core.entity.Character;
import com.egemsoft.core.entity.CharacterType;
import com.egemsoft.core.entity.Image;
import com.egemsoft.core.entity.base.BaseEntity;
import com.egemsoft.core.enums.SourceTypeEnum;
import com.egemsoft.rickandmorty.convert.impl.RemoteImageConverter;
import com.egemsoft.rickandmorty.initialization.common.CommonRestRequest;
import com.egemsoft.rickandmorty.model.dto.CharacterDto;
import com.egemsoft.rickandmorty.repository.CharacterRepository;
import com.egemsoft.rickandmorty.repository.ImageRepository;
import com.google.common.collect.MapDifference;
import com.google.common.collect.Maps;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class ImageBatchService {

    private final RemoteImageConverter remoteImageConverter;
    private final ImageRepository imageRepository;
    private final CharacterRepository characterRepository;

    @Transactional
    public void execute(List<CharacterDto> characterDtos) {
        List<Image> remoteImages = remoteImageConverter.convert(characterDtos);
        Map<Long, Character> characterMap = characterRepository.findAll()
                .stream().collect(Collectors.toMap(Character::getRemoteId, c -> c));
        List<Image> localImages = imageRepository.findAllBySourceType(SourceTypeEnum.CHARACTER);

        Map<Long, Image> remoteMap = new HashMap<>();
        remoteImages.forEach(remoteImage -> {
            Character character = characterMap.get(remoteImage.getCharacter().getRemoteId());
            remoteImage.setCharacter(character);
            remoteMap.putIfAbsent(remoteImage.getCharacter().getId(), remoteImage);
        });

        Map<Long, Image> localMap = new HashMap<>();
        localImages.forEach(remoteImage -> {
            localMap.putIfAbsent(remoteImage.getCharacter().getId(), remoteImage);
        });

        MapDifference<Long, Image> mapDifference = Maps.difference(localMap, remoteMap);
        Map<Long, Image> createMap = mapDifference.entriesOnlyOnRight();
        Map<Long, Image> deleteMap = mapDifference.entriesOnlyOnLeft();
        Map<Long, MapDifference.ValueDifference<Image>> updateMap = mapDifference.entriesDiffering();


        if (!CollectionUtils.isEmpty(createMap.values())) {
            createImage(createMap.values());
        }
        if (!CollectionUtils.isEmpty(updateMap.values())) {
            updateImage(updateMap.values());
        }
        if (!CollectionUtils.isEmpty(deleteMap.values())) {
            deleteImage(deleteMap.values());
        }
    }

    private void createImage(Collection<Image> images) {
        imageRepository.saveAll(images);
    }

    private void deleteImage(Collection<Image> characters) {
        List<Long> imageIds = characters.stream().map(BaseEntity::getId).collect(Collectors.toList());
        imageRepository.deleteImageByIds(imageIds);
    }

    private void updateImage(Collection<MapDifference.ValueDifference<Image>> values) {
        List<Image> updates = values.stream()
                .map(valuesDifference -> {
                    Image localImage = valuesDifference.leftValue();
                    Image remoteImage = valuesDifference.rightValue();
                    localImage.setUrl(remoteImage.getUrl());
                    localImage.setName(remoteImage.getName());
                    localImage.setCreated(remoteImage.getCreated());
                    return localImage;
                })
                .collect(Collectors.toList());
        imageRepository.saveAll(updates);
    }
}