package com.services.postal.controller;

import java.util.Date;

import com.services.postal.entities.Facility;
import com.services.postal.entities.Order;
import com.services.postal.service.FacilityService;
import com.services.postal.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Simulator Class - simulates packages through the system.
 */
@Component
public class SimulationController {

    private final OrderService orderService;
    private final FacilityService facilityService;

    @Autowired
    public SimulationController(OrderService orderService, FacilityService facilityService) {
        this.orderService = orderService;
        this.facilityService = facilityService;
    }
    
    // Divide total delivery duration into 3 phaes:
    // Phase 0 - package is at source
    // Phase 1 - package is at sorting facility closest to source
    // Phase 2 - package is at sorting facility closest to destination
    // Phase 3 - package is delivered
    @Scheduled(fixedDelayString = "${SimulationInterval}", initialDelay = 10000)
    public void updateDB() {
        for (Order order : this.orderService.getActiveOrders()) {
            Long deliveryInterval = (order.getDateExpected().getTime() - order.getDateOrdered().getTime()) / 3;
            Long currentDate = new Date().getTime() - order.getDateOrdered().getTime();
            int currentInterval = (int) Math.floor(currentDate / deliveryInterval);

            Facility newFacility = null;
            
            switch (currentInterval) {
                case 0:
                    // Package at source
                    break;
                case 1:
                    // Package at facility closest to source
                    newFacility = this.facilityService.findClosestFacility(order.getSourceLon(), order.getSourceLat());
                    break;
                case 2:
                    // Package at facility closest to destination
                    newFacility = this.facilityService.findClosestFacility(order.getDestinationLon(), order.getDestinationLat());
                    break;
                case 3:
                    // Delivered
                    order.setDateDelivered(new Date());
                    break;
                default:
                    break;
            }
            order.setFacility(newFacility);
            this.orderService.saveOrder(order);
        }
    }
}
