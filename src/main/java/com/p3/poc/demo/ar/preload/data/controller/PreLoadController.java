package com.p3.poc.demo.ar.preload.data.controller;

import com.p3.poc.demo.ar.DateUtils;
import com.p3.poc.demo.ar.invoice.entity.Invoice;
import com.p3.poc.demo.ar.invoice.repository.InvoiceRepository;
import com.p3.poc.demo.ar.ledger.entity.Ledger;
import com.p3.poc.demo.ar.ledger.enums.TransactionMode;
import com.p3.poc.demo.ar.ledger.repository.LedgerRepository;
import com.p3.poc.demo.ar.order.entity.Orders;
import com.p3.poc.demo.ar.order.repository.OrdersRepository;
import com.p3.poc.demo.ar.payment.entity.Payment;
import com.p3.poc.demo.ar.payment.enums.PaymentMode;
import com.p3.poc.demo.ar.payment.repository.PaymentRepository;
import com.p3.poc.demo.ar.preload.data.ConfigSetting;
import com.p3.poc.demo.ar.preload.data.service.PreLoadService;
import com.p3.poc.demo.ar.user.entity.Users;
import com.p3.poc.demo.ar.user.repository.UserRepository;
import org.ajbrown.namemachine.Name;
import org.ajbrown.namemachine.NameGenerator;
import org.ajbrown.namemachine.NameGeneratorOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by Ashok Kumar N
 * on 09/03/20.
 */

@RestController
@RequestMapping("/preload-data")
public class PreLoadController {

