package com.microservices.products.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Date;

@Entity
@Table(name = "tb_products")
@Data
@AllArgsConstructor // constructor con todos los atributos
@NoArgsConstructor  // constructor vacio
@Builder    // construir nuevas instancias de la clase con el metodo builder()
public class Product {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "El nombre no puede ser vacio")
    private String name;
    private String description;

    @Positive(message = "El stock debe ser mayor que 0")
    private Double stock;
    private Double price;
    private String status;

    @Column(name = "create_at") // si el nombre de la columna en la tabla es distinta del atributo en la clase
    @Temporal(TemporalType.TIMESTAMP)   // tiempo mapeado en fecha y hora
    private Date createAt;

    @NotNull(message = "La categoria no puede ser null")
    @ManyToOne(fetch = FetchType.LAZY)  // relacion *...1 , con carga perezosa (se cargara cuando se requiera)
    @JoinColumn(name = "category_id")  // mapeo de las columnas de la tabla foranea, a traves de su pk
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})  // ignorar inicializacion lazy de category
    private Category category;
}





















