package com.egemsoft.rickandmorty.controller;

import com.egemsoft.rickandmorty.controller.endpoint.CharacterEndpoint;
import com.egemsoft.rickandmorty.model.generic.GenericResponse;
import com.egemsoft.rickandmorty.service.CharacterService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CharacterController {

    private final CharacterService characterService;

    @GetMapping(value = CharacterEndpoint.GET_ALL_CHARACTER)
    public GenericResponse getAllCharacter(Pageable pageable) {
        return characterService.getAllCharacter(pageable);
    }

    @GetMapping(value = CharacterEndpoint.GET_CHARACTER)
    public GenericResponse getCharacter(@PathVariable("id") Long id) {
        return characterService.getCharacter(id);
    }
}
