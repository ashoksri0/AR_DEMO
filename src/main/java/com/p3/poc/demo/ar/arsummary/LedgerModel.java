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
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LedgerModel {
    private Date date;
    private String paymentMode;
    private Double amount;
    private Double balanceReceivable;
    private Long invoice;
}
