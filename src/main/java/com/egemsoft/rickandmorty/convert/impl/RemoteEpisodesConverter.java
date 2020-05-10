package com.egemsoft.rickandmorty.convert.impl;


import com.egemsoft.rickandmorty.convert.BaseConverter;
import com.egemsoft.rickandmorty.entity.Episode;
import com.egemsoft.rickandmorty.model.response.GetAllEpisode;

import java.util.ArrayList;
import java.util.List;

public class RemoteEpisodesConverter implements BaseConverter<List<GetAllEpisode>, List<Episode>> {


    @Override
    public List<Episode> convert(List<GetAllEpisode> remoteEpisodes) {
        List<Episode> episodes = new ArrayList<>();
        remoteEpisodes.forEach(remote -> {
            remote.getResults().forEach(result -> {
                Episode episode = new Episode();
                episode.setAirDate(result.getAirDate());
                episode.setEpisode(result.getEpisode());
                episode.setName(result.getName());
                episode.setUrl(result.getUrl());
                episodes.add(episode);
            });
        });
        return episodes;
    }
}
