package com.applaudo.csilva.service;

import com.applaudo.csilva.dto.AddressDto;

import java.util.List;

public interface AddressService {


    AddressDto getAddressById(Integer id);

    List<AddressDto> getAllAddress();

    List<AddressDto> getAddressesByUser(Integer userId);

    AddressDto getEnabledAddressByUser(Integer userId);

    AddressDto createAddressUser(AddressDto addressDto, Integer id);

    AddressDto deleteAddressById(Integer id);
}
