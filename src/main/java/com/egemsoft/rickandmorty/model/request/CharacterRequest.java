package com.egemsoft.rickandmorty.model.request;


import com.egemsoft.rickandmorty.enums.GenderEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CharacterRequest {

    private String status;
    private String species;
    private String type;
    private GenderEnum gender;

}
