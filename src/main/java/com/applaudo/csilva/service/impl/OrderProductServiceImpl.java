package com.applaudo.csilva.service.impl;

import com.applaudo.csilva.dto.OrderProductDto;
import com.applaudo.csilva.exception.ResourceFoundException;
import com.applaudo.csilva.exception.ResourceNotFoundException;
import com.applaudo.csilva.model.Checkout;
import com.applaudo.csilva.model.OrderProduct;
import com.applaudo.csilva.model.Product;
import com.applaudo.csilva.model.nullobjects.NullOrderProduct;
import com.applaudo.csilva.repository.CheckoutRepository;
import com.applaudo.csilva.repository.OrderProductRepository;
import com.applaudo.csilva.repository.ProductRepository;
import com.applaudo.csilva.service.CheckoutService;
import com.applaudo.csilva.service.OrderProductService;
import com.applaudo.csilva.utils.ObjectMapperUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class OrderProductServiceImpl implements OrderProductService {

    private final OrderProductRepository orderProductRepository;
    private final ProductRepository productRepository;
    private final CheckoutRepository checkoutRepository;
    private final CheckoutService checkoutService;


    @Override
    public OrderProductDto getOrderProductById(Integer id) {
        Optional<OrderProduct> orderProduct = orderProductRepository.findById(id);
        if(orderProduct.isPresent()) {
            return ObjectMapperUtils.map(orderProduct.get(), OrderProductDto.class);
        }
        throw new ResourceNotFoundException("OrderProduct not found with the given id");
    }

    @Override
    public List<OrderProductDto> getAllOrderProduct() {
        List<OrderProduct> orderProducts = orderProductRepository.findAll();
        return ObjectMapperUtils.mapAll(orderProducts, OrderProductDto.class);
    }

    @Override
    public List<OrderProductDto> getAllOrderProductByCheckout(Integer id) {
        List<OrderProduct> orderProducts = orderProductRepository.findAllByCheckout_Id(id);
        return ObjectMapperUtils.mapAll(orderProducts, OrderProductDto.class);
    }

    @Override
    public OrderProductDto createOrderProduct(OrderProductDto orderProductDto, Integer id) {
        Checkout checkout = checkoutRepository.getReferenceById(id);
        Product product = productRepository.getReferenceById(orderProductDto.getProductId());
        if (checkout == null) {
            throw new ResourceNotFoundException("Checkout not found with the given id");
        }
        if (product == null) {
            throw new ResourceNotFoundException("Product not found with the given id");
        }
        if (orderProductRepository.findByCheckoutAndProduct(checkout, product) != null) {
            throw new ResourceFoundException("Product already exits for the checkout with the given id");
        }
        OrderProduct orderProduct = ObjectMapperUtils.map(orderProductDto, OrderProduct.class);
        orderProduct.setPrice();
        OrderProduct orderProductCreated = orderProductRepository.save(orderProduct);
        orderProductCreated.setCheckout(checkout);
        return ObjectMapperUtils.map(orderProductCreated, OrderProductDto.class);
    }

    @Override
    public OrderProductDto updateOrderProduct(OrderProductDto orderProductDto, Integer id) {
        OrderProduct orderProduct = orderProductRepository.getReferenceById(id);
        Checkout checkout = checkoutRepository.getReferenceById(orderProductDto.getCheckoutId());
        Product product = productRepository.getReferenceById(orderProductDto.getProductId());
        if (orderProduct == null || checkout == null || product == null) {
            throw new ResourceNotFoundException("Resources not found with the given ids");
        }
        if (orderProductDto.getQuantity() == 0) {
            deleteOrderProductById(id);
            checkoutService.verifyCheckoutIsEmpty(checkout);
            return ObjectMapperUtils.map(NullOrderProduct.build(), OrderProductDto.class);
        }
        OrderProduct orderProductEntity = ObjectMapperUtils.map(orderProductDto, OrderProduct.class);
        orderProduct.setProduct(product);
        orderProduct.setPrice();
        orderProduct.setQuantity(orderProductEntity.getQuantity());
        orderProduct = orderProductRepository.save(orderProduct);
        return ObjectMapperUtils.map(orderProduct, OrderProductDto.class);
    }

    @Override
    public OrderProductDto updateOrderProductQuantityById(OrderProductDto orderProductDto, Integer id) {
        OrderProduct orderProduct = orderProductRepository.getReferenceById(id);
        Checkout checkout = checkoutRepository.getReferenceById(orderProductDto.getCheckoutId());
        Product product = productRepository.getReferenceById(orderProductDto.getProductId());
        if (orderProduct == null || checkout == null || product == null) {
            throw new ResourceNotFoundException("Resources not found with the given ids");
        }
        if (orderProductDto.getQuantity() == 0) {
            deleteOrderProductById(id);
            checkoutService.verifyCheckoutIsEmpty(checkout);
            return ObjectMapperUtils.map(NullOrderProduct.build(), OrderProductDto.class);
        }
        orderProduct.setQuantity(orderProductDto.getQuantity());
        orderProduct.setPrice();
        orderProduct = orderProductRepository.save(orderProduct);
        return ObjectMapperUtils.map(orderProduct, OrderProductDto.class);
    }

    @Override
    public OrderProductDto deleteOrderProductById(int id) {
        Optional<OrderProduct> orderProduct = orderProductRepository.findById(id);
        if (orderProduct.isPresent()) {
            OrderProductDto orderProductDto = ObjectMapperUtils.map(orderProduct, OrderProductDto.class);
            orderProductRepository.deleteById(id);
            checkoutService.verifyCheckoutIsEmpty(orderProduct.get().getCheckout());
            return orderProductDto;
        }
        throw new ResourceNotFoundException("Order Product not found with the given id");
    }


}
