package distributedimagination.quotation.controller;

import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ArrayList<String> getQuotationsList(@PathVariable String applicationName) {
        ArrayList<String> quotations = new ArrayList<String>();
        quotations.add("Sample Quote");
        for (int i = 0; i < serviceInstances.size(); i++) {
            applicationName = serviceInstances.get(i);
            quotations.add("/{applicationName}/quote");
        }
        return quotations;
    }
}
