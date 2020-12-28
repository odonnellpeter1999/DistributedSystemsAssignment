package com.services.postal.controller;

import com.services.postal.entities.Order;
import com.services.postal.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SimulationController {

    private final OrderService orderService;

    @Autowired
    public SimulationController(OrderService orderService) {
        this.orderService = orderService;
    }
    
    @Scheduled(fixedDelayString = "${SimulationInterval}", initialDelay = 10000)
    public void updateDB() {
        for (Order order : this.orderService.getActiveOrders()) {
            // TODO:
            // CALC: EXPECTED_DELIVERY_DATE - ORDERED_DATE, DIVIDE IN '3' SECTIONS
            // IF CURDATE IN:
            // - 1ST SECTION -> ORDER CONFIRMED
            // - 2ND SECTION -> ORDER AT SORTING FACILITY CLOSEST TO SOURCE
            // - 3RD SECTION -> ORDER AT SORTING FACILITY CLOSEST TO DESTINATION
        }

        // TODO: UPDATE DELIVIRED ORDERS (REMOVE CURRENT LOCATION)
    }
}
