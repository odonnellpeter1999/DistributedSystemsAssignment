package distributedimagination.delivery.controller;

import distributedimagination.delivery.service.DeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServlet;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

@SpringBootApplication
@RestController
public class DeliveryController {

    private final DeliveryService DeliveryService;

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