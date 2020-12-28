package com.microservices.products.repository;

import com.microservices.products.entity.Category;
import com.microservices.products.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// @Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByCategory(Category category);
}
