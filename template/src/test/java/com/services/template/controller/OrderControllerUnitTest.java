package com.services.template.controller;

import com.services.template.entities.Order;
import com.services.template.service.OrderService;
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

@WebMvcTest(controllers = OrderController.class)
class OrderControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;


    @Test
    void testGetAllOrdersTest() throws Exception {

        Order mockOrder = Order.builder()
                .oid(UUID.randomUUID())
                .cost(59.99)
                .destination("Dublin")
                .source("London")
                .expectedDelivery("Wednesday")
                .build();

        Mockito.when(orderService.getAllOrders()).thenReturn(List.of(mockOrder));

        String uri = "/orders";
        MockHttpServletRequestBuilder requestBuilders = MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE);

        MvcResult mvcResult = mockMvc.perform(requestBuilders).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString(); //todo an assert here on json structure
    }


}