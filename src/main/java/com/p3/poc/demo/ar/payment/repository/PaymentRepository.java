package com.p3.poc.demo.ar.payment.repository;

import com.p3.poc.demo.ar.payment.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Suriyanarayanan K
 * on 08/03/20 8:29 PM.
 */
@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
