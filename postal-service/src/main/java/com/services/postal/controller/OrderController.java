package com.services.postal.controller;

import com.services.postal.entities.Order;
import com.services.postal.errors.NoSuchOrderException;
import com.services.postal.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.validation.Valid;

@RestController
@Tag(name = "Service", description = "Postal Service API")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @Operation(summary = "Requestion quotation", description = "Endpoint for quotation requests")
    @PostMapping(value = "/quote", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Order getQuotation(@Valid @RequestBody Order application) {
        Order quoteOrder = this.orderService.createOrder(application);
        return quoteOrder;
    }

    @Operation(summary = "Place order", description = "Endpoint to place a new order")
    @PostMapping(value = "/orders", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Order> placeOrder(@Valid @RequestBody Order application) throws URISyntaxException {
        Order newOrder = this.orderService.createOrder(application);
        newOrder = this.orderService.saveOrder(newOrder);
        String path = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString()+ "/track/" + newOrder.getOid();
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(new URI(path));
        return new ResponseEntity<>(newOrder, headers, HttpStatus.CREATED);
    }

    @Operation(summary = "Get list of orders", description = "Endpoint for retrieving list of all orders")
    @GetMapping(value = "/orders", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    @Operation(summary = "Get specific order", description = "Endpoint for retrieving specific order")
    @GetMapping(value = "/track/{orderId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Order getOrderById(@PathVariable("orderId") String orderId) {
        Optional<Order> order = this.orderService.getOrderById(orderId);
        
        if (!order.isPresent()) {
            throw new NoSuchOrderException();
        } 

        return order.get();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach((error) -> {
            String fieldName = error.getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

}
