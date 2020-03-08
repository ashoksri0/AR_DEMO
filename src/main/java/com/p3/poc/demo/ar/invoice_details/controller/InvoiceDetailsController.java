package com.p3.poc.demo.ar.invoice_details.controller;

import com.p3.poc.demo.ar.invoice.entity.Invoice;
import com.p3.poc.demo.ar.invoice.repository.InvoiceRepository;
import com.p3.poc.demo.ar.invoice.service.InvoiceService;
import com.p3.poc.demo.ar.invoice_details.model.ARSummary;
import com.p3.poc.demo.ar.invoice_details.model.InvoiceDetails;
import com.p3.poc.demo.ar.ledger.entity.Ledger;
import com.p3.poc.demo.ar.ledger.enums.TransactionMode;
import com.p3.poc.demo.ar.ledger.repository.LedgerRepository;
import com.p3.poc.demo.ar.order.OrderModel;
import com.p3.poc.demo.ar.order.entity.Orders;
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
import java.util.function.Function;
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
    @Autowired
    InvoiceService invoiceService;
    @GetMapping
    public List<InvoiceDetails> getUserInvoice(@RequestParam Long userID,
                                               @RequestParam(required = false, defaultValue = "1970-01-01") Date startDate,
                                               @RequestParam(required = false, defaultValue = "2050-01-01") Date endDate,
                                               @RequestParam(required = false, defaultValue = "false") Boolean sort,
                                               @RequestParam(defaultValue = "0") Integer pageNo,
                                               @RequestParam(defaultValue = "10") Integer pageSize) {
        Pageable paging = PageRequest.of(pageNo, pageSize);
        List<InvoiceDetails> invoiceDetailsList = invoiceService.getInvoiceDetails(userID, startDate, endDate, sort, paging);
        return invoiceDetailsList;
    }



    @GetMapping("/order/{invoiceId}")
    public List<OrderModel> getOrderByInvoice(@PathVariable Long invoiceId) {
        return ordersRepository.findAllByInvoice_Id(invoiceId).stream().map(getOrdersOrderModelFunction()).collect(Collectors.toList());
    }

    private Function<Orders, OrderModel> getOrdersOrderModelFunction() {
        return orders -> {
            return OrderModel.builder().orderDate(orders.getOrderDate())
                    .orderName(orders.getOrderName())
                    .orderPrice(orders.getOrderPrice())
                    .orderQuantity(orders.getOrderQuantity()).build();
        };
    }


}
