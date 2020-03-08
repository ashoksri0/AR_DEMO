package com.p3.poc.demo.ar.payment.service;

import com.p3.poc.demo.ar.customexceptions.PaymentNotFoundException;
import com.p3.poc.demo.ar.payment.entity.Payment;
import com.p3.poc.demo.ar.payment.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


/**
 * Created by Suriyanarayanan K
 * on 08/03/20 8:28 PM.
 */
@Service
public class PaymentService {
    @Autowired
    PaymentRepository paymentRepository;

    public List<Payment> findAll() {
        return paymentRepository.findAll();
    }

    public Optional<Payment> findById(Long id) {
        return paymentRepository.findById(id);
    }

    public Payment save(Payment Payment) {
        return paymentRepository.save(Payment);
    }

    public void deleteById(Long id) {
        paymentRepository.findById(id).orElseThrow(PaymentNotFoundException::new);
    }

    public Payment updateById(Long id, Payment payment) {
        Payment optionalPayment = paymentRepository.findById(id).orElseThrow(PaymentNotFoundException::new);
        optionalPayment.setUserId(payment.getUserId());
        optionalPayment.setAmount_received(payment.getAmount_received());
        optionalPayment.setDate_received(payment.getDate_received());
        optionalPayment.setInvoice_id(payment.getInvoice_id());
        optionalPayment.setPaymentMode(payment.getPaymentMode());
        return optionalPayment;
    }
}
