package distributedimagination.delivery.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

@Service
public class DeliveryService {

    private OrderQuery orderQuery = GenerateOrderTest();

    public OrderQuery GenerateOrderTest() {
        ParcelQuery testParcel = new ParcelQuery(100.0, 100.0, 100.0, 100.0);
        ArrayList<ParcelQuery> parcelQueryList = new ArrayList<ParcelQuery>();
        parcelQueryList.add(testParcel);
        OrderQuery testOrder = new OrderQuery(90.0, 90.0, 90.0, 90.0,
                parcelQueryList);

        return testOrder;
    }

    public Map<String, String> getDeliveryDate() {
        final String uri = "http://discovery:8761/postal-services/urls";
        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> map;
        map = restTemplate.getForObject(uri, Map.class);
        return map;
    }

    public ArrayList<String> getDeliveryList() {
        ArrayList<String> delivery = new ArrayList<String>();
        Map<String, String> map = getLocations();
        Iterator<Map.Entry<String, String>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            String appURL = entry.getValue() + "deliveryDate";
            String name = entry.getKey();
            RestTemplate restTemplate = new RestTemplate();
            delivery.add(restTemplate.getForObject(appURL, String.class));
        }
        return delivery;
    }
}