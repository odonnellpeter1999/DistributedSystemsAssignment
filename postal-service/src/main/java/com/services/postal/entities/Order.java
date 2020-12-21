package com.services.postal.entities;

import lombok.*;
import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Shipping order, encapsulates all information about the order
 * Based on how DPD's ordering system is presented to the end user.
 * A shipping order will contain multiple parcels.
 * From ONE source location to ONE destination.
 */
@Builder
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID oid;
    private Double sourceLon;
    private Double sourceLat;
    private Double destinationLon;
    private Double destinationLat;
    private Double cost;
    private Date dateOrder;
    private Date dateDelivery;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "order")
    private List<Parcel> parcels;

    public void calcCost(Double serviceMultiplier) {
        Double costDistance = this.calcDistance() * 0.000001;
        Double costVolume = this.calcVolume() * 0.0001;
        Double costWeight = this.calcWeight() * 1.25;
        this.cost = (costDistance + costVolume + costWeight) * serviceMultiplier;
    }

    public void calcDelivery() {
        int expectedDays = (int) Math.ceil(this.calcDistance() / 200000); // 200km a day
        Calendar cal = Calendar.getInstance();
        cal.setTime(this.dateOrder);
        cal.add(Calendar.DATE, expectedDays);
        this.dateDelivery = cal.getTime();
    }

    private double calcWeight() {
        double result = 0.;
        for (Parcel parcel : this.getParcels()) {
            result += parcel.getWeightKg();
        }
        return result;
    }

    private double calcVolume() {
        double result = 0.;
        for (Parcel parcel : this.getParcels()) {
            result += (parcel.getLengthCm() * parcel.getWidthCm() * parcel.getHeightCm());
        }
        return result;
    }

    /**
     * Calculate distance between two points in latitude and longitude.
     * 
     * lat1, lon1 Start point lat2, lon2 End point
     * @returns Distance in Meters
     */
    private double calcDistance() {
        final int R = 6371; // Radius of the earth

        double latDistance = Math.toRadians(this.destinationLat - this.sourceLat);
        double lonDistance = Math.toRadians(this.destinationLon - this.sourceLon);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
            + Math.cos(Math.toRadians(this.sourceLat)) * Math.cos(Math.toRadians(this.destinationLat))
            * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters

        distance = Math.pow(distance, 2);

        return Math.sqrt(distance);
    }
}
