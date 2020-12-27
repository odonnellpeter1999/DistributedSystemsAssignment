package distributedimagination.quotation.controller;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;
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
import java.util.*;

@SpringBootApplication
@RestController
public class QuotationController {
    @Autowired
    private EurekaClient eurekaClient;

    @RequestMapping(value = "/service-instances/list")
    public Map getApplications() {
        Map<Integer, String> serviceInstances = new HashMap<>();
        List<Application> applications = eurekaClient.getApplications().getRegisteredApplications();
        for (Application application : applications) {
            for (InstanceInfo instance : application.getInstances()) {
                if (instance.getAppName().contains("POSTAL-SERVICE-"))
                    serviceInstances.put(instance.getPort(), instance.getAppName());
            }
        }
        return serviceInstances;
    }

    @RequestMapping(value = "/service-instances/quotations")
    public ArrayList<String> getQuotationsList() throws URISyntaxException {
        ArrayList<String> quotations = new ArrayList<String>();
        quotations.add("Test Quote");
        Map<Integer, String> serviceInstances = getApplications();
        Iterator<Map.Entry<Integer, String>> it = serviceInstances.entrySet().iterator();

        while (it.hasNext()) {
            Map.Entry<Integer, String> itMap = it.next();
            String applicationName = itMap.getValue();
            String port = itMap.getValue();
            final String baseUrl = "http://localhost:" + port + "/" + applicationName + "/quote";
            URI uri = new URI(baseUrl);
            RestTemplate restTemplate = new RestTemplate();
            HttpEntity<String> requestEntity = new HttpEntity<>(null);
            ResponseEntity<String> result = restTemplate.exchange(uri, HttpMethod.GET, requestEntity, String.class);
            quotations.add(result.toString());
        }
        return quotations;
    }
}
