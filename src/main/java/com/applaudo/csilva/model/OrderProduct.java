package com.applaudo.csilva.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "order_products")
public class OrderProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "product_id")
    private Product product;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "checkout_id")
    private Checkout checkout;

    @NotNull
    @PositiveOrZero
    private Integer quantity;

    @JsonIgnore
    private Double price;

    public OrderProduct(Product product, Integer quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public void setPrice() {
        this.price = getProduct().getPrice() * getQuantity();
    }

    @Transient
    @JsonIgnore
    public boolean isNull() {
        return false;
    }

    public OrderProduct(Product product, Integer quantity, Double price) {
        this.product = product;
        this.quantity = quantity;
        this.price = price;
    }
}
