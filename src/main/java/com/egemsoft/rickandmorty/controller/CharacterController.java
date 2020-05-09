package com.egemsoft.rickandmorty.controller;

import com.egemsoft.rickandmorty.controller.endpoint.CharacterEndpoint;
import com.egemsoft.rickandmorty.model.generic.GenericResponse;
import com.egemsoft.rickandmorty.service.EpisodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class CharacterController {

    private final EpisodeService episodeService;

    @GetMapping(value = CharacterEndpoint.GET_ALL_CHARACTER)
    public GenericResponse getAllEpisode(Pageable pageable) {
        return episodeService.getAllEpisode(pageable);
    }

    @GetMapping(value = CharacterEndpoint.GET_CHARACTER)
    public GenericResponse getEpisode(@PathVariable("id") Long id) {
        return episodeService.getEpisode(id);
    }
}
