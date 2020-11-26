package com.services.template.controller;

import com.services.template.entities.Order;
import com.services.template.entities.Parcel;
import com.services.template.service.ParcelService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@WebMvcTest(controllers = ParcelController.class)
class ParcelControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ParcelService parcelService;


    @Test
    void testGetAllParcelsTest() throws Exception {

        Order mockOrder = Order.builder()
                .oid(UUID.randomUUID())
                .cost(59.99)
                .destination("Dublin")
                .source("London")
                .expectedDelivery("Wednesday")
                .build();

        List<Parcel> mockParcels = List.of(
                Parcel.builder()
                        .id(UUID.randomUUID())
                        .value(499.99)
                        .contentDescription("Some Item")
                        .currentLocation("London")
                        .heightCm(50.5)
                        .lengthCm(50.5)
                        .widthCm(50.5)
                        .weightKg(20.23)
                        .order(mockOrder)
                        .build()
        );
        Mockito.when(parcelService.getAllParcels()).thenReturn(mockParcels);

        String uri = "/parcels";
        MockHttpServletRequestBuilder requestBuilders = MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE);

        MvcResult mvcResult = mockMvc.perform(requestBuilders).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString(); //todo an assert here on json structure
    }


}