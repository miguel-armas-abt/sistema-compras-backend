package com.microservices.products;

import com.microservices.products.entity.Category;
import com.microservices.products.entity.Product;
import com.microservices.products.repository.ProductRepository;
import com.microservices.products.service.ProductService;
import com.microservices.products.service.ProductServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
public class ProductServiceMockTest {

    @Mock
    private ProductRepository productRepository;    // datos generados desde Mock

    private ProductService productService;

    // en este metodo se mockean todos los datos para las pruebas unitarias
    @BeforeEach // su ejecucion sera antes del test
    public void setup() {
        MockitoAnnotations.initMocks(this); // inicializo Mockito

        // instancio un ProductServiceImpl y paso la data por constructor
        productService = new ProductServiceImpl(productRepository);

        // creo una instancia de Product
        Product product = Product.builder()
                .id(1L)
                .name("computer")
                .category(Category.builder().id(1L).build())
                .price(Double.parseDouble("12.5"))
                .stock(Double.parseDouble("5")).build();

        // mockeo la instancia del Product
        // cuando busco por id=1, debe retornarme un Optional de product
        Mockito.when(productRepository.findById(1L))
                .thenReturn(Optional.of(product));

        // cuando un producto sea modificado, debe actualizarse tambien el mock
        Mockito.when(productRepository.save(product)).thenReturn(product);
    }

    @Test
    public void whenValidGetId_ThenReturnProduct() {
        Product found = productService.getProduct(1L);
        Assertions.assertThat(found.getName()).isEqualTo("computer");
    }

    @Test
    public void whenValidUpdateStock_ThenReturnNewStock() {
        Product newStock = productService.updateStock(1L, Double.parseDouble("8"));
        Assertions.assertThat(newStock.getStock()).isEqualTo(13);
    }
}
