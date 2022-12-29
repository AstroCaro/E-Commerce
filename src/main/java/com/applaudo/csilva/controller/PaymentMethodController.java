package com.applaudo.csilva.controller;

import com.applaudo.csilva.dto.AddressDto;
import com.applaudo.csilva.dto.PaymentMethodDto;
import com.applaudo.csilva.exception.ResourceNotFoundException;
import com.applaudo.csilva.model.PaymentMethod;
import com.applaudo.csilva.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
public class PaymentMethodController {

    @Autowired
    private PaymentService paymentService;

    @GetMapping("/{id}")
    public ResponseEntity<PaymentMethodDto> getPaymentMethodById(@PathVariable final Integer id) throws ResourceNotFoundException {
        return ResponseEntity.ok(paymentService.getPaymentMethodById(id));
    }

    @GetMapping
    public ResponseEntity<List<PaymentMethodDto>> getAllPaymentMethods() {
        return ResponseEntity.ok(paymentService.getAllPaymentMethods());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PaymentMethodDto> deletePaymentMethodId(@PathVariable("id") Integer id) throws ResourceNotFoundException {
        return new ResponseEntity<PaymentMethodDto>(paymentService.deletePaymentMethodId(id), HttpStatus.NO_CONTENT);
    }


}
