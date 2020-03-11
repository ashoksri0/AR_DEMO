package com.p3.poc.demo.ar.payment.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.p3.poc.demo.ar.invoice.entity.Invoice;
import com.p3.poc.demo.ar.ledger.entity.Ledger;
import com.p3.poc.demo.ar.payment.enums.PaymentMode;

import javax.persistence.CascadeType;
import javax.persistence.Column;
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
    @Column(precision=10, scale=2)
    private Double amount;
    @Temporal(TemporalType.TIMESTAMP)
    private Date paymentReceivedDate;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "invoice_id")
    private Invoice invoice;
    @JsonIgnore
    @OneToOne(mappedBy = "payment")
    private Ledger ledger;


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

    @Override
    public String toString() {
        return "Payment{" +
                "id=" + id +
                ", paymentMode=" + paymentMode +
                ", amount=" + amount +
                ", paymentReceivedDate=" + paymentReceivedDate +
                '}';
    }
}
