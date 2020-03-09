package com.p3.poc.demo.ar.arsummary;

import com.p3.poc.demo.ar.invoice_details.model.ARSummary;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created by Ashok Kumar N
 * on 09/03/20.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class AR360Model {

    private ARSummary arSummary;
    private List<LedgerModel> ledgerModelList;

}
