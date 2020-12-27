package com.services.registryclient.controller;

import com.services.registryclient.service.RegistryClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(value = "/postal-services", produces = MediaType.APPLICATION_JSON_VALUE)
public class RegistryClientController {

    private final RegistryClientService registryClientService;

    @Autowired
    public RegistryClientController(RegistryClientService registryClientService) {
        this.registryClientService = registryClientService;
    }

    @GetMapping
    public Map<String, String> getAllParcels() {
        return registryClientService.getPostalServices();
    }
}
