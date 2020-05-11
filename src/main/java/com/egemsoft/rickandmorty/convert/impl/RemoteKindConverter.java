package com.egemsoft.rickandmorty.convert.impl;


import com.egemsoft.rickandmorty.convert.BaseConverter;
import com.egemsoft.rickandmorty.entity.Kind;
import com.egemsoft.rickandmorty.model.dto.CharacterDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class RemoteKindConverter implements BaseConverter<List<CharacterDto>, Map<String, Kind>> {

    @Override
    public Map<String, Kind> convert(List<CharacterDto> remoteCharacters) {
        Map<String, Kind> kindMap = new HashMap<>();
        remoteCharacters.forEach(remote -> {
            if (!kindMap.containsKey(remote.getSpecies().toUpperCase())) {
                Kind kind = new Kind();
                kind.setCreated(new Date());
                kind.setName(remote.getSpecies());
                kindMap.putIfAbsent(remote.getSpecies(), kind);
            }
        });
        return kindMap;
    }
}
