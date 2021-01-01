package distributedimagination.eureka.controller;

import common.PostalServiceInstances;
import com.netflix.appinfo.InstanceInfo;
import distributedimagination.eureka.service.RegistryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
public class RegistryController {

    private final RegistryService registryService;

    @Autowired
    public RegistryController(RegistryService registryService) {
        this.registryService = registryService;
    }

    @RequestMapping(value = "/postal-services", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<InstanceInfo> getAllParcels() {
        return registryService.getPostalServiceInstances();
    }

    @RequestMapping(value = "/postal-services/urls", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, String> getPostalServiceURLs() {
        return registryService.getPostalURLs();
    }
}