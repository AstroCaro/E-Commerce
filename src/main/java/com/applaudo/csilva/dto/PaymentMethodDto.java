package com.applaudo.csilva.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentMethodDto {

    private Integer id;

    @NotBlank(message = "Payment method's name is mandatory")
    private String name;

    private boolean enabled;

    private Integer userId;

}
