package com.applaudo.csilva.service;

import com.applaudo.csilva.dto.OrderDto;
import com.applaudo.csilva.dto.PaymentMethodDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderService {

    OrderDto getOrderById(Integer id);

    List<OrderDto> getAllOrders();

    OrderDto createOrder();

    OrderDto updateOrder();

    boolean deleteOrder(Integer id);
}
