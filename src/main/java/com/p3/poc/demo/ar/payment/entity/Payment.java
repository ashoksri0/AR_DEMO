package com.p3.poc.demo.ar.payment.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.p3.poc.demo.ar.invoice.entity.Invoice;
import com.p3.poc.demo.ar.ledger.entity.Ledger;
import com.p3.poc.demo.ar.payment.enums.PaymentMode;
import com.p3.poc.demo.ar.user.entity.Users;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Entity
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private PaymentMode paymentMode;
    private Double amount;
    @Temporal(TemporalType.DATE)
    private Date paymentReceivedDate;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY,cascade= CascadeType.ALL)
    @JoinColumn(name = "invoice_id")
    private Invoice invoice;
    @OneToOne(mappedBy = "payment")
    private Ledger ledger;
    @Temporal(TemporalType.DATE)
    private Date paymentCreatedDate;


    public Payment() {
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }


    public Ledger getLedger() {
        return ledger;
    }

    public void setLedger(final Ledger ledger) {
        this.ledger = ledger;
    }

    public PaymentMode getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(PaymentMode paymentMode) {
        this.paymentMode = paymentMode;
    }


    public Double getAmount() {
        return amount;
    }

    public void setAmount(final Double amount) {
        this.amount = amount;
    }

    public Date getPaymentCreatedDate() {
        return paymentCreatedDate;
    }

    public void setPaymentCreatedDate(final Date paymentCreatedDate) {
        this.paymentCreatedDate = paymentCreatedDate;
    }

    public Date getPaymentReceivedDate() {
        return paymentReceivedDate;
    }

    public void setPaymentReceivedDate(final Date dateOfJoining) {
        this.paymentReceivedDate = dateOfJoining;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(final Invoice invoice) {
        this.invoice = invoice;
    }
}
