package com.p3.poc.demo.ar.invoice_details.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
 public class InvoiceDetails {
    private Long invoiceID;
    private Date purchaseDate;
    private Double  totalCost;
    private Double  amountPaid;
    private Double  amountDue;
}
