package com.p3.poc.demo.ar.invoice.controller;

import com.p3.poc.demo.ar.utils.exceptions.UserNotFoundException;
import com.p3.poc.demo.ar.invoice.entity.Invoice;
import com.p3.poc.demo.ar.invoice.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Suriyanarayanan K
 * on 08/03/20 11:35 PM.
 */
@RestController
@RequestMapping("/invoice")
public class InvoiceController {

    @Autowired
    InvoiceService invoiceService;

    @GetMapping("/")
    public Iterable<Invoice> getInvoices() {
        return invoiceService.findAllInvoices();
    }

    @GetMapping("/{id}")
    public Invoice getInvoice(@PathVariable Long id) {
        return invoiceService.findInvoiceById(id).orElseThrow(UserNotFoundException::new);
    }

    @PostMapping("/")
    public Invoice addInvoice(@RequestBody Invoice invoice) {
        return invoiceService.saveInvoice(invoice);
    }

    @PutMapping("/{id}")
    public Invoice updateInvoice(@PathVariable Long id, @RequestBody Invoice invoice) {
        return invoiceService.updateInvoiceById(id, invoice);
    }

    @DeleteMapping("/{id}")
    public void deleteInvoice(@PathVariable Long id) {
        invoiceService.deleteInvoiceById(id);
    }
}
