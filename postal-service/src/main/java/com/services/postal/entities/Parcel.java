package com.services.postal.entities;

import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
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

    @NotNull
    @Positive
    private Double weightKg;

    @NotNull
    @Positive
    private Double lengthCm;

    @NotNull
    @Positive
    private Double widthCm;

    @NotNull
    @Positive
    private Double heightCm;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "orderOid", referencedColumnName = "oid")
    @JsonIgnore
    private Order order;

    public Double calcVolume() {
        return this.widthCm * this.lengthCm * this.heightCm;
    }
}
