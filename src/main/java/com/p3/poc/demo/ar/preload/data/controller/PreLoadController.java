package com.p3.poc.demo.ar.preload.data.controller;

import com.p3.poc.demo.ar.DateUtils;
import com.p3.poc.demo.ar.invoice.entity.Invoice;
import com.p3.poc.demo.ar.invoice.repository.InvoiceRepository;
import com.p3.poc.demo.ar.preload.data.ConfigSetting;
import com.p3.poc.demo.ar.preload.data.service.PreLoadService;
import com.p3.poc.demo.ar.user.entity.Users;
import com.p3.poc.demo.ar.user.repository.UserRepository;
import org.ajbrown.namemachine.Name;
import org.ajbrown.namemachine.NameGenerator;
import org.ajbrown.namemachine.NameGeneratorOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

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
    private InvoiceRepository invoiceRepository;
    @Autowired
    PreLoadService preLoadService;

    @PostMapping(path = "/generate")
    public String saveUsers(@RequestBody ConfigSetting configSetting) throws Exception {
        NameGeneratorOptions options = new NameGeneratorOptions();

//Get deterministic results by setting a random seed.
        options.setGenderWeight( 50 );
        NameGenerator generator = new NameGenerator( options );
        List<Name> nameList = generator.generateNames(configSetting.getUserCount());
        for (Integer i = 0; i < configSetting.getUserCount(); i++) {
            Name name = nameList.get(i);
            Users users = new Users();
            users.setFirstName(name.getFirstName());
            users.setDob(new Date());
            users.setDoj(new Date());
            users.setEmail(name.getFirstName()+"@gmail.com");
            users.setLastName(name.getLastName());
            users = userRepository.save(users);
        }

/*

        Invoice invoice = new Invoice();
        invoice.setUsers(users);
        invoice.setInvoiceDate(new Date(DateUtils.createRandomDate(1, 6).toEpochDay()));
        invoice.setInvoiceTotal(DateUtils.getRandomINrange(100000, 3000000));
        invoice = invoiceRepository.save(invoice);
*/

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
