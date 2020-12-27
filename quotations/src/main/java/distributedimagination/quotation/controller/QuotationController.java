package distributedimagination.quotation.controller;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootApplication
@RestController
public class QuotationController {
    @Autowired
    private EurekaClient eurekaClient;

    public Map<String, String> serviceInstances = new HashMap<>();

    @RequestMapping(value = "/service-instances/list")
    public Map getApplications() {
        List<Application> applications = eurekaClient.getApplications().getRegisteredApplications();
        for (Application application : applications) {
            for (InstanceInfo instance : application.getInstances()) {
                if (instance.getAppName().contains("POSTAL-SERVICE-"))
                    serviceInstances.put(instance.getAppName(), instance.getHomePageUrl());
            }
        }
        return serviceInstances;
    }

    @RequestMapping("/service-instances/quotations")
    public ArrayList<String> getQuotationsList(@PathVariable String applicationName, @PathVariable String port) throws URISyntaxException {
        ArrayList<String> quotations = new ArrayList<String>();
        for (int i = 0; i < serviceInstances.size(); i++) {
                applicationName = serviceInstances.get(i);
                port = serviceInstances.get(applicationName);
            final String baseUrl = "http://localhost:" + port + "/{applicationName}/quote";
            URI uri = new URI(baseUrl);
            RestTemplate restTemplate = new RestTemplate();
            HttpEntity<String> requestEntity = new HttpEntity<>(null);
            ResponseEntity<String> result = restTemplate.exchange(uri, HttpMethod.GET, requestEntity, String.class);
            quotations.add("/{applicationName}/quote");
        }
        return quotations;
    }
}
