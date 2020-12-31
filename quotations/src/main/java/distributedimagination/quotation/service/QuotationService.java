package distributedimagination.quotation.service;

import com.google.gson.Gson;
import distributedimagination.quotation.entity.OrderQuery;
import distributedimagination.quotation.entity.ParcelQuery;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

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
            RestTemplate restTemplates = new RestTemplate();

            String name = entry.getKey();
            String appURL = entry.getValue() + "quote";

            Gson g = null;
            String jsonString = g.toJson(orderQuery);
            HttpEntity<String> request = new HttpEntity<>(jsonString);
            ResponseEntity<String> responseEntity = restTemplates.postForEntity(appURL, request, String.class);

            quotations.add(responseEntity.getBody());
        }
        return quotations;
    }
}