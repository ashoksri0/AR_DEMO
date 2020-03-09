package com.p3.poc.demo.ar.preload.data.controller;

import com.p3.poc.demo.ar.invoice.entity.Invoice;
import com.p3.poc.demo.ar.ledger.entity.Ledger;
import com.p3.poc.demo.ar.ledger.enums.TransactionMode;
import com.p3.poc.demo.ar.order.entity.Orders;
import com.p3.poc.demo.ar.payment.entity.Payment;
import com.p3.poc.demo.ar.payment.enums.PaymentMode;
import com.p3.poc.demo.ar.user.entity.Users;
import com.p3.poc.demo.ar.user.model.UsersModel;
import com.p3.poc.demo.ar.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.crypto.Data;
import java.util.Date;

/**
 * Created by Ashok Kumar N
 * on 09/03/20.
 */

@RestController("/preload-data")
public class PreLoadController {

    @Autowired
    private UserRepository userRepository;

    /*@GetMapping
    public String loadData()
    {

        return "success";
    }*/

    @PostMapping(path = "/save/all/users/",consumes = MediaType.APPLICATION_JSON_VALUE)
    public String saveUsers(){


        Users users = new Users();
        users.setFirstName("Selva");
        users.setDob(new Date());
        users.setDoj(new Date());
        users.setEmail("test@gmail.com");
        users.setLastName("test");


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


        // Invoice Create Code

        Invoice invoice = new Invoice();
        invoice.setUsers(users);
        invoice.setInvoiceDate(new Date());
        invoice.setInvoiceTotal(12000d);

       // ledger.setPayment(12000d);

        // Order Create code



        invoice.setLedgers();
        invoice.setOrders();
        invoice.setPayment();
        invoice.setUsers();


        Orders orders = new Orders();
        orders.setInvoice();
        orders.setOrderDate();
        orders.setOrderName();
        orders.setOrderPrice();
        orders.setOrderQuantity();
        orders.setOrderStatus();

        userRepository.save(users);


        return "succcess";
    }

}
