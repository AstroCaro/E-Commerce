package com.applaudo.csilva.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InitialCheckoutDto {

    private Integer id;

    @NotNull
    private Integer userId;

    @NotNull
    private Integer productId;

    @NotNull
    private Integer quantity;


}
