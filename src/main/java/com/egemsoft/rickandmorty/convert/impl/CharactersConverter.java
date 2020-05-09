package com.egemsoft.rickandmorty.convert.impl;


import com.egemsoft.rickandmorty.convert.BaseConverter;
import com.egemsoft.rickandmorty.convert.impl.endpoint.ApiEndpoint;
import com.egemsoft.rickandmorty.entity.Character;
import com.egemsoft.rickandmorty.model.response.CharacterResponse;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

public class CharactersConverter implements BaseConverter<Page<Character>, List<CharacterResponse>> {


    @Override
    public List<CharacterResponse> convert(Page<Character> characterPage) {
        List<CharacterResponse> characterResponses = new ArrayList<>();
        characterPage.getContent().forEach(character -> {
            CharacterResponse characterResponse = new CharacterResponse();
            characterResponse.setId(character.getId());
            characterResponse.setName(character.getName());
            characterResponse.setUrl(character.getUrl());
            characterResponse.setCreated(character.getCreated());
            List<String> urls = new ArrayList<>();
            character.getEpisodes().forEach(episode -> {
                urls.add(ApiEndpoint.EPISODE_URL + episode.getId());
            });
            characterResponse.setEpisode(urls);
            characterResponses.add(characterResponse);
        });
        return characterResponses;
    }
}
