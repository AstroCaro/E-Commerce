package com.applaudo.csilva.service;

import com.applaudo.csilva.dto.AddressDto;
import com.applaudo.csilva.dto.PaymentMethodDto;
import com.applaudo.csilva.dto.UserDto;

import java.util.List;

public interface UserService {

    UserDto getUserById(Integer id);

    UserDto getUserByEmail(String email);

    List<UserDto> getAllUsers();

    UserDto createUser(UserDto userDto);

    UserDto updateUser(UserDto userDto, Integer id);

    UserDto deleteUser(Integer id);

    List<AddressDto> getAddresses( Integer id);

    List<PaymentMethodDto> getPaymentMethods(Integer id);

    AddressDto createAddressUser(AddressDto addressDto, Integer id);

    PaymentMethodDto createPaymentMethodUser(PaymentMethodDto paymentMethodDto, Integer id);
}
