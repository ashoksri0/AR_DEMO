package com.p3.poc.demo.ar.arsummary;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Created by Ashok Kumar N
 * on 09/03/20.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DataSetBean {
    private Date x;
    private Double y;
}
