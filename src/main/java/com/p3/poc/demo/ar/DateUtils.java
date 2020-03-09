package com.p3.poc.demo.ar;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Ashok Kumar N
 * on 09/03/20.
 */

public class DateUtils {

    public static Date createRandomDate(int startYear, int endYear) {
        int day = createRandomIntBetween(1, 28);
        int month = createRandomIntBetween(1, 11);
        int year = createRandomIntBetween(startYear, endYear);
        return Date.from(LocalDate.of(year, month, day).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }


    public static int createRandomIntBetween(int start, int end) {
        return start + (int) Math.round(Math.random() * (end - start));
    }
    public static double getRandomINrange(double min, double max) {
        return (Math.random() * (max + 1 - min)) + min;
    }
    public static  Set<Integer> getRandomINrange(double min, double max,int count) {
        Set<Integer> doubles=new HashSet();
        for (int i = 0; i < count; i++) {
            doubles.add(createRandomIntBetween((int)min,(int)max));
        }
           return doubles;
    }

}
