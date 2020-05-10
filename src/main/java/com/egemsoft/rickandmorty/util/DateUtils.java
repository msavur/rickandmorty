package com.egemsoft.rickandmorty.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMdd,yyyy");

    public static Date convertDate(String str) {
        Date airDate = null;
        try {
            airDate = simpleDateFormat.parse(str.replace(" ", ""));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return airDate;
    }
}