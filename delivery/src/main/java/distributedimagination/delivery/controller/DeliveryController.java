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

    @RequestMapping(value = "/service-instances/delivery/list")
    public Map<String, String> returnMap() {
        return deliveryService.getDeliveryDate();
    }

    @RequestMapping(value = "/service-instances/delivery")
    public ArrayList<String> getDeliveryList() {
        return deliveryService.getDeliveryList();
    }
}