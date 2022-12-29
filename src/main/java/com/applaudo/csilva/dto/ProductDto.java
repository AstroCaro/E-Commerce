package com.applaudo.csilva.dto;

import com.applaudo.csilva.model.OrderProduct;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Data
@NoArgsConstructor
public class ProductDto {

    private Integer id;

    @NotBlank(message = "Product name is required")
    private String name;

    @PositiveOrZero(message = "Stock must be greater or equals than 0")
    private Integer stock;

    @Positive(message = "Price must be positive")
    @NotNull(message = "Price is required")
    private Double price;

    private List<OrderProductDto> orderProducts;

    public ProductDto(String name, Integer stock, Double price) {
        this.name = name;
        this.stock = stock;
        this.price = price;
    }

}
