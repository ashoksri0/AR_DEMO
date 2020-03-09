package com.p3.poc.demo.ar.ledger.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.p3.poc.demo.ar.invoice.entity.Invoice;
import com.p3.poc.demo.ar.ledger.enums.TransactionMode;
import com.p3.poc.demo.ar.payment.entity.Payment;
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
public class Ledger {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Double transcation;
    @Temporal(TemporalType.TIMESTAMP)
    private Date transcationDate;
    private Double invoiceBalance;
    private Double userBalance;
    private Double userOpeningBalance;
    private Double invoiceOpeningBalance;
    private TransactionMode transactionMode;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "payment_id", referencedColumnName = "id")
    private Payment payment;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private Users users;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "invoice_id")
    private Invoice invoice;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getTranscation() {
        return transcation;
    }

    public void setTranscation(Double transcation) {
        this.transcation = transcation;
    }

    public Date getTranscationDate() {
        return transcationDate;
    }

    public void setTranscationDate(Date transcationDate) {
        this.transcationDate = transcationDate;
    }

    public Double getInvoiceBalance() {
        return invoiceBalance;
    }

    public void setInvoiceBalance(Double invoiceBalance) {
        this.invoiceBalance = invoiceBalance;
    }

    public Double getUserBalance() {
        return userBalance;
    }

    public void setUserBalance(Double userBalance) {
        this.userBalance = userBalance;
    }

    public TransactionMode getTransactionMode() {
        return transactionMode;
    }

    public void setTransactionMode(TransactionMode transactionMode) {
        this.transactionMode = transactionMode;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public Double getUserOpeningBalance() {
        return userOpeningBalance;
    }

    public void setUserOpeningBalance(final Double userOpeningBalance) {
        this.userOpeningBalance = userOpeningBalance;
    }

    public Double getInvoiceOpeningBalance() {
        return invoiceOpeningBalance;
    }

    public void setInvoiceOpeningBalance(final Double invoiceOpeningBalance) {
        this.invoiceOpeningBalance = invoiceOpeningBalance;
    }
}
