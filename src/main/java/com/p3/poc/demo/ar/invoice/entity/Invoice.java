package com.p3.poc.demo.ar.invoice.entity;

import com.p3.poc.demo.ar.order.entity.Order;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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
    private Double invoice_Total;
    @OneToMany(mappedBy = "invoice")
    private Set<Order> orders;
}
