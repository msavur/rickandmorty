package com.egemsoft.rickandmorty.model.dto;


import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class CharacterDto {

    private Long id;
    private String name;
    private String status;
    private String species;
    private String type;
    private String gender;
    private String image;
    private String url;
    private Date created;
    private List<String> episode;

}
