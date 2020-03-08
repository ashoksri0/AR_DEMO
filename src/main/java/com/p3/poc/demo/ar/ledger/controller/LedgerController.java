package com.p3.poc.demo.ar.ledger.controller;

import com.p3.poc.demo.ar.invoice_details.model.ARSummary;
import com.p3.poc.demo.ar.ledger.enums.TransactionMode;
import com.p3.poc.demo.ar.ledger.repository.LedgerRepository;
import com.p3.poc.demo.ar.utils.exceptions.LedgerNotFoundException;
import com.p3.poc.demo.ar.ledger.entity.Ledger;
import com.p3.poc.demo.ar.ledger.service.LedgerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

/**
 * Created by Suriyanarayanan K
 * on 08/03/20 11:45 PM.
 */
@RestController
@RequestMapping("/ledger")
public class LedgerController {

    @Autowired
    LedgerService ledgerService;

    @Autowired
    LedgerRepository ledgerRepository;

    @GetMapping("/")
    public Iterable<Ledger> getLedgers() {
        return ledgerService.findAllLedgers();
    }

    @GetMapping("/{id}")
    public Ledger getLedger(@PathVariable Long id) {
        return ledgerService.findLedgerById(id).orElseThrow(LedgerNotFoundException::new);
    }

    @PostMapping("/")
    public Ledger addLedger(@RequestBody Ledger ledger) {
        return ledgerService.saveLedger(ledger);
    }

    @PutMapping("/{id}")
    public Ledger updateLedger(@PathVariable Long id, @RequestBody  Ledger ledger) {
        return ledgerService.updateLedgerById(id, ledger);
    }

    @DeleteMapping("/{id}")
    public void deleteLedger(@PathVariable Long id) {
        ledgerService.deleteLedgerById(id);
    }


    @GetMapping("/summary")
    public ARSummary getInvoiceSummary(
            @RequestParam Long userID,
            @RequestParam(required = false) Long invoiceID,
            @RequestParam(required = false, defaultValue = "1970-01-01") Date startDate,
            @RequestParam(required = false, defaultValue = "2050-01-01") Date endDate
    ) {
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
