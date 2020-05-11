package com.egemsoft.rickandmorty.initialization.batchservice;


import com.egemsoft.rickandmorty.convert.impl.RemoteEpisodeConverter;
import com.egemsoft.rickandmorty.convert.impl.endpoint.ApiEndpoint;
import com.egemsoft.rickandmorty.entity.Character;
import com.egemsoft.rickandmorty.entity.Episode;
import com.egemsoft.rickandmorty.model.dto.EpisodeDto;
import com.egemsoft.rickandmorty.model.response.GetAllEpisode;
import com.egemsoft.rickandmorty.repository.EpisodeRepository;
import com.google.common.collect.MapDifference;
import com.google.common.collect.Maps;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class EpisodeBatchService {

    private final static int COUNT_PAGE = 2;

    private final EpisodeRepository episodeRepository;
    private final RemoteEpisodeConverter remoteEpisodeConverter;

    public void execute() {
        RestTemplate restTemplate = new RestTemplate();
        List<EpisodeDto> remoteEpisodeDtos = new ArrayList<>();
        GetAllEpisode getAllEpisode = null;
        try {
            getAllEpisode = restTemplate.getForObject(ApiEndpoint.PAGEABLE_EPISODE_URL + 1, GetAllEpisode.class);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return;
        }
        int pageSize = getAllEpisode.getInfo().getPages();
        remoteEpisodeDtos.addAll(getAllEpisode.getResults());
        recursionEpisode(pageSize, COUNT_PAGE, remoteEpisodeDtos);
        List<Episode> remoteEpisodes = remoteEpisodeConverter.convert(remoteEpisodeDtos);
        List<Episode> localEpisodes = episodeRepository.findAll();

        Map<Long, Episode> localMap = toEpisodeMap(localEpisodes);
        Map<Long, Episode> remoteMap = toEpisodeMap(remoteEpisodes);

        MapDifference<Long, Episode> mapDifference = Maps.difference(localMap, remoteMap);
        Map<Long, Episode> createMap = mapDifference.entriesOnlyOnRight();
        Map<Long, Episode> deleteMap = mapDifference.entriesOnlyOnLeft();
        Map<Long, MapDifference.ValueDifference<Episode>> updateMap = mapDifference.entriesDiffering();

        if (!CollectionUtils.isEmpty(createMap.values())) {
            createEpisodes(createMap.values());
        }

        if (!CollectionUtils.isEmpty(deleteMap.values())) {
            deleteEpisodes(deleteMap.values());
        }

        if (!CollectionUtils.isEmpty(updateMap.values())) {
            updateEpisodes(updateMap.values());
        }
    }


    private Set<Character> getCharacter(Map<Long, Character> characterMap, Episode episode, List<String> characterList) {
        Set<Character> characters = new HashSet<>();
        for (String url : characterList) {
            long characterId = Long.parseLong(url.substring(url.lastIndexOf("/") + 1));
            Character character = characterMap.get(characterId);
            characters.add(character);
        }
        return characters;
    }

    private void createEpisodes(Collection<Episode> episodes) {
        episodeRepository.saveAll(episodes);
    }

    private void deleteEpisodes(Collection<Episode> episodes) {
        episodeRepository.deleteInBatch(episodes);
    }

    private void updateEpisodes(Collection<MapDifference.ValueDifference<Episode>> values) {
        List<Episode> updates = values.stream()
                .map(valuesDifference -> {
                    Episode localEpisode = valuesDifference.leftValue();
                    Episode remoteEpisode = valuesDifference.rightValue();
                    localEpisode.setUrl(remoteEpisode.getUrl());
                    localEpisode.setName(remoteEpisode.getName());
                    localEpisode.setEpisode(remoteEpisode.getEpisode());
                    localEpisode.setCreated(remoteEpisode.getCreated());
                    localEpisode.setAirDate(remoteEpisode.getAirDate());
                    return localEpisode;
                })
                .collect(Collectors.toList());
        episodeRepository.saveAll(updates);
    }

    private Map<Long, Episode> toEpisodeMap(List<Episode> characters) {
        return characters.stream()
                .collect(Collectors.toMap(Episode::getRemoteId, r -> r));
    }

    public int recursionEpisode(int pageSize, int pageCount, List<EpisodeDto> remoteEpisodeDtos) {
        RestTemplate restTemplate = new RestTemplate();
        if (pageSize >= pageCount) {
            GetAllEpisode getAllEpisode = restTemplate.getForObject(ApiEndpoint.PAGEABLE_EPISODE_URL + pageCount, GetAllEpisode.class);
            remoteEpisodeDtos.addAll(getAllEpisode.getResults());
            return recursionEpisode(pageSize, pageCount + 1, remoteEpisodeDtos);
        }
        return pageSize;

    }
}