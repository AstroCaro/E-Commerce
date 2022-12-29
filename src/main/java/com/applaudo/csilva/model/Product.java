package com.applaudo.csilva.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Product name is required")
    @Column(name = "name", unique = true)
    private String name;

    @PositiveOrZero(message = "Stock must be greater or equals than 0")
    @Column(name = "stock")
    private Integer stock;

    @Positive(message = "Price must be positive")
    @NotNull(message = "Price is required")
    @Column(name = "price")
    private Double price;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "product", orphanRemoval = true)
    private List<OrderProduct> orderProducts = new ArrayList<>();

}
