package com.p3.poc.demo.ar.ledger.service;

import com.p3.poc.demo.ar.invoice_details.model.ARSummary;
import com.p3.poc.demo.ar.ledger.enums.TransactionMode;
import com.p3.poc.demo.ar.utils.exceptions.LedgerNotFoundException;
import com.p3.poc.demo.ar.ledger.entity.Ledger;
import com.p3.poc.demo.ar.ledger.repository.LedgerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Date;
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

    public ARSummary getArSummary(@RequestParam final Long userID, @RequestParam(required = false) final Long invoiceID, @RequestParam(required =
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
