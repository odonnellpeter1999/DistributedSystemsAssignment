package distributedimagination.tracking.service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Collections;
import java.util.Map;
import java.util.HashMap;

import distributedimagination.tracking.entity.OrderQuery;
import distributedimagination.tracking.entity.ParcelQuery;

@Service
public class TrackingService {

    private OrderQuery orderQuery = GenerateOrderTest();

    public OrderQuery GenerateOrderTest() {
        ParcelQuery testParcel = new ParcelQuery(100.0, 100.0, 100.0, 100.0);
        ArrayList<ParcelQuery> parcelQueryList = new ArrayList<ParcelQuery>();
        parcelQueryList.add(testParcel);
        OrderQuery testOrder = new OrderQuery(90.0, 90.0, 90.0, 90.0,
                parcelQueryList);

        return testOrder;
    }

    public Map<String, String> getPostalIDs() {
        final String uri = "http://discovery:8761/postal-services/id";
        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> map;
        map = restTemplate.getForObject(uri, Map.class);
        return map;
    }

    public Map<String, String> getTrackingList(JsonObject trackingInfo) {
        String trackingID = trackingInfo.get("trackingId").toString().replaceAll("^\"|\"$", "");
        String serviceID = trackingID.split("-")[0];
        Map<String, String> serviceIDs = getPostalIDs();
        String url = serviceIDs.get(serviceID) + "track/" + trackingID;

        System.out.println(url);
        System.out.println(serviceID);

        Map<String, String> tracking = new HashMap<>();
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        System.out.println(response.getBody());
        JsonObject jo = (JsonObject) JsonParser.parseString(response.getBody());
        String track = jo.get("facility").toString();
        String oid = jo.get("oid").toString();
        String dateDelivered = jo.get("dateDelivered").toString();
        String status = "ORDER CONFIRMED";
        if (oid == null) {
            status = "QUOTATION"; // Order has not been placed yet
        }
        if (dateDelivered != null) {
            status =  "DELIVERED"; // Order has been delivered 
        }
        if (track != null) {
            status =  "AT SORTING FACILITY"; // Order is still at source but placed
        }

        tracking.put("track", track);
        tracking.put("status", status);
        return tracking;
    }
}