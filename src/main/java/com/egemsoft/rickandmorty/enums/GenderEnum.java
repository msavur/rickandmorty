package com.egemsoft.rickandmorty.enums;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum GenderEnum {
    MALE("Male"),
    FEMALE("Female"),
    UNKNOWN("unknown");

    private String value;

    GenderEnum(String value) {
        this.value = value;
    }

    public static GenderEnum findGenderEnum(String value) {
        return Arrays.stream(GenderEnum.values())
                .filter(d -> d.getValue().equals(value))
                .findFirst()
                .orElse(GenderEnum.UNKNOWN);
    }

}
