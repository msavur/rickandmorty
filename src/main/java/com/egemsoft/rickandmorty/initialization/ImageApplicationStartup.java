package com.egemsoft.rickandmorty.initialization;


import com.egemsoft.rickandmorty.convert.impl.RemoteImageConverter;
import com.egemsoft.rickandmorty.entity.Episode;
import com.egemsoft.rickandmorty.entity.Image;
import com.egemsoft.rickandmorty.entity.base.BaseEntity;
import com.egemsoft.rickandmorty.enums.SourceTypeEnum;
import com.egemsoft.rickandmorty.initialization.common.CommonRestRequest;
import com.egemsoft.rickandmorty.repository.ImageRepository;
import com.google.common.collect.MapDifference;
import com.google.common.collect.Maps;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class ImageApplicationStartup implements ApplicationListener<ApplicationReadyEvent> {

    private final RemoteImageConverter remoteImageConverter;
    private final ImageRepository imageRepository;

    @Transactional
    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        initialInsertCharacterTable();
    }

    private void initialInsertCharacterTable() {
        List<Image> remoteImages = remoteImageConverter.convert(CommonRestRequest.getAllCharacter());
        List<Image> localImages = imageRepository.findAllBySourceType(SourceTypeEnum.CHARACTER);

        Map<Long, Image> remoteMap = new HashMap<>();
        remoteImages.forEach(remoteImage -> {
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

        createImage(createMap.values());
        deleteImage(deleteMap.values());
        updateImage(updateMap.values());
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