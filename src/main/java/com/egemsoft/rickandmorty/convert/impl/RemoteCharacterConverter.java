package com.egemsoft.rickandmorty.convert.impl;


import com.egemsoft.core.entity.Character;
import com.egemsoft.core.entity.CharacterType;
import com.egemsoft.core.entity.Kind;
import com.egemsoft.core.enums.CharacterStatusEnum;
import com.egemsoft.core.enums.GenderEnum;
import com.egemsoft.rickandmorty.convert.BaseConverter;
import com.egemsoft.rickandmorty.model.dto.CharacterDto;
import com.egemsoft.rickandmorty.repository.CharacterTypeRepository;
import com.egemsoft.rickandmorty.repository.KindRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class RemoteCharacterConverter implements BaseConverter<List<CharacterDto>, List<Character>> {

    private final KindRepository kindRepository;
    private final CharacterTypeRepository characterTypeRepository;

    @Override
    public List<Character> convert(List<CharacterDto> remoteCharacters) {

        Map<String, Kind> kindMap = kindRepository.findAll()
                .stream()
                .collect(Collectors.toMap(k -> k.getName().toUpperCase(), k -> k));
        Map<String, CharacterType> characterTypeMap = characterTypeRepository.findAll()
                .stream()
                .collect(Collectors.toMap(c->c.getName().toUpperCase(), k -> k));

        List<Character> characters = new ArrayList<>();
        for (CharacterDto remote : remoteCharacters) {
            Character character = new Character();
            character.setRemoteId(remote.getId());
            character.setName(remote.getName());
            character.setUrl(remote.getUrl());
            character.setCreated(remote.getCreated());
            if (!StringUtils.isEmpty(remote.getSpecies())) {
                Kind kind = kindMap.get(remote.getSpecies().toUpperCase());
                character.setKind(kind);
            }
            if (!StringUtils.isEmpty(remote.getType())) {
                CharacterType characterType = characterTypeMap.get(remote.getType().toUpperCase());
                character.setType(characterType);
            }
            character.setGender(GenderEnum.findGenderEnum(remote.getGender()));
            character.setStatus(CharacterStatusEnum.findCharacterStatusEnum(remote.getStatus()));
            character.setEpisodes(new HashSet<>());
            characters.add(character);
        }
        return characters;
    }
}
