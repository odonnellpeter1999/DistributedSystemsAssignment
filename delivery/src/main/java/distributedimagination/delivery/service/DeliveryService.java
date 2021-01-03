package distributedimagination.delivery.service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import distributedimagination.delivery.entity.OrderQuery;
import distributedimagination.delivery.entity.ParcelQuery;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.HashMap;

@Service
public class DeliveryService {

    private OrderQuery orderQuery = GenerateOrderTest();


    public Map<String, String> getDelivery() {
        final String uri = "http://discovery:8761/postal-services/urls";
        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> map;
        map = restTemplate.getForObject(uri, Map.class);
        return map;
    }

    public OrderQuery GenerateOrderTest() {
        ParcelQuery testParcel = new ParcelQuery(100.0, 100.0, 100.0, 100.0);
        ArrayList<ParcelQuery> parcelQueryList = new ArrayList<ParcelQuery>();
        parcelQueryList.add(testParcel);
        OrderQuery testOrder = new OrderQuery(90.0, 90.0, 90.0, 90.0,
                parcelQueryList);

        return testOrder;
    }

    public Map<String, String> getDeliveryList() {
        Map<String, String> delivery = new HashMap<>();
        Map<String, String> map = getDelivery();
        Iterator<Map.Entry<String, String>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            RestTemplate restTemplate = new RestTemplate();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

            String name = entry.getKey();
            String appURL = entry.getValue() + "orders";
            Gson gson = new Gson();
            String json = gson.toJson(orderQuery);
            HttpEntity<String> request = new HttpEntity<String>(json, headers);
            ResponseEntity<String> responseEntity = restTemplate.postForEntity(appURL, request, String.class);
            JsonParser jsonParser = new JsonParser();
            JsonObject jo = (JsonObject) jsonParser.parse(responseEntity.getBody());
            String orderDate = name + jo.get("dateOrdered");
            String trackingID = jo.get("trackingId").toString();
            delivery.put("orderDate", orderDate);
            delivery.put("trackingID", trackingID);
        }
        return delivery;
    }
}