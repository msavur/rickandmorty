package com.egemsoft.rickandmorty.service.impl;

import com.egemsoft.core.entity.Character;
import com.egemsoft.rickandmorty.convert.impl.CharacterConverter;
import com.egemsoft.rickandmorty.convert.impl.CharactersConverter;
import com.egemsoft.rickandmorty.convert.impl.endpoint.ApiEndpoint;
import com.egemsoft.rickandmorty.model.generic.GenericResponse;
import com.egemsoft.rickandmorty.model.generic.PageableInfo;
import com.egemsoft.rickandmorty.model.response.CharacterResponse;
import com.egemsoft.rickandmorty.model.response.EpisodeResponse;
import com.egemsoft.rickandmorty.repository.CharacterRepository;
import com.egemsoft.rickandmorty.service.CharacterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CharacterServiceImpl implements CharacterService {

    private final CharacterRepository characterRepository;

    @Override
    public GenericResponse<EpisodeResponse> getAllCharacter(Pageable pageable) {
        Page<Character> characterPage = characterRepository.findAll(pageable);
        CharactersConverter charactersConverter = new CharactersConverter();
        List<CharacterResponse> response = charactersConverter.convert(characterPage);
        PageableInfo pageableInfo = getPageableInfo(characterPage);
        return new GenericResponse(pageableInfo, response);
    }

    @Override
    public GenericResponse<EpisodeResponse> getCharacter(Long id) {
        Optional<Character> optionalCharacter = characterRepository.findById(id);
        if (!optionalCharacter.isPresent()) {
            return GenericResponse.empty();
        }
        CharacterConverter characterConverter = new CharacterConverter();
        CharacterResponse convert = characterConverter.convert(optionalCharacter.get());
        return new GenericResponse(convert);
    }

    private PageableInfo getPageableInfo(Page<Character> characterPage) {
        PageableInfo pageableInfo = new PageableInfo();
        pageableInfo.setCount(characterPage.getTotalElements());
        pageableInfo.setNext(ApiEndpoint.PAGEABLE_CHARACTER_URL + characterPage.getNumber() + 1);
        pageableInfo.setPages(characterPage.getSize());
        pageableInfo.setPrev("");
        return pageableInfo;
    }
}
