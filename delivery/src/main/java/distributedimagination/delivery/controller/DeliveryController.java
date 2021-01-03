package distributedimagination.delivery.controller;

import distributedimagination.delivery.service.DeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
public class DeliveryController {

    private final DeliveryService deliveryService;

    @Autowired
    public DeliveryController(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    @RequestMapping(value = "/delivery-providers")
    public Map<String, String> returnMap() {
        return deliveryService.getDelivery();
    }

    @RequestMapping(value = "request-delivery")
    public Map<String, String> getDeliveryList() {
        return deliveryService.getDeliveryList();
    }
}