package com.p3.poc.demo.ar.invoice.service;

import com.p3.poc.demo.ar.invoice.entity.Invoice;
import com.p3.poc.demo.ar.invoice.repository.InvoiceRepository;
import com.p3.poc.demo.ar.invoice_details.model.InvoiceDetails;
import com.p3.poc.demo.ar.order.entity.Orders;
import com.p3.poc.demo.ar.payment.entity.Payment;
import com.p3.poc.demo.ar.utils.exceptions.InvoiceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public List<InvoiceDetails> getInvoiceDetails(@RequestParam final Long userID,
                                                  @RequestParam(required = false, defaultValue = "1970-01-01") final Date startDate, @RequestParam(required = false, defaultValue = "2050-01-01") final Date endDate, @RequestParam(required = false, defaultValue = "false") final Boolean sort, final Pageable paging) {
        List<Invoice> invoiceList;
        if (sort) {
            invoiceList = invoiceRepository.findAllByUsers_IdAndInvoiceDateBetweenOrderByInvoiceDate(userID, startDate, endDate, paging);

        } else {
            invoiceList = invoiceRepository.findAllByUsers_IdAndInvoiceDateBetweenOrderByInvoiceDateDesc(userID, startDate, endDate, paging);
        }

        return invoiceList.stream().map(invoice -> {
            InvoiceDetails invoiceDetails = InvoiceDetails.builder().invoiceID(invoice.getId())
                    .purchaseDate(invoice.getInvoiceDate()).build();
            setAmountDetails(invoiceDetails, invoice);
            return invoiceDetails;
        }).collect(Collectors.toList());
    }

    public void setAmountDetails(InvoiceDetails invoiceDetails, Invoice invoice) {
        Double amountTotal = invoice.getInvoiceTotal();
        Double amountPaid = invoice.getPayment().stream().map(Payment::getAmount).reduce(0D, Double::sum);
        invoiceDetails.setAmountPaid(amountPaid);
        invoiceDetails.setAmountDue(amountTotal - amountPaid);
        invoiceDetails.setTotalCost(amountTotal);
    }
}
