package com.microservices.products.controller;

import com.microservices.products.entity.Category;
import com.microservices.products.entity.Product;
import com.microservices.products.service.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@WebMvcTest(value = ProductController.class)    // solo levanto este cotroller
class ProductControllerTest {

    Logger logger = LoggerFactory.getLogger(ProductControllerTest.class);

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    private Product productMockGet;
    private Product productMockPost;

    @BeforeEach
    public void setup () {
        // localdate cast to date
        ZoneId defaultZoneId = ZoneId.systemDefault();
        LocalDate localDate = LocalDate.of(2018, 9, 5);
        Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());

        productMockGet = Product.builder()
                .id(1L)
                .name("zapatillas adidas")
                .description("cloudfoam ultimate")
                .stock(5.0)
                .price(178.89)
                .status("CREATED")
                .createAt(date)
                .category(Category.builder().id(1L).name("zapatos").build())
                .build();

        productMockPost = Product.builder()
                .id(4L)
                .name("sandalias")
                .description("nike")
                .stock(10.0)
                .price(79.00)
                .status("CREATED")
                .createAt(date)
                .category(Category.builder().id(1L).name("zapatos").build())
                .build();

        logger.info("PRODUCTO: " + productMockPost.toString());
    }

    @Test
    public void returnProductById() throws Exception {
        // mockeo el producto service
        Mockito.when(productService.getProduct(1L)).thenReturn(productMockGet);

        // hago la solicitud http
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/products/1")
                .accept(MediaType.APPLICATION_JSON);

        // obtengo la respuesta a la solicitud
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        logger.info("RESPUESTA: " + mvcResult.getResponse());

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{\"id\": 1,")
                .append("\"name\":\"zapatillas adidas\",")
                .append("\"description\":\"cloudfoam ultimate\",")
                .append("\"stock\":5.0,")
                .append("\"price\":178.89,")
                .append("\"status\":\"CREATED\",")
                .append("\"createAt\":\"2018-09-05T05:00:00.000+0000\",")
                .append("\"category\":{\"id\":1,\"name\":\"zapatos\"}")
                .append("}");
        String expected = stringBuilder.toString();
        logger.info("EXPECTED: " + expected);
        logger.info("STRING BUILDER JSON: " + mvcResult.getResponse().getContentAsString());

        JSONAssert.assertEquals(expected, mvcResult.getResponse().getContentAsString(), false);
    }

    @Test
    public void returnProductWhenCreateProduct() throws Exception {
        // mockeo el producto service
        Mockito.when(productService.createProduct(productMockPost)).thenReturn(productMockPost);

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{\"name\":\"sandalias\",")
                .append("\"description\":\"nike\",")
                .append("\"stock\":10,")
                .append("\"price\":79.00,")
                .append("\"category\":{\"id\":1,\"name\":\"zapatos\"}")
                .append("}");
        String body = stringBuilder.toString();

        // hago la solicitud http
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/products/")
                .accept(MediaType.APPLICATION_JSON).content(body)
                .contentType(MediaType.APPLICATION_JSON);

        // obtengo la respuesta a la solicitud
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        Assertions.assertEquals(HttpStatus.CREATED.value(), response.getStatus());
        /*Assertions.assertEquals(
                "http:localhost:8080/products/",
                response.getHeader(HttpHeaders.LOCATION));*/
    }

}