package com.applaudo.csilva.dto;

import com.applaudo.csilva.model.AddressUser;
import com.applaudo.csilva.model.OrderProduct;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckoutDto {

    private Integer id;

    @NotNull
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dateCreated;

    private Integer addressUserId;

    private Integer paymentMethodId;

    @Positive
    private Double totalPrice;

    private List<OrderProductDto> orderProducts;

    private Integer userId;

}
