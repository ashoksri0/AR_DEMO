package com.p3.poc.demo.ar.user.entity;

import com.p3.poc.demo.ar.invoice.entity.Invoice;
import com.p3.poc.demo.ar.ledger.entity.Ledger;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.Set;

@Entity
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    @Temporal(TemporalType.DATE)
    private Date dob;

    @Temporal(TemporalType.DATE)
    private Date doj;

    @OneToMany(mappedBy = "users",fetch = FetchType.LAZY)
    private Set<Invoice> invoices;

    @OneToMany(mappedBy = "users",fetch = FetchType.LAZY)
    private Set<Ledger> ledgers;

    public Users() {
    }

    public Users(String firstName, String lastName, String email, Date dob, Date doj) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.dob = dob;
        this.doj = doj;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long userId) {
        this.id = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public Date getDoj() {
        return doj;
    }

    public void setDoj(Date doj) {
        this.doj = doj;
    }

    public Set<Invoice> getInvoices() {
        return invoices;
    }

    public void setInvoices(final Set<Invoice> invoices) {
        this.invoices = invoices;
    }

    public Set<Ledger> getLedgers() {
        return ledgers;
    }

    public void setLedgers(final Set<Ledger> ledgers) {
        this.ledgers = ledgers;
    }
}
