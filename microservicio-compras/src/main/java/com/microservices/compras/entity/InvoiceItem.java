package com.microservices.compras.entity;

import com.microservices.compras.model.Product;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Positive;

@Entity
@Data
@Table(name = "tb_invoce_items")
public class InvoiceItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Positive(message = "El stock debe ser mayor que cero")
    private Double quantity;
    private Double  price;

    @Column(name = "product_id")
    private Long productId;

    @Transient  // atributo no considerado al momento de persistir el objeto
    private Double subTotal;

    public Double getSubTotal(){
        if (this.price >0  && this.quantity >0 ){
            return this.quantity * this.price;
        }else {
            return (double) 0;
        }
    }

    public InvoiceItem(){
        this.quantity=(double) 0;
        this.price=(double) 0;
    }


    // clientes feign
    @Transient
    private Product product;
}
