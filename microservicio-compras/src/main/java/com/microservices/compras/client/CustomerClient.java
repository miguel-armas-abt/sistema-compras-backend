package com.microservices.compras.client;

import com.microservices.compras.model.Customer;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

// cliente-service segun el archivo de configuracion cliente-service.yml
// el plan de accion B (fallback) esta implementado CustomerHystrixFallbackFactory
@FeignClient(name = "cliente-service", fallback = CustomerHystrixFallbackFactory.class )

// quitamos el recurso /customers y lo colocamos directo en el verbo HTTP
// Esto con el proposito de implementar el fallback
// @RequestMapping("/customers")
public interface CustomerClient {

    // si la llamada a este recurso falla, se realizara la peticion al fallback
    @GetMapping(value = "customers/{id}")
    public ResponseEntity<Customer> getCustomer(@PathVariable("id") long id);   // declaro el metodo que voy a consumir

}
