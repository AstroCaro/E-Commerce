package com.applaudo.csilva.dto;

import com.applaudo.csilva.model.OrderProduct;
import com.applaudo.csilva.model.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class OrderDto {

    private Integer id;

    private Integer checkoutId;
}
