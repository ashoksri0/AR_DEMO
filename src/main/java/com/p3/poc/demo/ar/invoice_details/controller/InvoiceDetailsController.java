package com.p3.poc.demo.ar.invoice_details.controller;

import com.p3.poc.demo.ar.invoice.entity.Invoice;
import com.p3.poc.demo.ar.invoice.repository.InvoiceRepository;
import com.p3.poc.demo.ar.invoice_details.model.ARSummary;
import com.p3.poc.demo.ar.invoice_details.model.InvoiceDetails;
import com.p3.poc.demo.ar.ledger.entity.Ledger;
import com.p3.poc.demo.ar.ledger.enums.TransactionMode;
import com.p3.poc.demo.ar.ledger.repository.LedgerRepository;
import com.p3.poc.demo.ar.order.OrderModel;
import com.p3.poc.demo.ar.order.repository.OrdersRepository;
import com.p3.poc.demo.ar.order.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/invoice-details")
public class InvoiceDetailsController {
    @Autowired
    InvoiceRepository invoiceRepository;
    @Autowired
    OrdersRepository ordersRepository;
    @Autowired
    OrdersService ordersService;

    @Autowired
    LedgerRepository ledgerRepository;

    @GetMapping
    public List<InvoiceDetails> getUserInvoice(@RequestParam Long userID,
                                               @RequestParam(required = false, defaultValue = "1970-01-01") Date startDate,
                                               @RequestParam(required = false, defaultValue = "2050-01-01") Date endDate,
                                               @RequestParam(required = false, defaultValue = "false") Boolean sort,
                                               @RequestParam(defaultValue = "0") Integer pageNo,
                                               @RequestParam(defaultValue = "10") Integer pageSize) {
        Pageable paging = PageRequest.of(pageNo, pageSize);
        List<Invoice> invoiceList;
        if (sort) {
            invoiceList = invoiceRepository.findAllByUsers_IdAndInvoiceDateBetweenOrderByInvoiceDate(userID, startDate, endDate, paging);

        } else {
            invoiceList = invoiceRepository.findAllByUsers_IdAndInvoiceDateBetweenOrderByInvoiceDateDesc(userID, startDate, endDate, paging);
        }

        List<InvoiceDetails> invoiceDetailsList = invoiceList.stream().map(invoice -> {
            InvoiceDetails invoiceDetails = InvoiceDetails.builder().invoiceID(invoice.getId())
                    .purchaseDate(invoice.getInvoiceDate()).build();
            ordersService.setAmountDetails(invoiceDetails, invoice);
            return invoiceDetails;
        }).collect(Collectors.toList());
        return invoiceDetailsList;
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


    @GetMapping("/order/{invoiceId}")
    public List<OrderModel> getOrderByInvoice(@PathVariable Long invoiceId) {
        return ordersRepository.findAllByInvoice_Id(invoiceId).stream().map(orders -> {
            return OrderModel.builder().orderDate(orders.getOrderDate())
                    .orderName(orders.getOrderName())
                    .orderPrice(orders.getOrderPrice())
                    .orderQuantity(orders.getOrderQuantity()).build();
        }).collect(Collectors.toList());
    }


}
