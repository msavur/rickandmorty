package com.egemsoft.rickandmorty.service;


import com.egemsoft.rickandmorty.model.generic.GenericResponse;
import org.springframework.data.domain.Pageable;

public interface EpisodeService {
    GenericResponse<?> getAllEpisode(Pageable pageable);

    GenericResponse<?> getEpisode(Long id);
}
