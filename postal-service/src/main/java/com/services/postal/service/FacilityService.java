package com.services.postal.service;

import com.services.postal.entities.Facility;
import com.services.postal.repository.FacilityRepository;
import org.springframework.stereotype.Service;

@Service
public class FacilityService {

    private final FacilityRepository facilityRepository;


    public FacilityService(FacilityRepository facilityRepository) {
        this.facilityRepository = facilityRepository;
    }

    /**
     * Find closest facility to lon/lat coordinate.
     * @param lon Double representing longitude coordinate
     * @param lat Double representing latitude coordinate
     * @return Facility closest to input coordinate
     */
    public Facility findClosestFacility(Double lon, Double lat) {
        return this.facilityRepository.findClosestFacility(lon.toString(), lat.toString());
    }
    
}
