package com.p3.poc.demo.ar.order.controller;

import com.p3.poc.demo.ar.customexceptions.OrderNotFoundException;
import com.p3.poc.demo.ar.order.entity.Orders;
import com.p3.poc.demo.ar.order.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Suriyanarayanan K
 * on 08/03/20 9:54 PM.
 */
@RestController
@RequestMapping("/orders")
public class OrdersController {

    @Autowired
    OrdersService ordersService;

    @GetMapping("/")
    public Iterable<Orders> getOrders() {
        return ordersService.findAllOrders();
    }

    @GetMapping("/{id}")
    public Orders getOrder(@PathVariable Long id) {
        return ordersService.findOrdersById(id).orElseThrow(OrderNotFoundException::new);
    }

    @PostMapping("/")
    public Orders addOrder(@RequestBody Orders order) {
        return ordersService.saveOrder(order);
    }

    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable Long id) {
        ordersService.deleteOrderById(id);
    }

    @PutMapping("/{id}")
    public Orders updateOrder(@PathVariable Long id, @RequestBody Orders order) {
        return ordersService.updateOrderById(id, order);
    }
}
