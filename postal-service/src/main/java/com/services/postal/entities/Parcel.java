package com.services.postal.entities;

import lombok.*;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.UUID;

@Builder
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "parcels")
public class Parcel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID oid;
    private Double weightKg;
    private Double lengthCm;
    private Double widthCm;
    private Double heightCm;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "orderOid", referencedColumnName = "oid")
    @NonNull
    @JsonIgnore
    private Order order;

    public Double calcVolume() {
        return this.widthCm * this.lengthCm * this.heightCm;
    }
}
