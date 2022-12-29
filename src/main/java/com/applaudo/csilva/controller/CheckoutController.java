package com.applaudo.csilva.controller;

import com.applaudo.csilva.dto.CheckoutDto;
import com.applaudo.csilva.dto.InitialCheckoutDto;
import com.applaudo.csilva.dto.OrderProductDto;
import com.applaudo.csilva.dto.UserDto;
import com.applaudo.csilva.exception.ResourceFoundException;
import com.applaudo.csilva.exception.ResourceNotFoundException;
import com.applaudo.csilva.model.AddressUser;
import com.applaudo.csilva.model.Checkout;
import com.applaudo.csilva.model.OrderProduct;
import com.applaudo.csilva.service.CheckoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/checkouts")
public class CheckoutController {

    @Autowired
    private CheckoutService checkoutService;

    @GetMapping("/{id}")
    public ResponseEntity<CheckoutDto> checkoutById(@PathVariable final Integer id) throws ResourceNotFoundException {
        return ResponseEntity.ok(checkoutService.getCheckoutById(id));
    }

    @GetMapping
    public ResponseEntity<List<CheckoutDto>> getAllCheckouts() {
        return ResponseEntity.ok(checkoutService.getAllCheckouts());
    }

    @PostMapping
    public ResponseEntity<CheckoutDto> createBasicCheckout(@RequestBody @Valid InitialCheckoutDto checkoutDto) throws ResourceNotFoundException {
        return ResponseEntity.status(HttpStatus.CREATED).body(checkoutService.createCheckout(checkoutDto));
    }

    @PostMapping("/{id}/orderproducts")
    public ResponseEntity<CheckoutDto> createOrderProductInCheckout(@RequestBody @Valid OrderProductDto orderProductDto, @PathVariable("id") Integer id) throws ResourceNotFoundException, ResourceFoundException {
        return ResponseEntity.status(HttpStatus.CREATED).body(checkoutService.createOrderProduct(orderProductDto, id));
    }


    @PutMapping("/{checkoutid}/selectaddress/{id}")
    public ResponseEntity<CheckoutDto> selectAddressUser(@PathVariable("id") Integer id, @PathVariable("checkoutid") Integer checkoutId) throws ResourceNotFoundException {
        return ResponseEntity.status(HttpStatus.CREATED).body(checkoutService.selectAddressUser(id, checkoutId));
    }

    @PutMapping("/{checkoutid}/selectpayment/{id}")
    public ResponseEntity<CheckoutDto> selectPaymentMethodUser(@PathVariable("id") Integer id, @PathVariable("checkoutid") Integer checkoutId) throws ResourceNotFoundException {
        return ResponseEntity.status(HttpStatus.CREATED).body(checkoutService.selectPaymentMethod(id, checkoutId));
    }

    @PutMapping("/{checkoutid}/changeaddress/{id}")
    public ResponseEntity<CheckoutDto> changeAddressUser(@PathVariable("id") Integer id, @PathVariable("checkoutid") Integer checkoutId) throws ResourceNotFoundException {
        return ResponseEntity.status(HttpStatus.CREATED).body(checkoutService.changeAddressUser(id, checkoutId));
    }

    @PutMapping("/{checkoutid}/changepayment/{id}")
    public ResponseEntity<CheckoutDto> changePaymentMethodUser(@PathVariable("id") Integer id, @PathVariable("checkoutid") Integer checkoutId) throws ResourceNotFoundException {
        return ResponseEntity.status(HttpStatus.CREATED).body(checkoutService.changePaymentMethod(id, checkoutId));
    }

}
