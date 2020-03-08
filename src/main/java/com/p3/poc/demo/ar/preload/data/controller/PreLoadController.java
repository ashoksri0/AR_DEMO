package com.p3.poc.demo.ar.preload.data.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Ashok Kumar N
 * on 09/03/20.
 */

@RestController("/preload-data")
public class PreLoadController {


    @GetMapping
    public String loadData()
    {

        return "success";
    }

}
