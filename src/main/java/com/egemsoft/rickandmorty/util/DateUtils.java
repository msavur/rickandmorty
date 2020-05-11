package com.egemsoft.rickandmorty.util;

import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static java.time.Month.APRIL;
import static java.time.Month.AUGUST;
import static java.time.Month.DECEMBER;
import static java.time.Month.FEBRUARY;
import static java.time.Month.JANUARY;
import static java.time.Month.JULY;
import static java.time.Month.JUNE;
import static java.time.Month.MARCH;
import static java.time.Month.MAY;
import static java.time.Month.NOVEMBER;
import static java.time.Month.OCTOBER;
import static java.time.Month.SEPTEMBER;

@Slf4j
public class DateUtils {

    private final static String ZERO = "0";

    public static Date convertDate(String str) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        Date date = null;
        String[] strings = StringUtils.splitString(str);
        try {
            date = formatter.parse(contactStringDate(strings));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    private static String contactStringDate(String[] strings) {
        String monthCalculator = getMonthCalculator(strings[0]);
        String day = strings[1].length() == 1 ? ZERO + strings[1] : strings[1];
        return monthCalculator + "-" + day + "-" + strings[2];
    }

    private static String getMonthCalculator(String month) {
        int value = 1;
        switch (month.toUpperCase()) {
            case "JANUARY":
                value = JANUARY.getValue();
                break;
            case "FEBRUARY":
                value = FEBRUARY.getValue();
                break;
            case "MARCH":
                value = MARCH.getValue();
                break;
            case "APRIL":
                value = APRIL.getValue();
                break;
            case "MAY":
                value = MAY.getValue();
                break;
            case "JUNE":
                value = JUNE.getValue();
                break;
            case "JULY":
                value = JULY.getValue();
                break;
            case "AUGUST":
                value = AUGUST.getValue();
                break;
            case "SEPTEMBER":
                value = SEPTEMBER.getValue();
                break;
            case "OCTOBER":
                value = OCTOBER.getValue();
                break;
            case "NOVEMBER":
                value = NOVEMBER.getValue();
                break;
            case "DECEMBER":
                value = DECEMBER.getValue();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + month.toUpperCase());
        }
        if (value < 10)
            return ZERO + value;
        return String.valueOf(value);
    }
}