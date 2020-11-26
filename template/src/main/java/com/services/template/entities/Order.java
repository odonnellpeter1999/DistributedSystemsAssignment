package com.services.template.entities;

import lombok.*;

import javax.persistence.*;
import java.util.UUID;


/**
 * Shipping order, encapsulates all information about the order
 * Based on how DPD's ordering system is presented to the end user.
 * A shipping order will contain multiple parcels.
 * From ONE source location to ONE destination.
 */
@Entity
@Table(name = "orders")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode
@ToString
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID oid;
    private String source; //change this to either lat/long, geopoint, or postgres(postGIS) compatible, geometry/geography etc
    private String destination;  //change this to either lat/long, geopoint, or postgres(postGIS) compatible, geometry/geography etc
    private Double cost;
    private String expectedDelivery;  //change this to proper date
}
