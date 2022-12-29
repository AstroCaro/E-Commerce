package com.applaudo.csilva.controller;

import com.applaudo.csilva.dto.CheckoutDto;
import com.applaudo.csilva.dto.OrderProductDto;
import com.applaudo.csilva.exception.ResourceNotFoundException;
import com.applaudo.csilva.service.OrderProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/")
public class OrderProductController {

    @Autowired
    private OrderProductService orderProductService;

    @DeleteMapping("/checkouts/{checkoutid}/orderproducts/{id}")
    public ResponseEntity<OrderProductDto> deleteOrderProductInCheckout(@PathVariable("id") Integer id) throws ResourceNotFoundException {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderProductService.deleteOrderProductById(id));
    }

    @PutMapping("/checkouts/{checkoutid}/orderproducts/{id}")
    public ResponseEntity<OrderProductDto> changeQuantityOrderProduct(@RequestBody @Valid OrderProductDto orderProductDto, @PathVariable("id") Integer id) throws ResourceNotFoundException {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderProductService.updateOrderProductQuantityById(orderProductDto, id));
    }



}
