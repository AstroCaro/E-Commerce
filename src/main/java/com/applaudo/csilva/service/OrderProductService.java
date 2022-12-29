package com.applaudo.csilva.service;

import com.applaudo.csilva.dto.OrderProductDto;

import java.util.List;

public interface OrderProductService {

    OrderProductDto getOrderProductById(Integer id);

    List<OrderProductDto> getAllOrderProduct();

    List<OrderProductDto> getAllOrderProductByCheckout(Integer id);

    OrderProductDto createOrderProduct(OrderProductDto orderProductDto, Integer id);

    OrderProductDto updateOrderProduct(OrderProductDto orderProductDto, Integer id);

    OrderProductDto updateOrderProductQuantityById(OrderProductDto orderProductDto, Integer id);

    OrderProductDto deleteOrderProductById(int id);
}
