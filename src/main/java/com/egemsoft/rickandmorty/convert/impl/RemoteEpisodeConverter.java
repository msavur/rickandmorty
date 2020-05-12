package com.egemsoft.rickandmorty.convert.impl;


import com.egemsoft.core.entity.Episode;
import com.egemsoft.rickandmorty.convert.BaseConverter;
import com.egemsoft.rickandmorty.model.dto.EpisodeDto;
import com.egemsoft.rickandmorty.util.DateUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class RemoteEpisodeConverter implements BaseConverter<List<EpisodeDto>, List<Episode>> {

    @Override
    public List<Episode> convert(List<EpisodeDto> remoteEpisodes) {
        List<Episode> episodes = new ArrayList<>();
        for (EpisodeDto remote : remoteEpisodes) {
            Episode episode = new Episode();
            episode.setRemoteId(remote.getId());
            episode.setEpisode(remote.getEpisode());
            episode.setAirDate(DateUtils.convertDate(remote.getAirDate()));
            episode.setName(remote.getName());
            episode.setUrl(remote.getUrl());
            episode.setCreated(remote.getCreated());
            episodes.add(episode);
        }
        return episodes;
    }
}
