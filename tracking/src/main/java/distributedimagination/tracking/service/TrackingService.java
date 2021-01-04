package distributedimagination.tracking.service;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.HashMap;

@Service
public class TrackingService {


    public Map<String, String> getPostalIDs() {
        final String uri = "http://discovery:8761/postal-services/id";
        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> map;
        map = restTemplate.getForObject(uri, Map.class);
        return map;
    }

    public Map<String, String> getTracking(JsonObject trackingInfo) {
        String url;
        Map<String, String> trackingResponse = new HashMap<>();
        String trackingID = trackingInfo.get("trackingId").toString().replaceAll("^\"|\"$", "");
        String serviceID = trackingID.split("-")[0];
        
        Map<String, String> serviceIDs = getPostalIDs();
        if (serviceIDs.get(serviceID) != null) {
            url = serviceIDs.get(serviceID) + "track/" + trackingID;
        } else {
            trackingResponse.put("error", "Tracking ID does not exist");
            return trackingResponse;
        }

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        System.out.println(response.getBody());
        JsonObject jo = (JsonObject) JsonParser.parseString(response.getBody());
        
        if (jo.get("status") != null) {
            String status = jo.get("status").toString().replace("\"", "");
            trackingResponse.put("status", status);
        }   

        if (jo.get("facility") != null) {
            JsonObject facility = (JsonObject) jo.get("facility");
            String facilityName = facility.get("name").toString().replace("\"", "");;
            trackingResponse.put("location", facilityName);
        }

        return trackingResponse;
    }
}