package distributedimagination.quotation.service;


import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import distributedimagination.quotation.entity.OrderQuery;
import distributedimagination.quotation.entity.ParcelQuery;
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

@Service
public class QuotationService {

    private OrderQuery orderQuery = GenerateOrderTest();

    //temporary hardcoded order testing
    public OrderQuery GenerateOrderTest() {
        ParcelQuery testParcel = new ParcelQuery(100.0, 100.0, 100.0, 100.0);
        ArrayList<ParcelQuery> parcelQueryList = new ArrayList<ParcelQuery>();
        parcelQueryList.add(testParcel);
        OrderQuery testOrder = new OrderQuery(90.0, 90.0, 90.0, 90.0,
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
    /*loops through all postal services, and sends a post request to each using gson to convert a OrderQuery object to
    JSON. The response is then parsed to a JSON Object and returns the name of the postal service and the cost field*/
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
            Gson gson = new Gson();
            String json = gson.toJson(orderQuery);
            HttpEntity<String> request = new HttpEntity<String>(json, headers);
            ResponseEntity<String> responseEntity = restTemplate.postForEntity(appURL, request, String.class);
            JsonParser jsonParser = new JsonParser();
            JsonObject jo = (JsonObject) jsonParser.parse(responseEntity.getBody());
            String quote = name + ": " + jo.get("cost");
            quotations.add(quote);
        }
        return quotations;
    }

}