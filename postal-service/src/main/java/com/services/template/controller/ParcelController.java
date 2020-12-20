package com.services.template.controller;

import com.services.template.entities.Parcel;
import com.services.template.service.ParcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/parcels", produces = MediaType.APPLICATION_JSON_VALUE)
public class ParcelController {

    private final ParcelService parcelService;

    @Autowired
    public ParcelController(ParcelService parcelService) {
        this.parcelService = parcelService;
    }

    @GetMapping
    public List<Parcel> getAllParcels() {
        return parcelService.getAllParcels();
    }

    @GetMapping(params = "parcelId")
    public Parcel getParcelById(@RequestParam String parcelId) {
        return parcelService.getParcelById(parcelId);
    }

    @GetMapping(params = "orderId")
    public List<Parcel> getParcelsByOrderId(@RequestParam String orderId) {
        return parcelService.getParcelsByOrderId(orderId);
    }
}
