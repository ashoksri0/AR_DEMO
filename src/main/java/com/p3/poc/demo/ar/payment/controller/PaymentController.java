package com.p3.poc.demo.ar.payment.controller;

import com.p3.poc.demo.ar.utils.exceptions.PaymentNotFoundException;
import com.p3.poc.demo.ar.payment.entity.Payment;
import com.p3.poc.demo.ar.payment.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Suriyanarayanan K
 * on 08/03/20 8:28 PM.
 */
@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    PaymentService paymentService;

    @GetMapping("/")
    public Iterable<Payment> getPayment() {
        return paymentService.findAllPayments();
    }

    @GetMapping("/{id}")
    public Payment getPayment(@PathVariable Long id) {
        return paymentService.findPaymentById(id).orElseThrow(PaymentNotFoundException::new);
    }

    @PostMapping("/")
    public Payment addPayment(@RequestBody Payment payment) {
        return paymentService.savePayment(payment);
    }

    @DeleteMapping("/{id}")
    public void deletePayment(@PathVariable Long id) {
        paymentService.deletePaymentById(id);
    }

    @PutMapping("/{id}")
    public Payment updatePayment(@PathVariable Long id, @RequestBody Payment payment) {
        return paymentService.updatePaymentById(id, payment);
    }
}
