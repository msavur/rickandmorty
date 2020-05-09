package com.egemsoft.rickandmorty.model.response;


import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class CharacterResponse {
    private Long id;
    private String name;
    private String status;
    private String species;
    private String type;
    private String gender;
    private String image;
    private List<String> episode;
    private String url;
    private Date created;
}
