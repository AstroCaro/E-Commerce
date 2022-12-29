package com.applaudo.csilva.controller;

import com.applaudo.csilva.dto.AddressDto;
import com.applaudo.csilva.dto.PaymentMethodDto;
import com.applaudo.csilva.dto.UserDto;
import com.applaudo.csilva.exception.ResourceFoundException;
import com.applaudo.csilva.exception.ResourceNotFoundException;
import com.applaudo.csilva.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable final Integer id) throws ResourceNotFoundException {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody @Valid UserDto userDto) throws ResourceFoundException {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(userDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable("id") Integer id) {
        return ResponseEntity.ok().body(userService.updateUser(userDto, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UserDto> deleteUser(@PathVariable("id") Integer id) throws ResourceNotFoundException {
        return new ResponseEntity<UserDto>(userService.deleteUser(id), HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}/addresses")
    public ResponseEntity<List<AddressDto>> getAddresses(@PathVariable final Integer id) {
        return ResponseEntity.ok(userService.getAddresses(id));
    }

    @PostMapping("/{id}/addresses")
    public ResponseEntity<AddressDto> createAddressUser(@Valid @RequestBody AddressDto addressDto, @PathVariable("id") Integer id) throws ResourceFoundException, ResourceNotFoundException {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createAddressUser(addressDto, id));
    }

    @GetMapping("/{id}/payments")
    public ResponseEntity<List<PaymentMethodDto>> getPaymentMethods(@PathVariable final Integer id) {
        return ResponseEntity.ok(userService.getPaymentMethods(id));
    }

    @PostMapping("/{id}/payments")
    public ResponseEntity<PaymentMethodDto> createPaymentUser(@Valid @RequestBody PaymentMethodDto paymentMethodDto, @PathVariable("id") Integer id) throws ResourceFoundException, ResourceNotFoundException {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createPaymentMethodUser(paymentMethodDto, id));
    }






}
