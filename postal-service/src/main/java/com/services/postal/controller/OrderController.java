package com.services.postal.controller;

import com.services.postal.entities.Order;
import com.services.postal.entities.Parcel;
import com.services.postal.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Point;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping(value = "/quote", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Order getQuotation(@RequestBody Order application) {
        Order quoteOrder = this.orderService.createOrder(application);
        return quoteOrder;
    }

    @PostMapping(value = "/orders", produces = MediaType.APPLICATION_JSON_VALUE)
    public Order placeOrder(@RequestBody Order application) {
        Order newOrder = this.orderService.createOrder(application);
        return this.orderService.saveOrder(newOrder);
    }

    @GetMapping(value = "/orders", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Order> getAllOrders() {
        System.out.println("ik kom hier 2!");
        return orderService.getAllOrders();
    }

    @GetMapping(value = "/orders", params = "orderId", produces = MediaType.APPLICATION_JSON_VALUE)
    public Order getOrderById(@RequestParam String orderId) {
        System.out.println("ik kom hier 3!");
        return orderService.getOrderById(orderId);
    }

}
