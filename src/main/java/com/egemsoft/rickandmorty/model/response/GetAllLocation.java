package com.egemsoft.rickandmorty.model.response;


import com.egemsoft.rickandmorty.model.dto.LocationDto;
import com.egemsoft.rickandmorty.model.dto.PageableDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GetAllLocation {
    private PageableDto info;
    private List<LocationDto> results;
}
