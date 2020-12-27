package distributedimagination.eureka.controller;

import distributedimagination.eureka.service.RegistryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(value = "/postal-services", produces = MediaType.APPLICATION_JSON_VALUE)
public class RegistryController {

    private final RegistryService registryService;

    @Autowired
    public RegistryController(RegistryService registryService) {
        this.registryService = registryService;
    }

    @GetMapping
    public Map<String, String> getAllParcels() {
        return registryService.getPostalServices();
    }
}
