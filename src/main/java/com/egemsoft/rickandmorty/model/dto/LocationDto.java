package com.egemsoft.rickandmorty.model.dto;


import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class LocationDto {
    private Long id;
    private String name;
    private String type;
    private String dimension;
    private List<String> residents;
    private String url;
    private Date created;
}
