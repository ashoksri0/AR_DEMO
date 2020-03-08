package com.p3.poc.demo.ar.order.repository;

import com.p3.poc.demo.ar.order.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Suriyanarayanan K
 * on 08/03/20 9:55 PM.
 */
@Repository
public interface OrdersRepository extends JpaRepository<Orders, Long> {
}
