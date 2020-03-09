package com.p3.poc.demo.ar.preload.data.controller;

import com.p3.poc.demo.ar.DateUtils;
import com.p3.poc.demo.ar.invoice.entity.Invoice;
import com.p3.poc.demo.ar.user.entity.Users;
import com.p3.poc.demo.ar.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * Created by Ashok Kumar N
 * on 09/03/20.
 */

@RestController
@RequestMapping("/preload-data")
public class PreLoadController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    DateUtils dateUtils;

    @GetMapping(path = "/generate")
    public String saveUsers(){
        Users users = new Users();
        users.setFirstName("Selva");
        users.setDob(new Date());
        users.setDoj(new Date());
        users.setEmail("test@gmail.com");
        users.setLastName("test");
        users= userRepository.save(users);

        Invoice invoice = new Invoice();
        invoice.setUsers(users);
        System.out.println(" date "+dateUtils.getRandomDate(new Date("2020-01-01"),new Date("2020-04-01")));
        invoice.setInvoiceDate(dateUtils.getRandomDate(new Date("2020-01-01"),new Date("2020-04-01")));
        invoice.setInvoiceTotal(dateUtils.getRandomINrange(100000,3000000));


/*
     // Ledger Create Code

        Ledger ledger =new Ledger();
        ledger.setInvoice();
        ledger.setInvoiceBalance(5000d);
        ledger.setTransactionMode(TransactionMode.AMOUNT_RECEIVED);
        ledger.setInvoiceOpeningBalance();
        ledger.setUserOpeningBalance();
        //ledger.setPayment(12000d);
        ledger.setTranscation(2000d);
        ledger.setUserBalance(1000d);
        ledger.setUsers(users);
        ledger.setTranscationDate(new Date());


        // Payment  Create Code

        Payment payment = new Payment();
        payment.setAmount(15000d);
        payment.setPaymentMode(PaymentMode.CHEQUE);
        payment.setPaymentReceivedDate(new Date());
        payment.setInvoice(invoice);
        payment.setLedger();
        payment.setPaymentCreatedDate();




        Orders orders = new Orders();
        orders.setInvoice();
        orders.setOrderDate();
        orders.setOrderName();
        orders.setOrderPrice();
        orders.setOrderQuantity();
        orders.setOrderStatus();

        userRepository.save(users);*/


        return "succcess";
    }

}
