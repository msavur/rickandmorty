package com.egemsoft.rickandmorty.convert.impl;


import com.egemsoft.rickandmorty.convert.BaseConverter;
import com.egemsoft.rickandmorty.entity.CharacterType;
import com.egemsoft.rickandmorty.model.dto.CharacterDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class RemoteCharacterTypeConverter implements BaseConverter<List<CharacterDto>, Map<String, CharacterType>> {

    @Override
    public Map<String, CharacterType> convert(List<CharacterDto> remoteCharacters) {
        Map<String, CharacterType> characterTypeMap = new HashMap<>();
        remoteCharacters.forEach(remote -> {
            if (!StringUtils.isEmpty(remote.getType()) && !characterTypeMap.containsKey(remote.getType().toUpperCase())) {
                CharacterType characterType = new CharacterType();
                characterType.setCreated(new Date());
                characterType.setName(remote.getType());
                characterType.setUrl(remote.getUrl());
                characterTypeMap.putIfAbsent(remote.getSpecies(), characterType);
            }
        });
        return characterTypeMap;
    }
}
