package com.egemsoft.rickandmorty.service;


import com.egemsoft.rickandmorty.model.generic.GenericResponse;
import org.springframework.data.domain.Pageable;

public interface CharacterService {
    GenericResponse<?> getAllCharacter(Pageable pageable);

    GenericResponse<?> getCharacter(Long id);
}
