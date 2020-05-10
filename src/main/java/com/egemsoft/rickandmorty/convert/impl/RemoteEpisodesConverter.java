package com.egemsoft.rickandmorty.convert.impl;


import com.egemsoft.rickandmorty.convert.BaseConverter;
import com.egemsoft.rickandmorty.entity.Episode;
import com.egemsoft.rickandmorty.model.dto.EpisodeDto;

import java.util.ArrayList;
import java.util.List;

public class RemoteEpisodesConverter implements BaseConverter<List<EpisodeDto>, List<Episode>> {


    @Override
    public List<Episode> convert(List<EpisodeDto> remoteEpisodes) {
        List<Episode> episodes = new ArrayList<>();
        remoteEpisodes.forEach(remote -> {
            Episode episode = new Episode();
            episode.setRemoteId(remote.getId());
            episode.setAirDate(remote.getAirDate());
            episode.setEpisode(remote.getEpisode());
            episode.setName(remote.getName());
            episode.setUrl(remote.getUrl());
            episodes.add(episode);
        });
        return episodes;
    }
}
