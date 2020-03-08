package com.p3.poc.demo.ar.ledger.service;

import com.p3.poc.demo.ar.customexceptions.LedgerNotFoundException;
import com.p3.poc.demo.ar.ledger.entity.Ledger;
import com.p3.poc.demo.ar.ledger.repository.LedgerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Created by Suriyanarayanan K
 * on 08/03/20 11:47 PM.
 */
@Service
public class LedgerService {

    @Autowired
    LedgerRepository ledgerRepository;

    public List<Ledger> findAllLedgers() {
        return ledgerRepository.findAll();
    }

    public Optional<Ledger> findLedgerById(Long id) {
        return ledgerRepository.findById(id);
    }

    public Ledger saveLedger(Ledger ledger) {
        return ledgerRepository.save(ledger);
    }

    public void deleteLedgerById(Long id) {
        ledgerRepository.findById(id).orElseThrow(LedgerNotFoundException::new);
        ledgerRepository.deleteById(id);
    }

    public Ledger updateLedgerById(Long id, Ledger ledger) {
        Ledger ledgerToUpdate = ledgerRepository.findById(id).orElseThrow(LedgerNotFoundException::new);
        ledgerToUpdate.setInvoice(ledger.getInvoice());
        ledgerToUpdate.setInvoiceBalance(ledger.getInvoiceBalance());
        ledgerToUpdate.setPayment(ledger.getPayment());
        ledgerToUpdate.setTransactionMode(ledger.getTransactionMode());
        ledgerToUpdate.setTranscation(ledger.getTranscation());
        ledgerToUpdate.setTranscationDate(ledger.getTranscationDate());
        ledgerToUpdate.setUserBalance(ledger.getUserBalance());
        ledgerToUpdate.setUsers(ledger.getUsers());
        ledgerRepository.save(ledgerToUpdate);
        return ledgerToUpdate;
    }
}
