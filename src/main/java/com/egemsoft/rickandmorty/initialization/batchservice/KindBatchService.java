package com.egemsoft.rickandmorty.initialization.batchservice;


import com.egemsoft.core.entity.Kind;
import com.egemsoft.rickandmorty.convert.impl.RemoteKindConverter;
import com.egemsoft.rickandmorty.initialization.common.CommonRestRequest;
import com.egemsoft.rickandmorty.model.dto.CharacterDto;
import com.egemsoft.rickandmorty.repository.KindRepository;
import com.google.common.collect.MapDifference;
import com.google.common.collect.Maps;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class KindBatchService {

    private final RemoteKindConverter remoteKindConverter;
    private final KindRepository kindRepository;

    @Transactional
    public void execute(List<CharacterDto> remoteCharacters) {
        Map<String, Kind> remoteMap = remoteKindConverter.convert(remoteCharacters);
        Map<String, Kind> localMap = kindRepository.findAll()
                .stream()
                .collect(Collectors.toMap(Kind::getName, r -> r));

        MapDifference<String, Kind> mapDifference = Maps.difference(localMap, remoteMap);
        Map<String, Kind> createMap = mapDifference.entriesOnlyOnRight();
        Map<String, Kind> deleteMap = mapDifference.entriesOnlyOnLeft();
        Map<String, MapDifference.ValueDifference<Kind>> updateMap = mapDifference.entriesDiffering();


        if (!CollectionUtils.isEmpty(createMap.values())) {
            createKind(createMap.values());
        }
        if (!CollectionUtils.isEmpty(updateMap.values())) {
            updateKind(updateMap.values());
        }
        if (!CollectionUtils.isEmpty(deleteMap.values())) {
            deleteKind(deleteMap.values());
        }
    }

    private void createKind(Collection<Kind> Kinds) {
        kindRepository.saveAll(Kinds);
    }

    private void deleteKind(Collection<Kind> kinds) {
        kindRepository.deleteInBatch(kinds);
    }

    private void updateKind(Collection<MapDifference.ValueDifference<Kind>> values) {
        List<Kind> updates = values.stream()
                .map(valuesDifference -> {
                    Kind localKind = valuesDifference.leftValue();
                    Kind remoteKind = valuesDifference.rightValue();
                    localKind.setName(remoteKind.getName());
                    localKind.setCreated(remoteKind.getCreated());
                    return localKind;
                })
                .collect(Collectors.toList());
        kindRepository.saveAll(updates);
    }
}