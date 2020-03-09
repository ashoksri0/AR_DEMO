package com.p3.poc.demo.ar.arsummary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;

/**
 * Created by Ashok Kumar N
 * on 09/03/20.
 */
@RestController
@RequestMapping("/ar-summary")
public class ARController {
    @Autowired
    ARService arService;

    @GetMapping("/list/data")
    public AR360Model getSummaryDataList( @RequestParam Long userID,
                                                  @RequestParam(required = false) Long invoiceID,
                                                  @RequestParam(required = false, defaultValue = "1970-01-01") Date startDate,
                                                  @RequestParam(required = false, defaultValue = "2050-01-01") Date endDate)
    {
      return   arService.getAr360ViewLIst(userID, invoiceID, startDate, endDate);
    }

   /* @GetMapping("/list/data")
    {
        arService.getArSummaryList();
    }*/
}
