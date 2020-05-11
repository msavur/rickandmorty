package com.egemsoft.rickandmorty.util;

import lombok.extern.slf4j.Slf4j;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
public class DateUtils {

    public static Date convertDate(String str) {
        DateFormat formatter = new SimpleDateFormat("MMM dd, yyyy");
        Date date = null;
        try {
            date = formatter.parse(str);
        } catch (ParseException e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
        return date;
    }
}