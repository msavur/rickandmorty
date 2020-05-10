package com.egemsoft.rickandmorty.convert.impl;


import com.egemsoft.rickandmorty.convert.BaseConverter;
import com.egemsoft.rickandmorty.entity.Character;
import com.egemsoft.rickandmorty.model.dto.CharacterDto;

import java.util.ArrayList;
import java.util.List;

public class RemoteCharacterConverter implements BaseConverter<List<CharacterDto>, List<Character>> {


    @Override
    public List<Character> convert(List<CharacterDto> remoteCharacters) {
        List<Character> characters = new ArrayList<>();
        remoteCharacters.forEach(remote -> {
            Character character = new Character();
            character.setRemoteId(remote.getId());
            character.setName(remote.getName());
            character.setUrl(remote.getUrl());
            characters.add(character);
        });
        return characters;
    }
}