    @Autowired
    PreLoadService preLoadService;
    PaymentMode[] paymentModes = PaymentMode.values();
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrdersRepository ordersRepository;
    @Autowired
    private LedgerRepository ledgerRepository;
    @Autowired
    private InvoiceRepository invoiceRepository;
    @Autowired
    private PaymentRepository paymentRepository;
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    @PostMapping(path = "/generate", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String saveUsers(@RequestBody ConfigSetting configSetting) throws Exception {
        NameGeneratorOptions options = new NameGeneratorOptions();
        options.setGenderWeight(50);
        NameGenerator generator = new NameGenerator(options);
        List<Name> nameList = generator.generateNames(configSetting.getUserCount());
        preLoadService.getOrder();
        List<String> productKeys = new ArrayList(preLoadService.getProductsMap().keySet());
        Map<String, Double> productsMap = preLoadService.getProductsMap();
        savingInvoices(configSetting, nameList, productKeys, productsMap);
        return "success";
    }

    private void savingInvoices(@RequestBody final ConfigSetting configSetting, final List<Name> nameList, final List<String> productKeys,
                                final Map<String, Double> productsMap) {
        for (Integer i = 0; i < configSetting.getUserCount(); i++) {
            Name name = nameList.get(i);
            Users users = new Users();
            users.setFirstName(name.getFirstName());
            System.out.println("date " + DateUtils.createRandomDate(1980, 2005));
            users.setDob(DateUtils.createRandomDate(1980, 2005));
            users.setDoj(DateUtils.createRandomDate(2008, 2012));
            users.setEmail(name.getFirstName() + "@gmail.com");
            users.setLastName(name.getLastName());
            users = userRepository.save(users);

            savingOrders(productKeys, productsMap, users);

            List<Invoice> invoiceList = invoiceRepository.findAllByUsers_Id(users.getId());
            final Users finalUsers = users;
            invoiceList.stream().forEachOrdered(invoice -> {
                LOGGER.info(" Invoice ID {} total amount {}",invoice.getId(),invoice.getInvoiceTotal());
                Double invoiceTotalAmount = invoice.getInvoiceTotal();
                Double summingAmount = 0D;
                while (summingAmount <= invoiceTotalAmount) {
                    double amount = DateUtils.getRandomINrange(invoiceTotalAmount / 8, invoiceTotalAmount / 4);
                    summingAmount += amount;
                    if (summingAmount >= invoiceTotalAmount) {
                        break;
                    }
                    Payment payment = new Payment();
                    payment.setAmount(amount);
                    payment.setPaymentMode(paymentModes[DateUtils.createRandomIntBetween(0, paymentModes.length - 1)]);
                    payment.setPaymentReceivedDate(DateUtils.createRandomDate(invoice.getInvoiceDate().getYear() + 1901, 2019));
                    payment.setInvoice(invoice);
                    payment = paymentRepository.save(payment);
                    LOGGER.info(" Invoice ID {} payment {}",invoice.getId(),payment);
                    Ledger ledger = new Ledger();
                    ledger.setInvoice(invoice);
                    ledger.setPayment(payment);
                    ledger.setTransactionMode(TransactionMode.AMOUNT_RECEIVED);
                    ledger.setTranscation(payment.getAmount());
                    ledger.setUsers(finalUsers);
                    ledger.setTranscationDate(payment.getPaymentReceivedDate());
                    ledgerRepository.save(ledger);
                }
            });
            Map<Long, Double> invoiceMap = new TreeMap();
            Double userBalance = 0D;
            List<Ledger> ledgerList = ledgerRepository.findAllByUsers_IdOrderByTranscationDate(users.getId());

            for (final Ledger ledger : ledgerList) {
                double invoiceBalance = 0D;
                Invoice invoice = ledger.getInvoice();
                if (invoiceMap.containsKey(invoice.getId())) {
                    invoiceBalance = invoiceMap.get(invoice.getId());
                }
                ledger.setInvoiceOpeningBalance(invoiceBalance);
                ledger.setUserOpeningBalance(userBalance);
                switch (ledger.getTransactionMode()) {
                    case AMOUNT_RECEIVED:
                        userBalance -= ledger.getTranscation();
                        invoiceBalance -= ledger.getTranscation();
                        break;
                    case AMOUNT_RECEIVABLE:
                        userBalance += ledger.getTranscation();
                        invoiceBalance += ledger.getTranscation();
                        break;
                }
                ledger.setUserBalance(userBalance);
                ledger.setInvoiceBalance(invoiceBalance);
                invoiceMap.put(invoice.getId(), invoiceBalance);
                ledgerRepository.save(ledger);
            }
        }
    }

    private void savingOrders(final List<String> productKeys, final Map<String, Double> productsMap, final Users users) {
    //    for (Integer invoiceINdex = 0; invoiceINdex < DateUtils.getRandomINrange(10, 20); invoiceINdex++) {
        for (Integer invoiceINdex = 0; invoiceINdex < 5; invoiceINdex++) {
            Invoice invoice = new Invoice();
            invoice.setUsers(users);
            invoice.setInvoiceDate(DateUtils.createRandomDate(2010, 2016));
            Invoice invoice2 = invoiceRepository.save(invoice);

            Set<Integer> integerSet = DateUtils.getRandomINrange(0, 28, DateUtils.createRandomIntBetween(1, 5));
            final AtomicReference<Double> sumPrice = new AtomicReference<>(0D);
            integerSet.forEach(integer ->
            {
                int qaunt = DateUtils.createRandomIntBetween(1, 3);
                String productName = productKeys.get(integer);
                Orders orders = new Orders();
                orders.setInvoice(invoice2);
                orders.setOrderDate(invoice.getInvoiceDate());
                orders.setOrderName(productName);
                orders.setOrderPrice(productsMap.get(productName) * qaunt);
                sumPrice.updateAndGet(v -> v + orders.getOrderPrice());
                orders.setOrderQuantity(Double.parseDouble(qaunt + ""));
                orders.setOrderStatus("DELIVERED");
                ordersRepository.save(orders);
            });
            invoice2.setInvoiceTotal(sumPrice.get());
            invoiceRepository.save(invoice2);
            Ledger ledger = new Ledger();
            ledger.setInvoice(invoice2);
            ledger.setTransactionMode(TransactionMode.AMOUNT_RECEIVABLE);
            ledger.setTranscation(invoice2.getInvoiceTotal());
            ledger.setTranscationDate(invoice2.getInvoiceDate());
            ledger.setUsers(users);
            ledgerRepository.save(ledger);
        }
    }

}
