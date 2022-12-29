package com.applaudo.csilva.controller;

import com.applaudo.csilva.dto.AddressDto;
import com.applaudo.csilva.exception.ResourceNotFoundException;
import com.applaudo.csilva.service.AddressService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/addresses")
public class AddressController {

    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<AddressDto> getAddressById(@PathVariable final Integer id) throws ResourceNotFoundException {
        return ResponseEntity.ok(addressService.getAddressById(id));
    }

    @GetMapping
    public ResponseEntity<List<AddressDto>> getAllAddress() {
        return ResponseEntity.ok(addressService.getAllAddress());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<AddressDto> deleteAddressById(@PathVariable("id") Integer id) throws ResourceNotFoundException {
        return new ResponseEntity<AddressDto>(addressService.deleteAddressById(id), HttpStatus.NO_CONTENT);
    }

}
