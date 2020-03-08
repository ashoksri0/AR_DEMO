package com.p3.poc.demo.ar.ledger.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.p3.poc.demo.ar.invoice.entity.Invoice;
import com.p3.poc.demo.ar.ledger.TransactionMode;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.Set;

@Entity
public class Ledger {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Double transcation;
    @Temporal(TemporalType.DATE)
    private Date transcationDate;

    private Double invoiceBalance;

    private Double userBalance;

    private Date transactionDate;

    private TransactionMode transactionMode;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY,cascade= CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private Users users;

    @OneToMany(mappedBy = "ledger")
    private Set<Invoice> invoices;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(final Users users) {
        this.users = users;
    }
}
