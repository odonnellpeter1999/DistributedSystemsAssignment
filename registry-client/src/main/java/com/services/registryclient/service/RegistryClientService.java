package com.services.registryclient.service;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RegistryClientService {

    @Autowired
    private EurekaClient eurekaClient;

    public Map<String, String> getPostalServices() {
        Map<String, String> postalServices = new HashMap<>();
        List<Application> applications = eurekaClient.getApplications().getRegisteredApplications();
        for (Application application : applications) {
            for (InstanceInfo instance: application.getInstances()) {
                if (instance.getAppName().substring(15).equals("POSTAL-SERVICE-"))
                    postalServices.put(instance.getAppName(), instance.getHomePageUrl());
            }

        }


        return postalServices;
    }

}
