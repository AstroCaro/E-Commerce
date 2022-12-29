package com.applaudo.csilva.dto;

import com.applaudo.csilva.model.Checkout;
import com.applaudo.csilva.model.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderProductDto {

    private Integer id;

    @NotNull(message = "Quantity is required")
    private Integer quantity;

    @NotNull
    private Integer productId;

    private Integer checkoutId;

    private Double price;
}
