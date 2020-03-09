package com.p3.poc.demo.ar;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Ashok Kumar N
 * on 09/03/20.
 */

public class DateUtils {

    public static LocalDate createRandomDate(int startMonth, int endMonth) {
        int day = createRandomIntBetween(1, 28);
        int month = createRandomIntBetween(startMonth, endMonth);
        int year = createRandomIntBetween(2020, 2020);
        return LocalDate.of(year, month, day);
    }


    private static int createRandomIntBetween(int start, int end) {
        return start + (int) Math.round(Math.random() * (end - start));
    }
    public static double getRandomINrange(double min, double max) {
        return (Math.random() * (max + 1 - min)) + min;
    }

}
