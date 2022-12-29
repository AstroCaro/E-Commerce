package com.applaudo.csilva.service;

import com.applaudo.csilva.dto.CheckoutDto;
import com.applaudo.csilva.dto.InitialCheckoutDto;
import com.applaudo.csilva.dto.OrderProductDto;
import com.applaudo.csilva.model.Checkout;

import java.util.List;

public interface CheckoutService {
    CheckoutDto createOrderProduct(OrderProductDto orderProductDto, Integer id);

    void verifyCheckoutIsEmpty(Checkout checkout);

    CheckoutDto getCheckoutById(int id);

    List<CheckoutDto> getAllCheckouts();

    CheckoutDto createCheckout(InitialCheckoutDto initialCheckoutDto);

    CheckoutDto deleteCheckoutById(Integer id);

    CheckoutDto selectAddressUser(Integer addressId, Integer checkoutId);

    CheckoutDto selectPaymentMethod(Integer paymentMethodId, Integer checkoutId);

    CheckoutDto changeAddressUser(Integer addressId, Integer checkoutId);

    CheckoutDto changePaymentMethod(Integer paymentMethodId, Integer checkoutId);

}
