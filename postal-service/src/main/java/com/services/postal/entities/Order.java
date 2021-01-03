package com.services.postal.entities;

import lombok.*;
import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonInclude;
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
@Getter
@Setter
@Builder
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID oid;

    @DecimalMax("180") 
    @DecimalMin("-180")
    @NotNull
    private Double sourceLon;

    @DecimalMax("90") 
    @DecimalMin("-90")
    @NotNull 
    private Double sourceLat;

    @DecimalMax("180") 
    @DecimalMin("-180")
    @NotNull 
    private Double destinationLon;

    @DecimalMax("90") 
    @DecimalMin("-90")
    @NotNull
    private Double destinationLat;

    // Values determined by server, do not need to be validated
    private Double cost; // Total cost of the order
    private Date dateOrdered; // Date order was placed
    private Date dateExpected; // Expected delivery date
    private Date dateDelivered; // Date order was delivered
    private transient String serviceName; // Not stored to database
    private transient String serviceId; // Not stored to database
    private transient String trackingId; // PostalServiceId + Oid

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "facilityOid", referencedColumnName = "oid")
    private Facility facility;

    @NotEmpty
    @Valid
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "order")
    private List<Parcel> parcels;

    /**
     * Getter for status of order, determined by internal properties.
     * @return String - Status of order
     */
    public String getStatus() {
        if (this.oid == null) {
            return "QUOTATION"; // Order has not been placed yet
        }
        if (this.dateDelivered != null) {
            return "DELIVERED"; // Order has been delivered 
        }
        if (this.facility != null) {
            return "AT SORTING FACILITY"; // Order is still at source but placed
        }
        return "ORDER CONFIRMED";
    }

    /**
     * Calculates and sets the total cost of this order based on
     * the total weight, volume and distance.
     * @param multiplier - Multiply the calculates cost with this constant
     */
    public void calcCost(Double multiplier) {
        Double costDistance = this.calcDistance() * 0.000001;
        Double costVolume = this.calcVolume() * 0.0001;
        Double costWeight = this.calcWeight() * 1.25;
        Double cost = (costDistance + costVolume + costWeight) * multiplier;
        this.cost = (double) Math.round(cost * 100) / 100;
    }

    /**
     * Calculates and sets the expected delivery date based on
     * delivery speed, distance and order date.
     * @param speed Double - in meters per second
     */
    public void calcDateExpected(Double speed) {
        int expectedSeconds = (int) Math.ceil(this.calcDistance() / speed); // 200km a day?
        Calendar cal = Calendar.getInstance();
        cal.setTime(this.dateOrdered);
        cal.add(Calendar.SECOND, expectedSeconds);
        this.dateExpected = cal.getTime(); // Set expected delivery date
    }

    /**
     * Calculates the total weight of all parcels included in this order.
     * @return Double - weight in kg
     */
    private double calcWeight() {
        double result = 0.;
        for (Parcel parcel : this.getParcels()) {
            result += parcel.getWeightKg();
        }
        return result;
    }

    /**
     * Calculates the total volume of all parcels included in this order.
     * @return Double - volume in cm^3
     */
    private double calcVolume() {
        double result = 0.;
        for (Parcel parcel : this.getParcels()) {
            result += parcel.calcVolume();
        }
        return result;
    }

    /**
     * Calculates the distance between source and destination.
     * @return Distance in Meters
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

    public Date getDateExpected() {
        return dateExpected;
    }

    public Date getDateDelivered() {
        return dateDelivered;
    }

    public Double getCost() {
        return cost;
    }

    public Date getDateOrdered() {
        return dateOrdered;
    }

    public UUID getOid() {
        return oid;
    }

    public Double getDestinationLat() {
        return destinationLat;
    }

    public String getTrackingId() {
        return trackingId;
    }

    public Double getDestinationLon() {
        return destinationLon;
    }

    public Facility getFacility() {
        return facility;
    }

    public List<Parcel> getParcels() {
        return parcels;
    }

    public String getServiceId() {
        return serviceId;
    }
    
    public String getServiceName() {
        return serviceName;
    }

    public Double getSourceLat() {
        return sourceLat;
    }

    public Double getSourceLon() {
        return sourceLon;
    }

	public void setDateDelivered(Date date) {
        this.dateDelivered = date;
	}

	public void setFacility(Facility newFacility) {
        this.facility = newFacility;
	}
}
