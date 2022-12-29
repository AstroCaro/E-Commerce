package com.applaudo.csilva.service.impl;

import com.applaudo.csilva.dto.OrderDto;
import com.applaudo.csilva.exception.ResourceNotFoundException;
import com.applaudo.csilva.model.Order;
import com.applaudo.csilva.repository.OrderRepository;
import com.applaudo.csilva.service.OrderService;
import com.applaudo.csilva.utils.ObjectMapperUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Override
    public OrderDto getOrderById(Integer id) {
        Optional<Order> order = orderRepository.findById(id);
        if (order.isPresent()) {
            return ObjectMapperUtils.map(order.get(), OrderDto.class);
        }
        throw new ResourceNotFoundException("Order not found with the given id");
    }

    @Override
    public List<OrderDto> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return ObjectMapperUtils.mapAll(orders, OrderDto.class);
    }

    @Override
    public OrderDto createOrder() {
        return null;
    }

    @Override
    public OrderDto updateOrder() {
        return null;
    }

    @Override
    public boolean deleteOrder(Integer id) {
        return false;
    }
}
