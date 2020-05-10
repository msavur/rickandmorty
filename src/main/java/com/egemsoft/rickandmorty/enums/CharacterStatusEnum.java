package com.egemsoft.rickandmorty.enums;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum CharacterStatusEnum {
    ALIVE("Alive"),
    DEAD("Dead"),
    UNKNOWN("unknown");

    private String value;

    CharacterStatusEnum(String value) {
        this.value = value;
    }

    public static CharacterStatusEnum findCharacterStatusEnum(String value){
        return Arrays.stream(CharacterStatusEnum.values())
                .filter(d -> d.getValue().equals(value))
                .findFirst()
                .orElseThrow(() -> new UnsupportedOperationException("Character Status  enum not found"));
    }
}
