package com.p3.poc.demo.ar.ledger.controller;

import com.p3.poc.demo.ar.customexceptions.LedgerNotFoundException;
import com.p3.poc.demo.ar.invoice.entity.Invoice;
import com.p3.poc.demo.ar.ledger.entity.Ledger;
import com.p3.poc.demo.ar.ledger.service.LedgerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Suriyanarayanan K
 * on 08/03/20 11:45 PM.
 */
@RestController
@RequestMapping("/ledger")
public class LedgerController {

    @Autowired
    LedgerService ledgerService;

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
}
