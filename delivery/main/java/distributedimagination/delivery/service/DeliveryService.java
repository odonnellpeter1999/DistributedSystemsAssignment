package distributedimagination.delivery.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

@Service
public class DeliveryService {

    public Map<String, String> getDeliveryDate() {
        final String uri = "http://discovery:8761/postal-services/urls";
        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> map;
        map = restTemplate.getForObject(uri, Map.class);
        return map;
    }

    public ArrayList<String> getDeliveryList() {
        ArrayList<String> delivery = new ArrayList<String>();
        ArrayList<String> map = getLocations();
        Iterator<Map.Entry<String, String>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            String appURL = entry.getValue();
            String name = entry.getKey();
            RestTemplate restTemplates = new RestTemplate();
            delivery.add(restTemplates.getForObject(appURL, String.class));
        }
        return delivery;
    }
}