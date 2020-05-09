package com.egemsoft.rickandmorty.controller;

import com.egemsoft.rickandmorty.controller.endpoint.EpisodeEndpoint;
import com.egemsoft.rickandmorty.model.generic.GenericResponse;
import com.egemsoft.rickandmorty.service.EpisodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class EpisodeController {

    private final EpisodeService episodeService;

    @GetMapping(value = EpisodeEndpoint.GET_ALL_EPISODES)
    public GenericResponse getAllEpisode(Pageable pageable) {
        return episodeService.getAllEpisode(pageable);
    }

    @GetMapping(value = EpisodeEndpoint.GET_EPISODE)
    public GenericResponse getEpisode(@PathVariable("id") Long id) {
        return episodeService.getEpisode(id);
    }
}
