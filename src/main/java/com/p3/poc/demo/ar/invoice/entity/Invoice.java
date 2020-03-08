package com.p3.poc.demo.ar.invoice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.p3.poc.demo.ar.ledger.entity.Ledger;
import com.p3.poc.demo.ar.order.entity.Orders;
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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.Set;

@Entity
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Temporal(TemporalType.DATE)
    private Date invoice_date;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY,cascade=CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private Users users;

    @OneToMany(mappedBy = "invoice")
    private Set<Ledger> ledgers;


    private Double invoice_Total;
    @OneToMany(mappedBy = "invoice")
    private Set<Orders> orders;
    @OneToMany(mappedBy = "invoice",cascade = CascadeType.ALL)
    private Set<Payment> payment;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public Date getInvoice_date() {
        return invoice_date;
    }

    public void setInvoice_date(final Date invoice_date) {
        this.invoice_date = invoice_date;
    }

    public Double getInvoice_Total() {
        return invoice_Total;
    }

    public void setInvoice_Total(final Double invoice_Total) {
        this.invoice_Total = invoice_Total;
    }

    public Set<Orders> getOrders() {
        return orders;
    }

    public void setOrders(final Set<Orders> orders) {
        this.orders = orders;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(final Users users) {
        this.users = users;
    }

    public Set<Ledger> getLedgers() {
        return ledgers;
    }

    public void setLedgers(final Set<Ledger> ledgers) {
        this.ledgers = ledgers;
    }

    public Set<Payment> getPayment() {
        return payment;
    }

    public void setPayment(final Set<Payment> payment) {
        this.payment = payment;
    }
}
