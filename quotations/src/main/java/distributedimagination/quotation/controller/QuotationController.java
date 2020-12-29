package distributedimagination.quotation.controller;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;

import jdk.nashorn.internal.parser.JSONParser;
import org.h2.util.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.json.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.stream.Collectors;

@SpringBootApplication
@RestController
public class QuotationController {
    @Autowired
    private EurekaClient eurekaClient;

//    @RequestMapping(value = "/service-instances/list")
//    public ArrayList<String> getApplications() {
//        ArrayList<String> serviceInstances = new ArrayList<String>();
//        List<Application> applications = eurekaClient.getApplications().getRegisteredApplications();
//        for (Application application : applications) {
//            for (InstanceInfo instance : application.getInstances()) {
//                if (instance.getAppName().contains("POSTAL-SERVICE-"))
//                    serviceInstances.add(instance.getHomePageUrl());
//            }
//        }
//        return serviceInstances;
//    }

    @RequestMapping(value = "/service-instances/quotations")
    public ArrayList<String> getQuotationsList() throws URISyntaxException {

        ArrayList<String> quotations = new ArrayList<>();
        ArrayList<String> instances = new ArrayList<>();

        String baseUrl = "http://discovery:8761/postal-services";
        URI uri = new URI(baseUrl);
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> requestEntity = new HttpEntity<>(null);
        ResponseEntity<String> result = restTemplate.exchange(uri, HttpMethod.GET, requestEntity, String.class);

        Map<String, String> map = Arrays.stream(result.toString().split(","))
                .map(s -> s.split(":"))
                .collect(Collectors.toMap(s -> s[0], s -> s[1]));

        quotations.add(map.get("homePageUrl"));



        String instance = parse.getFirst("homePageUrl").toString();

        instances.add(instance);

        Iterator<String> iterator = instances.iterator();

        while (iterator.hasNext()) {
            String appURL = iterator.next();
            baseUrl = appURL + "/quote";
            uri = new URI(baseUrl);
            RestTemplate restTemplates = new RestTemplate();
            HttpEntity<String> requestEntitys = new HttpEntity<>(null);
            ResponseEntity<String> quoteResponse = restTemplates.exchange(uri, HttpMethod.GET, requestEntitys, String.class);
            String quote = quoteResponse.getBody();
            quotations.add(quote);
        }
        return quotations;
    }
}
