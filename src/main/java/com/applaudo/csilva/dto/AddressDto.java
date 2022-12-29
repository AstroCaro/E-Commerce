package com.applaudo.csilva.dto;

import com.applaudo.csilva.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressDto implements Serializable {

    private Integer id;

    @NotBlank(message = "Address is mandatory")
    private String address;

    @NotBlank
    private String city;

    @NotNull
    private boolean enabled;

    private Integer userId;

}
