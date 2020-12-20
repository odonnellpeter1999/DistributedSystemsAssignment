package com.services.postal.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Builder
@Data
@Entity
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "parcels")
@ToString
public class Parcel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String contentDescription;
    private Double value;
    private Double weightKg;
    private Double lengthCm;
    private Double widthCm;
    private Double heightCm;
    private String currentLocation; // change this to either lat/long, geopoint, or postgres(postGIS) compatible, geometry/geography etc

    @ManyToOne
    @JoinColumn(name = "oid", referencedColumnName = "oid")
    @NonNull
    private Order order;
}
