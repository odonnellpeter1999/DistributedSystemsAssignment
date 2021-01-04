package distributedimagination.tracking.entity;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.List;


public class OrderQuery {

    private Double sourceLon;
    private Double sourceLat;
    private Double destinationLon;
    private Double destinationLat;
    private List<ParcelQuery> parcels;

    public OrderQuery(Double sourceLon, Double sourceLat, Double destinationLon, Double destinationLat, List<ParcelQuery> parcels) {
        this.sourceLon = sourceLon;
        this.sourceLat = sourceLat;
        this.destinationLon = destinationLon;
        this.destinationLat = destinationLat;
        this.parcels = parcels;
    }
}

