package com.p3.poc.demo.ar;

import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Ashok Kumar N
 * on 09/03/20.
 */
@Component
public class DateUtils {

    public Date getRandomDate(Date d1,Date d2)
    {
        Date randomDate = new Date(ThreadLocalRandom.current()
                .nextLong(d1.getTime(), d2.getTime()));
        return randomDate;
    }


    public  double getRandomINrange(double min, double max) {
        return (Math.random() * (max + 1 - min)) + min;
    }

}
