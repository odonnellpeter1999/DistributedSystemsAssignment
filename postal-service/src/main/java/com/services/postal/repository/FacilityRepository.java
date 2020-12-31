package com.services.postal.repository;

import com.services.postal.entities.Facility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import javax.transaction.Transactional;
import java.util.UUID;

@Transactional
public interface FacilityRepository extends JpaRepository<Facility, UUID> {

    @Query(value = "SELECT *," +
    "(" +
        "6371 * " +
        "ACOS(COS(RADIANS(:lon)) * " +
        "COS(RADIANS(LOCATION_LAT)) * " +
        "COS(RADIANS(LOCATION_LON) - " +
        "RADIANS(:lat)) + " +
        "SIN(RADIANS(:lon)) * " +
        "SIN(RADIANS(LOCATION_LAT)))" +
     ") AS distance " +
     "FROM FACILITIES " +
     "ORDER BY distance LIMIT 1", nativeQuery = true)
    Facility findClosestFacility(@Param("lon") Double lon, @Param("lat") Double lat);

}