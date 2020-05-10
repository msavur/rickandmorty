package com.egemsoft.rickandmorty.model.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageableDto {
    private int count;
    private int pages;
    private String next;
    private String prev;
}
