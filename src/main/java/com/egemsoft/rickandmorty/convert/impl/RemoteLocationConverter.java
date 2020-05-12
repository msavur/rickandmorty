package com.egemsoft.rickandmorty.convert.impl;


import com.egemsoft.core.entity.Location;
import com.egemsoft.rickandmorty.convert.BaseConverter;
import com.egemsoft.rickandmorty.model.dto.LocationDto;

import java.util.ArrayList;
import java.util.List;

public class RemoteLocationConverter implements BaseConverter<List<LocationDto>, List<Location>> {


    @Override
    public List<Location> convert(List<LocationDto> remoteLocations) {
        List<Location> locations = new ArrayList<>();
        remoteLocations.forEach(remote -> {
            Location location = new Location();
            location.setRemoteId(remote.getId());
            location.setDimension(remote.getDimension());
            location.setSpecies(remote.getDimension());
            location.setName(remote.getName());
            location.setUrl(remote.getUrl());
            location.setType(remote.getType());
            location.setCreated(remote.getCreated());
            locations.add(location);
        });
        return locations;
    }
}
