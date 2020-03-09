package com.p3.poc.demo.ar.invoice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.p3.poc.demo.ar.ledger.entity.Ledger;
import com.p3.poc.demo.ar.order.entity.Orders;
import com.p3.poc.demo.ar.payment.entity.Payment;
import com.p3.poc.demo.ar.user.entity.Users;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Temporal(TemporalType.TIMESTAMP)
    private Date invoiceDate;
    private Double invoiceTotal;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private Users users;

    @OneToMany(mappedBy = "invoice")
    private Set<Ledger> ledgers;



    @OneToMany(mappedBy = "invoice")
    private Set<Orders> orders;
    @OneToMany(mappedBy = "invoice",cascade = CascadeType.ALL)
    private Set<Payment> payment;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(Date invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public Double getInvoiceTotal() {
        return invoiceTotal;
    }

    public void setInvoiceTotal(Double invoiceTotal) {
        this.invoiceTotal = invoiceTotal;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public Set<Ledger> getLedgers() {
        return ledgers;
    }

    public void setLedgers(Set<Ledger> ledgers) {
        this.ledgers = ledgers;
    }

    public Set<Orders> getOrders() {
        return orders;
    }

    public void setOrders(Set<Orders> orders) {
        this.orders = orders;
    }

    public Set<Payment> getPayment() {
        return payment;
    }

    public void setPayment(Set<Payment> payment) {
        this.payment = payment;
    }
}
