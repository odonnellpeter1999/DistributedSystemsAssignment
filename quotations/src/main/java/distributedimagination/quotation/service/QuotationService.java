package distributedimagination.quotation.service;


import distributedimagination.quotation.entity.OrderQuery;
import distributedimagination.quotation.entity.ParcelQuery;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class QuotationService {

    private OrderQuery orderQuery = GenerateOrderTest();

    public OrderQuery GenerateOrderTest() {
        ParcelQuery testParcel = new ParcelQuery(100.0, 100.0, 100.0, 100.0);
        ArrayList<ParcelQuery> parcelQueryList = new ArrayList<ParcelQuery>();
        parcelQueryList.add(testParcel);
        OrderQuery testOrder = new OrderQuery(100.0, 100.0, 100.0, 100.0,
                parcelQueryList);

        return testOrder;
    }

    public Map<String, String> getQuotes() {
        final String uri = "http://discovery:8761/postal-services/urls";
        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> map;
        map = restTemplate.getForObject(uri, Map.class);
        return map;
    }

    public ArrayList<String> getQuotationsList() {
        Map<String, String> map = getQuotes();
        ArrayList<String> quotations = new ArrayList<>();
        Iterator<Map.Entry<String, String>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            RestTemplate restTemplate = new RestTemplate();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

            String name = entry.getKey();
            String appURL = entry.getValue() + "quote";

            HttpEntity<String> request = new HttpEntity<String>(orderQuery.toString(), headers);
            ResponseEntity<String> responseEntity = restTemplate.postForEntity(appURL, request, String.class);
            quotations.add(responseEntity.getBody());
        }
        return quotations;
    }



}