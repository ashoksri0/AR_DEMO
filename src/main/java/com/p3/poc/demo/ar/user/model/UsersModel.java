package com.p3.poc.demo.ar.user.model;

import com.p3.poc.demo.ar.invoice.entity.Invoice;
import com.p3.poc.demo.ar.ledger.entity.Ledger;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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



@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsersModel {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Date dob;
    private Date doj;
    private Integer invoiceCount;
}
