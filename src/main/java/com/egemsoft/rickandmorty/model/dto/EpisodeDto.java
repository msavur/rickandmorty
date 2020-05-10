package com.egemsoft.rickandmorty.model.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class EpisodeDto {
    private Long id;
    private String name;
    @JsonProperty("air_date")
    private String airDate;
    private String episode;
    private String url;
    private Date created;
    private List<String> characters;
}
