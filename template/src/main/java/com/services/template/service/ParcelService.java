package com.services.template.service;

import com.services.template.entities.Parcel;
import com.services.template.repository.ParcelRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ParcelService {


    final ParcelRepository parcelRepository;

    public ParcelService(ParcelRepository parcelRepository) {
        this.parcelRepository = parcelRepository;
    }

    public List<Parcel> getAllParcels() {
        List<Parcel> parcels = new ArrayList<Parcel>();
        parcelRepository.findAll().forEach(parcels::add);
        return parcels;
    }

    public List<Parcel> getParcelsByOrderId(String orderId) {
        return parcelRepository.findParcelByOrderId(orderId);
    }

    public Parcel getParcelById(String parcelId) {
        return parcelRepository.findById(UUID.fromString(parcelId)).get();
    }
}
