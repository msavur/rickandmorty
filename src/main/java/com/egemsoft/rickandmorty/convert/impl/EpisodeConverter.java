package com.egemsoft.rickandmorty.convert.impl;


import com.egemsoft.core.entity.Episode;
import com.egemsoft.rickandmorty.convert.BaseConverter;
import com.egemsoft.rickandmorty.convert.impl.endpoint.ApiEndpoint;
import com.egemsoft.rickandmorty.model.response.EpisodeResponse;

import java.util.ArrayList;
import java.util.List;

public class EpisodeConverter implements BaseConverter<Episode, EpisodeResponse> {


    @Override
    public EpisodeResponse convert(Episode episode) {
        EpisodeResponse episodeResponse = new EpisodeResponse();
        episodeResponse.setId(episode.getId());
        episodeResponse.setName(episode.getName());
        episodeResponse.setUrl(episode.getUrl());
        episodeResponse.setEpisode(episode.getEpisode());
        episodeResponse.setAirDate(episode.getAirDate());
        episodeResponse.setCreated(episode.getCreated());
        List<String> urls = new ArrayList<>();
        episode.getCharacters().forEach(character -> {
            urls.add(ApiEndpoint.CHARACTER_URL + character.getId());
        });
        episodeResponse.setCharacters(urls);
        return episodeResponse;
    }
}
