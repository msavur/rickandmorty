package com.egemsoft.rickandmorty.convert.impl;


import com.egemsoft.rickandmorty.convert.BaseConverter;
import com.egemsoft.rickandmorty.entity.Character;
import com.egemsoft.rickandmorty.entity.Image;
import com.egemsoft.rickandmorty.enums.SourceTypeEnum;
import com.egemsoft.rickandmorty.model.dto.CharacterDto;
import com.egemsoft.rickandmorty.repository.CharacterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class RemoteImageConverter implements BaseConverter<List<CharacterDto>, List<Image>> {

    private final CharacterRepository characterRepository;

    @Override
    public List<Image> convert(List<CharacterDto> remoteCharacters) {
        List<Image> images = new ArrayList<>();
        List<Long> remoteIds = remoteCharacters.stream().map(CharacterDto::getId).collect(Collectors.toList());
        Map<Long, Character> characterMap = characterRepository.findByRemoteIdIn(remoteIds)
                .stream()
                .collect(Collectors.toMap(Character::getRemoteId, c -> c));
        remoteCharacters.forEach(remote -> {
            Image image = new Image();
            image.setSourceType(SourceTypeEnum.CHARACTER);
            image.setCharacter(new Character());
            if (characterMap.containsKey(remote.getId())) {
                image.setCharacter(characterMap.get(remote.getId()));
            } else {
                image.setCharacter(new Character());
            }
            image.setCreated(new Date());
            image.setName(remote.getName());
            image.setUrl(remote.getImage());
            images.add(image);
        });
        return images;
    }
}
