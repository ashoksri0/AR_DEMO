package com.p3.poc.demo.ar.payment.controller;

import com.p3.poc.demo.ar.customexceptions.PaymentNotFoundException;
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
    public Iterable<Payment> getUsers() {
        return paymentService.findAll();
    }

    @GetMapping("/{id}")
    public Payment getStudent(@PathVariable Long id) {
        return paymentService.findById(id).orElseThrow(PaymentNotFoundException::new);
    }

    @PostMapping("/")
    public Payment addStudent(@RequestBody Payment payment) {
        return paymentService.save(payment);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        paymentService.deleteById(id);
    }

    @PutMapping("/{id}")
    public Payment updateStudent(@PathVariable Long id, @RequestBody Payment payment) {
        return paymentService.updateById(id, payment);
    }
}
