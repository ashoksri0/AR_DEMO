package com.p3.poc.demo.ar.payment.entity;

import com.p3.poc.demo.ar.payment.enums.PaymentMode;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long payment_id;
    private Long userId;
    private Long invoice_id;
    private PaymentMode paymentMode;
    private Date date_received;
    private Double amount_received;

    public Payment() {
    }

    public Long getPayment_id() {
        return payment_id;
    }

    public void setPayment_id(Long payment_id) {
        this.payment_id = payment_id;
    }

    public Long getInvoice_id() {
        return invoice_id;
    }

    public void setInvoice_id(Long invoice_id) {
        this.invoice_id = invoice_id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public PaymentMode getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(PaymentMode paymentMode) {
        this.paymentMode = paymentMode;
    }

    public Date getDate_received() {
        return date_received;
    }

    public void setDate_received(Date date_received) {
        this.date_received = date_received;
    }

    public Double getAmount_received() {
        return amount_received;
    }

    public void setAmount_received(Double amount_received) {
        this.amount_received = amount_received;
    }
}
