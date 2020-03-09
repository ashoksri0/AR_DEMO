package com.p3.poc.demo.ar.arsummary;

import com.p3.poc.demo.ar.invoice_details.model.ARSummary;
import com.p3.poc.demo.ar.ledger.entity.Ledger;
import com.p3.poc.demo.ar.ledger.enums.TransactionMode;
import com.p3.poc.demo.ar.ledger.repository.LedgerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

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
            LedgerModel ledgerModel=LedgerModel.builder().date(ledger.getTranscationDate()).build();
            TransactionMode transactionMode = ledger.getTransactionMode();
            switch (transactionMode) {
                case AMOUNT_RECEIVED:
                    ledgerModel.setPaymentMode(AMOUNT_RECEIVED.toString());
                    arSummary.setTotalReceived(ledger.getTranscation() + arSummary.getTotalReceived());
                    break;
                case AMOUNT_RECEIVABLE:
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
        return AR360Model.builder().ledgerModelList(ledgerModelList).arSummary(arSummary).build();
    }

    public ARSummary getArSummaryForChart(@RequestParam final Long userID, @RequestParam(required = false) final Long invoiceID,
                                        @RequestParam(required =
            false, defaultValue = "1970-01-01") final Date startDate, @RequestParam(required = false, defaultValue = "2050-01-01") final Date endDate) {
        List<Ledger> ledgerList;
        if (invoiceID == null) {
            ledgerList =
                    ledgerRepository.findAllByUsers_IdAndTranscationDateBetweenOrderByTranscationDateDesc(userID, startDate, endDate);
        } else {
            ledgerList = ledgerRepository.findAllByUsers_IdAndInvoice_IdAndTranscationDateBetweenOrderByTranscationDateDesc(userID, invoiceID,
                    startDate, endDate);
        }
        ARSummary arSummary = ARSummary.builder().openingBalance(0D).totalDue(0D).totalReceivable(0D).totalReceived(0D).build();
        ledgerList.forEach(ledger -> {
            TransactionMode transactionMode = ledger.getTransactionMode();
            switch (transactionMode) {
                case AMOUNT_RECEIVED:
                    arSummary.setTotalReceived(ledger.getTranscation() + arSummary.getTotalReceived());
                    break;
                case AMOUNT_RECEIVABLE:
                    arSummary.setTotalReceivable(ledger.getTranscation() + arSummary.getTotalReceivable());
                    break;
            }
        });

        if (ledgerList.size() > 0) {
            Double openingBalance = invoiceID == null ? ledgerList.get(ledgerList.size() - 1).getUserOpeningBalance() :
                    ledgerList.get(ledgerList.size() - 1).getInvoiceOpeningBalance();
            arSummary.setOpeningBalance(openingBalance);
            arSummary.setTotalReceivable(arSummary.getTotalReceivable() + openingBalance);
        }
        arSummary.setTotalDue(arSummary.getTotalReceivable() - arSummary.getTotalReceived());
        return arSummary;
    }

}
