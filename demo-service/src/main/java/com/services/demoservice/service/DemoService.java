package com.services.demoservice.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DemoService {

    public List<String> getAllParcels() {
        List<String> parcels = new ArrayList<String>();
        parcels.add("Parcel A");
        parcels.add("Parcel B");
        parcels.add("Parcel C");
        return parcels;
    }

}
