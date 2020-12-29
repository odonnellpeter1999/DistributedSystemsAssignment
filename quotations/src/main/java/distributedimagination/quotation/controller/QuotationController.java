package distributedimagination.quotation.controller;

import distributedimagination.quotation.service.QuotationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServlet;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

@SpringBootApplication
@RestController
public class QuotationController {

    private final QuotationService quotationService;

    @Autowired
    public QuotationController(QuotationService quotationService) {
        this.quotationService = quotationService;
    }

    @RequestMapping(value = "/service-instances/quotations")
    public Map<String, String> returnMap() {
        return quotationService.getQuotes();
    }

//    @RequestMapping(value = "/service-instances/quotations")
//    public ArrayList<String> getQuotationsList() throws URISyntaxException {
//        ArrayList<String> quotations = new ArrayList<>();
//        ArrayList<String> instances = new ArrayList<>();
//
//        String baseUrl = "http://discovery:8761/postal-services/urls";
//        URI uri = new URI(baseUrl);
//        RestTemplate restTemplate = new RestTemplate();
//        HttpEntity<String> requestEntity = new HttpEntity<>(null);
//
//        ResponseEntity<String> result = restTemplate.exchange(uri, HttpMethod.GET, requestEntity, String.class);
//        String lines[] = result.toString().split("\\r?\\n");
//        instances.add(Arrays.lines.asList());
//
//        Iterator<String> iterator = instances.iterator();
//        while (iterator.hasNext()) {
//            String appURL = iterator.next();
//            baseUrl = appURL + "/quote";
//            uri = new URI(baseUrl);
//            RestTemplate restTemplates = new RestTemplate();
//            HttpEntity<String> requestEntitys = new HttpEntity<>(null);
//            ResponseEntity<String> quoteResponse = restTemplates.exchange(uri, HttpMethod.GET, requestEntitys, String.class);
//            String quote = quoteResponse.getBody();
//            quotations.add(quote);
//        }
//        return quotations;
//    }
}
