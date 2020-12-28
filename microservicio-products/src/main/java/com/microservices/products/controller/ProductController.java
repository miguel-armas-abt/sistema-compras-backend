package com.microservices.products.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservices.products.entity.Category;
import com.microservices.products.entity.Product;
import com.microservices.products.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<List<Product>> listProduct(@RequestParam(name="categoryId", required = false) Long categoryId) {
        List<Product> products = new ArrayList<>();

        // si no pertenece a una categoria, devuelvo la lista completa
        if(categoryId == null) {
            products = productService.listAllProduct();
            if(products.isEmpty())
                return  ResponseEntity.noContent().build();
        }
        else {
            products = productService.findByCategory(Category.builder().id(categoryId).build());
            if(products.isEmpty())
                return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(products);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable("id") Long id) {
        Product productDb = productService.getProduct(id);
        if(productDb == null ) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(productDb);
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@Valid @RequestBody Product product, BindingResult result) {   // BindingResult devuelve el resultado de las validaciones

        // si hay errores con las validaciones
        if(result.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.formatMessage(result));
        }
        Product productCreate = productService.createProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(productCreate);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable("id") Long id, @RequestBody Product product) {
        product.setId(id);
        Product productDb = productService.updateProduct(product);
        if(productDb == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(productDb);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Product> deleteProduct(@PathVariable("id") Long id) {
        Product productDelete = productService.deleteProduct(id);
        if(productDelete == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(productDelete);
    }

    @GetMapping(value = "/{id}/stock")
    public ResponseEntity<Product> updateStockProduct(@PathVariable("id") Long id, @RequestParam(name = "quantity", required = true) Double quantity) {
        Product productDb = productService.updateStock(id, quantity);
        if(productDb == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(productDb);
    }

    private String formatMessage(BindingResult result) {
        List<Map<String, String>> errors = result.getFieldErrors().stream()
                .map(err -> {
                    Map<String, String> error = new HashMap<>();
                    // getField(): campo donde se genera el error
                    // DefaultMessage(): mensaje del error
                    error.put(err.getField(), err.getDefaultMessage());
                    return error;
                }).collect(Collectors.toList());

        ErrorMessage errorMessage = ErrorMessage.builder()
                .code("errors")
                .messages(errors).build();

        // convierto ErrorMessage en un JSON
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = "";
        try{
            jsonString = mapper.writeValueAsString(errorMessage);   // convierto
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
        }
        return jsonString;
    }
}
