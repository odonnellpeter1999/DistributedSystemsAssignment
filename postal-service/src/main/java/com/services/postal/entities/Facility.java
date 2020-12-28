package com.services.postal.entities;

import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.UUID;

@Builder
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "facilities")
public class Facility implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID oid;

    @NotNull
    private String name;

    @DecimalMax("180") 
    @DecimalMin("-180")
    @NotNull 
    private Double locationLon;

    @DecimalMax("90") 
    @DecimalMin("-90")
    @NotNull
    private Double locationLat;
}
