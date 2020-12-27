package distributedimagination.quotation.controller;

import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

@SpringBootApplication
@RestController
public class QuotationController {
    @Autowired
    private DiscoveryClient discoveryClient;

    public ArrayList<String> serviceInstances = new ArrayList<>();

    @RequestMapping(value = "/service-instances/list")
    public ArrayList getApplications() {
        serviceInstances.addAll(discoveryClient.getServices());
        return serviceInstances;
    }

    @RequestMapping("/service-instances/quotations")
    public ArrayList<String> getQuotationsList(@PathVariable String applicationName) throws URISyntaxException {
        ArrayList<String> quotations = new ArrayList<String>();
        quotations.add("Sample Quote");
        for (int i = 0; i < serviceInstances.size(); i++) {
            if(serviceInstances.get(i).contains("POSTAL-SERVICE"))
            applicationName = serviceInstances.get(i);
            final String baseUrl = "http://localhost:"+8761+"/{applicationName}/quote";
            URI uri = new URI(baseUrl);
            RestTemplate restTemplate = new RestTemplate();
            HttpEntity<String> requestEntity = new HttpEntity<>(null);
            ResponseEntity<String> result = restTemplate.exchange(uri, HttpMethod.GET,requestEntity, String.class);
            quotations.add("/{applicationName}/quote");
        }
        return quotations;
    }
}
