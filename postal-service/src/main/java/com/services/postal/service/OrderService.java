package com.services.postal.service;

import com.services.postal.entities.Order;
import com.services.postal.entities.Parcel;
import com.services.postal.repository.OrderRepository;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
public class OrderService {

    final OrderRepository orderRepository;
    final double serviceMultiplier;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
        this.serviceMultiplier = 0.5 + (1 - 0.5) * new Random().nextDouble(); // Generate random double between 0.5 and 1
    }

    public Order createOrder(Order application) {
        for (Parcel parcel : application.getParcels()) {
            parcel.setOrder(application); // FK reference parcel -> order
        }
        application.setDateOrder(new Date()); // Set current date as order date
        application.setLocationLon(application.getSourceLon()); // Set current location to source location
        application.setLocationLat(application.getSourceLat()); // Set current location to source location
        application.calcCost(serviceMultiplier); // Calculate cost
        application.calcDelivery(); // Calculate expected delivery date

        return application;
    }

    public Order saveOrder(Order order) {
        return this.orderRepository.save(order);
    }

    public List<Order> getAllOrders() {
        List<Order> bookings = new ArrayList<Order>();
        orderRepository.findAll().forEach(bookings::add);
        return bookings;
    }

    public Order getOrderById(String id) {
        // TODO: HANDLE NOSUCHELEMENTEXCEPTION
        return orderRepository.findById(UUID.fromString(id)).get();
    }
}
