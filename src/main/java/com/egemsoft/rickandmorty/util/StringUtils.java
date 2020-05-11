package com.egemsoft.rickandmorty.util;

public class StringUtils {

    public static String[] splitString(String str) {
        return str.replace(",", "").split(" ");
    }
}
