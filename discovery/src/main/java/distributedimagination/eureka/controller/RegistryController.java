package distributedimagination.eureka.controller;

import com.netflix.appinfo.InstanceInfo;
import distributedimagination.eureka.service.RegistryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/postal-services", produces = MediaType.APPLICATION_JSON_VALUE)
public class RegistryController {

    private final RegistryService registryService;

    @Autowired
    public RegistryController(RegistryService registryService) {
        this.registryService = registryService;
    }

    @GetMapping
    public List<InstanceInfo> getAllParcels() {
        return registryService.getPostalServices();
    }
}
