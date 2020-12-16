package com.microservices.products.entity;

import lombok.*;

import javax.persistence.*;

@Entity // defino la entidad
@Table(name = "tb_categories")  // si el nombre de la tabla es distinto al de la clase
// @Getter  @Setter // getters and setters
@Data // getters, setters, hashcode, equals, toString
@AllArgsConstructor @NoArgsConstructor @Builder
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // pk auto incrementable
    private Long id;
    private String name;
}
