package com.microservices.compras.client;

import com.microservices.compras.model.Customer;
import org.springframework.http.ResponseEntity;

public class CustomerHystrixFallbackFactory implements CustomerClient{

    // este es el plan de accion B
    @Override
    public ResponseEntity<Customer> getCustomer(long id) {

        Customer customer = Customer.builder()
                .firstName("none")
                .lastName("none")
                .email("none")
                .photoUrl("none").build();
        return ResponseEntity.ok(customer);
    }
}
