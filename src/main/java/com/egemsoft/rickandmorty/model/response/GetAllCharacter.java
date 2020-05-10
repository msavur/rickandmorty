package com.egemsoft.rickandmorty.model.response;


import com.egemsoft.rickandmorty.model.dto.CharacterDto;
import com.egemsoft.rickandmorty.model.dto.PageableDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GetAllCharacter {
    private PageableDto info;
    private List<CharacterDto> results;
}
