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
        ARSummary arSummary = ledgerService.getArSummary(userID, invoiceID, startDate, endDate);
        return arSummary;
    }



}
