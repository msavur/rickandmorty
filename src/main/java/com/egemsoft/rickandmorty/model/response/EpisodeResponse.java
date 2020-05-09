package com.egemsoft.rickandmorty.model.response;


import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class EpisodeResponse {
    private Long id;
    private String name;
    private Date airDate;
    private String episode;
    private List<String> characters;
    private String url;
    private Date created;
}
