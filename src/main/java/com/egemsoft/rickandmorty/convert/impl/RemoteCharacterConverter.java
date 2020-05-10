package com.egemsoft.rickandmorty.convert.impl;


import com.egemsoft.rickandmorty.convert.BaseConverter;
import com.egemsoft.rickandmorty.entity.Character;
import com.egemsoft.rickandmorty.enums.CharacterStatusEnum;
import com.egemsoft.rickandmorty.enums.GenderEnum;
import com.egemsoft.rickandmorty.model.dto.CharacterDto;
import com.egemsoft.rickandmorty.repository.ImageRepository;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class RemoteCharacterConverter implements BaseConverter<List<CharacterDto>, List<Character>> {


    @Override
    public List<Character> convert(List<CharacterDto> remoteCharacters) {

        //imageRepository.findAll();

        List<Character> characters = new ArrayList<>();
        remoteCharacters.forEach(remote -> {
            Character character = new Character();
            character.setRemoteId(remote.getId());
            character.setName(remote.getName());
            character.setUrl(remote.getUrl());
            character.setCreated(remote.getCreated());
            character.setGender(GenderEnum.findGenderEnum(remote.getGender()));
            character.setStatus(CharacterStatusEnum.findCharacterStatusEnum(remote.getStatus()));
         //   character.setImage(remote.getImage());
           // character.setSpecies(remote.getSpecies());
           // character.setType(remote.getType());
            characters.add(character);
        });
        return characters;
    }
}
