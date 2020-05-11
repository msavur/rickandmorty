package com.egemsoft.rickandmorty.convert.impl;


import com.egemsoft.rickandmorty.convert.BaseConverter;
import com.egemsoft.rickandmorty.entity.Character;
import com.egemsoft.rickandmorty.entity.CharacterType;
import com.egemsoft.rickandmorty.entity.Image;
import com.egemsoft.rickandmorty.entity.Kind;
import com.egemsoft.rickandmorty.enums.CharacterStatusEnum;
import com.egemsoft.rickandmorty.enums.GenderEnum;
import com.egemsoft.rickandmorty.enums.SourceTypeEnum;
import com.egemsoft.rickandmorty.model.dto.CharacterDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

@Component
@RequiredArgsConstructor
public class RemoteCharacterConverter implements BaseConverter<List<CharacterDto>, List<Character>> {


    @Override
    public List<Character> convert(List<CharacterDto> remoteCharacters) {

        List<Character> characters = new ArrayList<>();
        for (CharacterDto remote : remoteCharacters) {
            Character character = new Character();
            character.setRemoteId(remote.getId());
            character.setName(remote.getName());
            character.setUrl(remote.getUrl());
            character.setCreated(remote.getCreated());
            character.setKind(getCreateKind(remote));
            character.setType(getCreateCharacterType(remote));
            character.setImages(getCreateImages(character, remote));
            character.setGender(GenderEnum.findGenderEnum(remote.getGender()));
            character.setStatus(CharacterStatusEnum.findCharacterStatusEnum(remote.getStatus()));
            character.setEpisodes(new HashSet<>());
            characters.add(character);
        }
        return characters;
    }

    private List<Image> getCreateImages(Character character, CharacterDto remote) {
        List<Image> images = new ArrayList<>();
        Image image = new Image();
        image.setCharacter(character);
        image.setCreated(new Date());
        image.setUrl(remote.getImage());
        image.setName(remote.getName());
        image.setSourceType(SourceTypeEnum.CHARACTER);
        images.add(image);
        return images;
    }

    private CharacterType getCreateCharacterType(CharacterDto remote) {
        CharacterType characterType = new CharacterType();
        characterType.setName(remote.getType());
        characterType.setCreated(new Date());
        return characterType;
    }

    private Kind getCreateKind(CharacterDto remote) {
        Kind kind = new Kind();
        kind.setCreated(new Date());
        kind.setName(remote.getSpecies().toUpperCase());
        return kind;
    }
}
