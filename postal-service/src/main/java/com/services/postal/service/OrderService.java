package com.services.postal.service;

import com.services.postal.entities.Order;
import com.services.postal.entities.Parcel;
import com.services.postal.errors.NoSuchOrderException;
import com.services.postal.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    @Value("${DeliveryCostMultiplier}")
    private double DeliveryCostMultiplier;

    @Value("${PostalServiceName}")
    private transient String serviceName;

    @Value("${PostalServiceId}")
    private transient String serviceId;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order createOrder(Order application) {
        for (Parcel parcel : application.getParcels()) {
            parcel.setOrder(application); // FK reference parcel -> order
        }
        application.setDateOrder(new Date()); // Set current date as order date
        application.calcCost(this.DeliveryCostMultiplier); // Calculate cost
        application.calcDelivery(); // Calculate expected delivery date
        
        // Set Postal serviceName and serviceId (not able to set this in beans as it's a spring-boot input variable)
        application.setServiceName(this.serviceName);
        application.setServiceId(this.serviceId);
        return application;
    }

    public Order saveOrder(Order order) {
        return this.orderRepository.saveAndFlush(order);
    }

    public List<Order> getAllOrders() {
        List<Order> bookings = new ArrayList<Order>();
        orderRepository.findAll().forEach(bookings::add);
        return bookings;
    }

    public List<Order> getActiveOrders() {
        return orderRepository.getActiveOrders();
    }

    public Optional<Order> getOrderById(String id) {
        try {
            return orderRepository.findById(UUID.fromString(id));
        } catch (IllegalArgumentException e) {
            throw new NoSuchOrderException();
        }
    }
}
