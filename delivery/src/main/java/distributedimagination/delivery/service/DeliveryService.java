package distributedimagination.delivery.service;

import com.google.gson.*;
import distributedimagination.delivery.entity.OrderQuery;
import distributedimagination.delivery.entity.ParcelQuery;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class DeliveryService {

    public Map<String, String> getDelivery() {
        final String uri = "http://discovery:8761/postal-services/urls";
        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> map;
        map = restTemplate.getForObject(uri, Map.class);
        return map;
    }

    public String GenerateDelivery(JsonObject jsonObject) {
        JsonArray parcelArray = jsonObject.getAsJsonArray("parcels");
        ArrayList<ParcelQuery> parcelQueryList = new ArrayList<ParcelQuery>();

        //iterates through each parcel and parses each element
        for (JsonElement jsonElement : parcelArray) {
            JsonObject parcel = jsonElement.getAsJsonObject();
            ParcelQuery parcelQuery = new ParcelQuery(parcel.get("weightKg").getAsDouble(),
                    parcel.get("lengthCm").getAsDouble(), parcel.get("widthCm").getAsDouble(),
                    parcel.get("heightCm").getAsDouble());
            parcelQueryList.add(parcelQuery);
        }

        OrderQuery orderQuery = new OrderQuery(jsonObject.get("sourceLon").getAsDouble(),
                jsonObject.get("sourceLat").getAsDouble(), jsonObject.get("destinationLon").getAsDouble(),
                jsonObject.get("destinationLat").getAsDouble(), parcelQueryList);

        ArrayList<Map<String, String>> deliveries = getDeliveryList(orderQuery);

        Gson gson = new Gson();
        String jsArray = gson.toJson(deliveries);

        return jsArray;

    }

    public ArrayList<Map<String, String>> getDeliveryList(OrderQuery orderQuery) {
        Map<String, String> map = getDelivery();
        ArrayList<Map<String, String>> deliveries = new ArrayList<>();
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
            JsonObject jo = (JsonObject) JsonParser.parseString(responseEntity.getBody());
            String orderDate = name + jo.get("dateOrdered");
            String trackingID = jo.get("trackingId").toString();

            HashMap<String, String> obj = new HashMap<>();
            obj.put("orderDate", orderDate);
            obj.put("trackingID", trackingID);
            deliveries.add(obj);
        }
        return deliveries;
    }
}


