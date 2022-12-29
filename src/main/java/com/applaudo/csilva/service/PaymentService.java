package com.applaudo.csilva.service;

import com.applaudo.csilva.dto.PaymentMethodDto;

import java.util.List;

public interface PaymentService {

    PaymentMethodDto getPaymentMethodById(Integer id);

    List<PaymentMethodDto> getAllPaymentMethods();

    List<PaymentMethodDto> getPaymentMethodsByUser(Integer userId);

    PaymentMethodDto createPaymentMethodUser(PaymentMethodDto paymentMethodDto, Integer id);

    PaymentMethodDto deletePaymentMethodId(Integer id);
}
