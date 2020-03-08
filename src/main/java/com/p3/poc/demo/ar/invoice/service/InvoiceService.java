package com.p3.poc.demo.ar.invoice.service;

import com.p3.poc.demo.ar.customexceptions.InvoiceNotFoundException;
import com.p3.poc.demo.ar.invoice.entity.Invoice;
import com.p3.poc.demo.ar.invoice.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Created by Suriyanarayanan K
 * on 08/03/20 11:33 PM.
 */

@Service
public class InvoiceService {

    @Autowired
    InvoiceRepository invoiceRepository;

    public List<Invoice> findAllInvoices() {
        return invoiceRepository.findAll();
    }

    public Optional<Invoice> findInvoiceById(Long id) {
        return invoiceRepository.findById(id);
    }

    public Invoice saveInvoice(Invoice invoice) {
        return invoiceRepository.save(invoice);
    }

    public void deleteInvoiceById(Long id) {
        invoiceRepository.findById(id).orElseThrow(InvoiceNotFoundException::new);
        invoiceRepository.deleteById(id);
    }

    public Invoice updateInvoiceById(Long id, Invoice invoice) {
        Invoice invoiceToUpdate = invoiceRepository.findById(id).orElseThrow(InvoiceNotFoundException::new);
        invoiceToUpdate.setInvoiceDate(invoice.getInvoiceDate());
        invoiceToUpdate.setInvoiceTotal(invoice.getInvoiceTotal());
        invoiceToUpdate.setLedgers(invoice.getLedgers());
        invoiceToUpdate.setOrders(invoice.getOrders());
        invoiceToUpdate.setPayment(invoice.getPayment());
        invoiceToUpdate.setUsers(invoice.getUsers());
        invoiceRepository.save(invoiceToUpdate);
        return invoiceToUpdate;
    }
}
