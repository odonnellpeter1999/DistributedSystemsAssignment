package com.services.template.service;

import com.services.template.entities.Order;
import com.services.template.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class OrderService {

    final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<Order> getAllOrders() {
        List<Order> bookings = new ArrayList<Order>();
        orderRepository.findAll().forEach(bookings::add);
        return bookings;
    }

    public Order getOrderById(String id) {
        return orderRepository.findById(UUID.fromString(id)).get();
    }
}
