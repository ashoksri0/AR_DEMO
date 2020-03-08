package com.p3.poc.demo.ar.invoice_details.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ARSummary {
    private Double openingBalance = 0D;
    private Double totalReceivable = 0D;
    private Double totalReceived = 0D;
    private Double totalDue = 0D;
}
