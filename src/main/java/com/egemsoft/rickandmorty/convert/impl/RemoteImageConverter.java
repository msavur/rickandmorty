package com.egemsoft.rickandmorty.convert.impl;


import com.egemsoft.core.entity.Character;
import com.egemsoft.core.entity.Image;
import com.egemsoft.core.enums.SourceTypeEnum;
import com.egemsoft.rickandmorty.convert.BaseConverter;
import com.egemsoft.rickandmorty.model.dto.CharacterDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class RemoteImageConverter implements BaseConverter<List<CharacterDto>, List<Image>> {

    @Override
    public List<Image> convert(List<CharacterDto> remoteCharacters) {
        List<Image> images = new ArrayList<>();
        remoteCharacters.forEach(remote -> {
            Image image = new Image();
            image.setSourceType(SourceTypeEnum.CHARACTER);
            image.setCreated(new Date());
            Character character = new Character();
            character.setRemoteId(remote.getId());
            image.setCharacter(character);
            image.setName(remote.getName());
            image.setUrl(remote.getImage());
            images.add(image);
        });
        return images;
    }
}
