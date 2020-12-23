package com.services.postal.controller;

import com.services.postal.entities.Order;
import com.services.postal.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;
import java.net.URISyntaxException;
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
    public ResponseEntity<Order> placeOrder(@RequestBody Order application) throws URISyntaxException {
        Order newOrder = this.orderService.createOrder(application);
        newOrder = this.orderService.saveOrder(newOrder);
        String path = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString()+ "/track?orderId=" + newOrder.getOid();
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(new URI(path));
        return new ResponseEntity<>(newOrder, headers, HttpStatus.CREATED);
    }

    @GetMapping(value = "/orders", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping(value = "/track", params = "orderId", produces = MediaType.APPLICATION_JSON_VALUE)
    public Order getOrderById(@RequestParam String orderId) {
        return orderService.getOrderById(orderId);
    }

}
