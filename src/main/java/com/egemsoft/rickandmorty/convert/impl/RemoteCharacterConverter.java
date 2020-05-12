package com.egemsoft.rickandmorty.convert.impl;


import com.egemsoft.core.entity.Character;
import com.egemsoft.core.entity.CharacterType;
import com.egemsoft.core.entity.Image;
import com.egemsoft.core.entity.Kind;
import com.egemsoft.core.enums.CharacterStatusEnum;
import com.egemsoft.core.enums.GenderEnum;
import com.egemsoft.core.enums.SourceTypeEnum;
import com.egemsoft.rickandmorty.convert.BaseConverter;
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
            character.setGender(GenderEnum.findGenderEnum(remote.getGender()));
            character.setStatus(CharacterStatusEnum.findCharacterStatusEnum(remote.getStatus()));
            character.setEpisodes(new HashSet<>());
            characters.add(character);
        }
        return characters;
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
