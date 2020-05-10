package com.egemsoft.rickandmorty.model.response;


import com.egemsoft.rickandmorty.model.dto.EpisodeDto;
import com.egemsoft.rickandmorty.model.dto.PageableDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GetAllEpisode {
    private PageableDto info;
    private List<EpisodeDto> results;
}
