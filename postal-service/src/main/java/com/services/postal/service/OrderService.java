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

    @Value("${DeliverySpeed}")
    private double DeliverySpeed;

    @Value("${PostalServiceName}")
    private transient String serviceName;

    @Value("${PostalServiceId}")
    private transient String serviceId;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    /**
     * Create order with generated properties filled in.
     * Used for quotations and orders;
     * @param application Order - with mandatory properties only
     * @return Order with properties filled in
     */
    public Order createOrder(Order application) {
        for (Parcel parcel : application.getParcels()) {
            parcel.setOrder(application); // FK reference parcel -> order
        }
        application.setDateOrdered(new Date()); // Set current date as order date
        application.calcCost(this.DeliveryCostMultiplier); // Calculate cost
        application.calcDateExpected(this.DeliverySpeed); // Calculate expected delivery date
        
        return this.addServiceDetails(application);
    }

    /**
     * Adds serviceName and serviceId from spring-boot properties file to the order.
     * Cannot be set directly in Entity as environment variables are not available there.
     * @param order Order
     * @return Order with serviceName and serviceId set
     */
    public Order addServiceDetails(Order order) {
        // Set Postal serviceName and serviceId (not able to set this in beans as it's a spring-boot input variable)
        order.setServiceName(this.serviceName);
        order.setServiceId(this.serviceId);
        return order;
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
