package distributedimagination.tracking.controller;

import distributedimagination.tracking.service.TrackingService;
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
public class TrackingController {

    private final TrackingService trackingService;

    @Autowired
    public TrackingController(TrackingService trackingService) {
        this.trackingService = trackingService;
    }

    @RequestMapping(value = "/service-instances/tracking")
    public Map<String, String> returnMap() {
        return trackingService.getLocations();
    }

    @RequestMapping(value = "/service-instances/tracking")
    public ArrayList<String> getTrackingList() {
        return trackingService.getTrackingList();
    }
}