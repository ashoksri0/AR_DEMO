package com.p3.poc.demo.ar.arsummary;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.p3.poc.demo.ar.invoice_details.model.ARSummary;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private List<DataSetBean> amount_received_list;
    private List<DataSetBean> amount_receivable_list;

}
