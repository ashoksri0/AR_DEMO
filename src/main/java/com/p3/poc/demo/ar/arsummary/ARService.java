package com.p3.poc.demo.ar.arsummary;

import com.p3.poc.demo.ar.invoice_details.model.ARSummary;
import com.p3.poc.demo.ar.ledger.entity.Ledger;
import com.p3.poc.demo.ar.ledger.enums.TransactionMode;
import com.p3.poc.demo.ar.ledger.repository.LedgerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static com.p3.poc.demo.ar.ledger.enums.TransactionMode.AMOUNT_RECEIVABLE;
import static com.p3.poc.demo.ar.ledger.enums.TransactionMode.AMOUNT_RECEIVED;

/**
 * Created by Ashok Kumar N
 * on 09/03/20.
 */

@Service
public class ARService {
    @Autowired
    LedgerRepository ledgerRepository;

    public AR360Model getAr360ViewLIst(@RequestParam final Long userID, @RequestParam(required = false) final Long invoiceID,
                                       @RequestParam(required =
                                                  false, defaultValue = "1970-01-01") final Date startDate, @RequestParam(required = false, defaultValue = "2050-01-01") final Date endDate) {

          List<Ledger> ledgerList;
          List<DataSetBean> amount_received_list =new ArrayList();
          List<DataSetBean> amount_receivable_list = new ArrayList();


        if (invoiceID == null) {
            ledgerList =
                    ledgerRepository.findAllByUsers_IdAndTranscationDateBetweenOrderByTranscationDateDesc(userID, startDate, endDate);
        } else {
            ledgerList = ledgerRepository.findAllByUsers_IdAndInvoice_IdAndTranscationDateBetweenOrderByTranscationDateDesc(userID, invoiceID,
                    startDate, endDate);
        }
        List<LedgerModel> ledgerModelList=new ArrayList();
        ARSummary arSummary = ARSummary.builder().openingBalance(0D).totalDue(0D).totalReceivable(0D).totalReceived(0D).build();
        ledgerList.forEach(ledger -> {
            LedgerModel ledgerModel=LedgerModel.builder().date(ledger.getTranscationDate()).invoice(ledger.getInvoice().getId()).build();
            TransactionMode transactionMode = ledger.getTransactionMode();
            switch (transactionMode) {
                case AMOUNT_RECEIVED:
                   amount_received_list.add(0,DataSetBean.builder().x(ledger.getTranscationDate()).y(ledger.getTranscation()).build());
                    ledgerModel.setPaymentMode(AMOUNT_RECEIVED.toString());
                    arSummary.setTotalReceived(ledger.getTranscation() + arSummary.getTotalReceived());
                    break;
                case AMOUNT_RECEIVABLE:
                    amount_receivable_list.add(0,DataSetBean.builder().x(ledger.getTranscationDate()).y(ledger.getTranscation()).build());
                    ledgerModel.setPaymentMode(AMOUNT_RECEIVABLE.toString());
                    arSummary.setTotalReceivable(ledger.getTranscation() + arSummary.getTotalReceivable());
                    break;
            }
            ledgerModel.setAmount(ledger.getTranscation());
            ledgerModel.setBalanceReceivable(invoiceID == null ?  ledger.getUserBalance():ledger.getInvoiceBalance());
            ledgerModelList.add(ledgerModel);
        });
        if (ledgerList.size() > 0) {
            Double openingBalance = invoiceID == null ? ledgerList.get(ledgerList.size() - 1).getUserOpeningBalance() :
                    ledgerList.get(ledgerList.size() - 1).getInvoiceOpeningBalance();
            arSummary.setOpeningBalance(openingBalance);
            arSummary.setTotalReceivable(arSummary.getTotalReceivable() + openingBalance);
        }
        arSummary.setTotalDue(arSummary.getTotalReceivable() - arSummary.getTotalReceived());
        if (amount_receivable_list.size()==0) {
            amount_receivable_list.add(DataSetBean.builder().x(startDate).y(arSummary.getOpeningBalance()).build());
        }
        return AR360Model.builder()
                .ledgerModelList(ledgerModelList)
                .amount_receivable_list(amount_receivable_list)
                .amount_received_list(amount_received_list)
                .arSummary(arSummary)
                .build();
    }


}
