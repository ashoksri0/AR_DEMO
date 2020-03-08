package com.p3.poc.demo.ar.order.service;

import com.p3.poc.demo.ar.customexceptions.OrderNotFoundException;
import com.p3.poc.demo.ar.order.entity.Orders;
import com.p3.poc.demo.ar.order.repository.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Created by Suriyanarayanan K
 * on 08/03/20 9:55 PM.
 */
@Service
public class OrdersService {

    @Autowired
    OrdersRepository ordersRepository;

    public List<Orders> findAllOrders() {
        return ordersRepository.findAll();
    }

    public Optional<Orders> findOrdersById(Long id) {
        return ordersRepository.findById(id);
    }

    public Orders saveOrder(Orders Payment) {
        return ordersRepository.save(Payment);
    }

    public void deleteOrderById(Long id) {
        ordersRepository.findById(id).orElseThrow(OrderNotFoundException::new);
    }

    public Orders updateOrderById(Long id, Orders order) {
        Orders orderToUpdate = ordersRepository.findById(id).orElseThrow(OrderNotFoundException::new);
        orderToUpdate.setInvoice(order.getInvoice());
        orderToUpdate.setOrderDate(order.getOrderDate());
        orderToUpdate.setOrderPrice(order.getOrderPrice());
        orderToUpdate.setOrderStatus(order.getOrderStatus());
        orderToUpdate.setOrderQuantity(order.getOrderQuantity());
        orderToUpdate.setOrderName(order.getOrderName());
        ordersRepository.save(orderToUpdate);
        return orderToUpdate;
    }
}
