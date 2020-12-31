package distributedimagination.quotation.service;

import distributedimagination.quotation.entity.OrderQuery;
import distributedimagination.quotation.entity.ParcelQuery;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

@Service
public class QuotationService {

    private OrderQuery orderQuery;

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
        orderQuery = GenerateOrderTest();
        ArrayList<String> quotations = new ArrayList<>();
        Iterator<Map.Entry<String, String>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            String name = entry.getKey();
            quotations.add(entry.getValue());
            String appURL = entry.getValue() + "quote";
            quotations.add(name + ":" + appURL);
            HttpEntity<String> requestEntity = new HttpEntity<String>(orderQuery.toString());
            RestTemplate restTemplates = new RestTemplate();
            quotations.add(restTemplates.postForObject(appURL, requestEntity, String.class));
        }
        return quotations;
    }
}