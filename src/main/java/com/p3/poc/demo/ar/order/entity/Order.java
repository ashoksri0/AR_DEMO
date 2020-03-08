package com.p3.poc.demo.ar.order.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.p3.poc.demo.ar.invoice.entity.Invoice;
import com.p3.poc.demo.ar.payment.entity.Payment;

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

@Entity
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Temporal(TemporalType.DATE)
    private Date orderDate;
    private String shipAddress;
    private Double orderTotal;
    private String orderStatus;
    private Double orderPrice;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY,cascade= CascadeType.ALL)
    @JoinColumn(name = "invoice_id")
    private Invoice invoice;


}
