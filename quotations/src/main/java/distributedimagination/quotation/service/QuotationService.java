package distributedimagination.quotation.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class QuotationService {

    public Map<String, String > getQuotes() {
        final String uri = "http://discovery:8761/postal-services/urls";
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(uri, String.class);
        Map<String, String> map = new HashMap<String, String>();
        String[] pairs = result.split(",");
        for (int i=0;i<pairs.length;i++) {
            String pair = pairs[i];
            String[] keyValue = pair.split(":");
            map.put(keyValue[0], keyValue[1]);
        }
        return map;
    }
}