package com.p3.poc.demo.ar.invoice_details.controller;

import com.p3.poc.demo.ar.invoice.entity.Invoice;
import com.p3.poc.demo.ar.invoice.repository.InvoiceRepository;
import com.p3.poc.demo.ar.invoice_details.model.InvoiceDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/invoice-details")
public class InvoiceDetailsController {
 @Autowired
 InvoiceRepository invoiceRepository;
    @GetMapping("/{userID}")
    public List<InvoiceDetails> getUserInvoice(@PathVariable Long userID)
    {
        List<Invoice> invoiceList = invoiceRepository.findAllByUsers_Id(userID);

        List<InvoiceDetails> invoiceDetailsList = invoiceList.stream().map(invoice -> {
            InvoiceDetails invoiceDetails = InvoiceDetails.builder().invoiceID(invoice.getId())
                    .purchaseDate(invoice.getInvoiceDate()).build();
            return invoiceDetails;
        }).collect(Collectors.toList());
        return invoiceDetailsList;
    }

}
