package com.microservices.compras.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.microservices.compras.model.Customer;
import lombok.Data;

import javax.persistence.*;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "tb_invoices")
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "number_invoice")
    private String numberInvoice;

    private String description;

    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "create_at")
    @Temporal(TemporalType.DATE)
    private Date createAt;

    @Valid  // validacion en cada columna de un InvoiceItem
    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })

    // si se crea una cabecera, se crea su detalle
    // si no existe la cabecer, no existe su detealle
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "invoice_id")
    private List<InvoiceItem> items;

    private String state;

    public Invoice(){
        items = new ArrayList<>();
    }

    @PrePersist // registro automatico de la fecha antes de insertar en la base de datos
    public void prePersist() {
        this.createAt = new Date();
    }





    // clientes feign
    @Transient
    private Customer customer;

}
