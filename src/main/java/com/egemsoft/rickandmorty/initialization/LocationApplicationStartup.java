package com.egemsoft.rickandmorty.initialization;


import com.egemsoft.rickandmorty.convert.impl.RemoteLocationConverter;
import com.egemsoft.rickandmorty.convert.impl.endpoint.ApiEndpoint;
import com.egemsoft.rickandmorty.entity.Location;
import com.egemsoft.rickandmorty.model.dto.LocationDto;
import com.egemsoft.rickandmorty.model.response.GetAllLocation;
import com.egemsoft.rickandmorty.repository.LocationRepository;
import com.google.common.collect.MapDifference;
import com.google.common.collect.Maps;
import lombok.RequiredArgsConstructor;
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

@Component
@RequiredArgsConstructor
public class LocationApplicationStartup implements ApplicationListener<ApplicationReadyEvent> {

    private final LocationRepository locationRepository;

    @Transactional
    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        initialInsertEpisode();
    }

    private void initialInsertEpisode() {
        RestTemplate restTemplate = new RestTemplate();
        List<LocationDto> remoteLocationDtos = new ArrayList<>();
        GetAllLocation getLocation = restTemplate.getForObject(ApiEndpoint.PAGEABLE_LOCATION_URL + 1, GetAllLocation.class);
        int pageSize = getLocation.getInfo().getPages();
        recursionMethod(pageSize, 1, remoteLocationDtos);
        RemoteLocationConverter remoteLocationConverter = new RemoteLocationConverter();
        List<Location> remoteLocations = remoteLocationConverter.convert(remoteLocationDtos);
        List<Location> localLocations = locationRepository.findAll();

        Map<Long, Location> remoteMap = remoteLocations.stream()
                .collect(Collectors.toMap(Location::getRemoteId, r -> r));

        Map<Long, Location> localMap = localLocations.stream()
                .collect(Collectors.toMap(Location::getRemoteId, r -> r));

        MapDifference<Long, Location> mapDifference = Maps.difference(localMap, remoteMap);
        Map<Long, Location> createMap = mapDifference.entriesOnlyOnRight();
        Map<Long, Location> deleteMap = mapDifference.entriesOnlyOnLeft();
        Map<Long, MapDifference.ValueDifference<Location>> updateMap = mapDifference.entriesDiffering();

        createLocations(createMap.values());
        deleteLocations(deleteMap.values());
        updateLocations(updateMap.values());
    }

    private void createLocations(Collection<Location> locations) {
        locationRepository.saveAll(locations);
    }

    private void deleteLocations(Collection<Location> locations) {
        locationRepository.deleteInBatch(locations);
    }

    private void updateLocations(Collection<MapDifference.ValueDifference<Location>> values) {
        List<Location> updates = values.stream()
                .map(valuesDifference -> {
                    Location localLocation = valuesDifference.leftValue();
                    Location remoteLocation = valuesDifference.rightValue();
                    localLocation.setUrl(remoteLocation.getUrl());
                    localLocation.setName(remoteLocation.getName());
                    localLocation.setType(remoteLocation.getType());
                    localLocation.setDimension(remoteLocation.getDimension());
                    localLocation.setCreated(remoteLocation.getCreated());
                    return localLocation;
                })
                .collect(Collectors.toList());
        locationRepository.saveAll(updates);
    }

    public int recursionMethod(int pageSize, int pageCount, List<LocationDto> remoteLocationDtos) {
        RestTemplate restTemplate = new RestTemplate();
        if (pageSize >= pageCount) {
            GetAllLocation getCurrentPageLocations = restTemplate.getForObject(ApiEndpoint.PAGEABLE_LOCATION_URL + pageCount, GetAllLocation.class);
            remoteLocationDtos.addAll(getCurrentPageLocations.getResults());
            return recursionMethod(pageSize, pageCount + 1, remoteLocationDtos);
        }
        return pageSize;

    }
}