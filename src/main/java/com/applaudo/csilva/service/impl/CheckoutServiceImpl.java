package com.applaudo.csilva.service.impl;

import com.applaudo.csilva.dto.CheckoutDto;
import com.applaudo.csilva.dto.InitialCheckoutDto;
import com.applaudo.csilva.dto.OrderProductDto;
import com.applaudo.csilva.exception.ResourceFoundException;
import com.applaudo.csilva.exception.ResourceNotFoundException;
import com.applaudo.csilva.model.*;
import com.applaudo.csilva.repository.*;
import com.applaudo.csilva.service.CheckoutService;
import com.applaudo.csilva.service.OrderProductService;
import com.applaudo.csilva.utils.ObjectMapperUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CheckoutServiceImpl implements CheckoutService {

    private final CheckoutRepository checkoutRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final OrderProductRepository orderProductRepository;
    private final AddressRepository addressRepository;
    private final PaymentRepository paymentRepository;

    @Override
    public CheckoutDto getCheckoutById(int id) {
        Optional<Checkout> checkout = checkoutRepository.findById(id);
        if(checkout.isPresent()) {
            return ObjectMapperUtils.map(checkout.get(), CheckoutDto.class);
        }
        throw new ResourceNotFoundException("Checkout not found with the given id");
    }

    @Override
    public List<CheckoutDto> getAllCheckouts() {
        List<Checkout> checkouts = checkoutRepository.findAll();
        return ObjectMapperUtils.mapAll(checkouts, CheckoutDto.class);
    }

    @Override
    public CheckoutDto createCheckout(InitialCheckoutDto initialCheckoutDto) {
        Optional<User> user = userRepository.findById(initialCheckoutDto.getUserId());
        Optional<Product> product = productRepository.findById(initialCheckoutDto.getProductId());
        if (!user.isPresent()) {
            throw new ResourceNotFoundException("User not found with the given id");
        }
        if (!product.isPresent()) {
            throw new ResourceNotFoundException("Product not found with the given id");
        }
        OrderProduct initialOrderProduct = new OrderProduct(product.get(), initialCheckoutDto.getQuantity());
        initialOrderProduct.setProduct(product.get());
        initialOrderProduct.setPrice();
        List<OrderProduct> orderProducts = new ArrayList<>();
        orderProducts.add(initialOrderProduct);
        Checkout checkout = new Checkout(orderProducts, user.get());
        checkout.setDateCreated(LocalDate.now());
        checkout = checkoutRepository.save(checkout);
        initialOrderProduct.setCheckout(checkout);
        orderProductRepository.save(initialOrderProduct);
        return ObjectMapperUtils.map(checkout, CheckoutDto.class);
    }

    @Override
    public CheckoutDto createOrderProduct(OrderProductDto orderProductDto, Integer id) {
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
        orderProduct.setProduct(product);
        orderProduct.setPrice();
        orderProduct.setCheckout(checkout);
        orderProductRepository.save(orderProduct);
        return ObjectMapperUtils.map(checkout, CheckoutDto.class);
    }

    @Override
    public void verifyCheckoutIsEmpty(Checkout checkout) {
        if (checkout.getOrderProducts().isEmpty()) {
            deleteCheckoutById(checkout.getId());
        }
    }

    @Override
    public CheckoutDto deleteCheckoutById(Integer id) {
        Optional<Checkout> checkout = checkoutRepository.findById(id);
        if (checkout.isPresent()) {
            CheckoutDto checkoutDto = ObjectMapperUtils.map(checkout, CheckoutDto.class);
            checkoutRepository.deleteById(id);
            return checkoutDto;
        }
        throw new ResourceNotFoundException("Checkout not found with the given id");
    }



    @Override
    public CheckoutDto selectAddressUser(Integer addressId, Integer checkoutId) {
        Optional<AddressUser> addressUser = addressRepository.findById(addressId);
        Checkout checkout = checkoutRepository.getReferenceById(checkoutId);

        if(addressUser == null || checkout == null) {
            throw new ResourceNotFoundException("Resources not found with the given ids");
        }
        if(!checkout.getUser().getUserAddresses().contains(addressUser.get())) {
            throw new ResourceNotFoundException("Address not found for the given user");
        }
        addressUser.get().setEnabled(true);
        checkout.setAddressUser(addressUser.get());
        addressRepository.save(addressUser.get());
        CheckoutDto checkoutDto = ObjectMapperUtils.map(checkoutRepository.save(checkout), CheckoutDto.class);
        return checkoutDto;
    }

    @Override
    public CheckoutDto selectPaymentMethod(Integer paymentMethodId, Integer checkoutId) {
        Optional<PaymentMethod> paymentMethod = paymentRepository.findById(paymentMethodId);
        Checkout checkout = checkoutRepository.getReferenceById(checkoutId);
        if(paymentMethod == null || checkout == null) {
            throw new ResourceNotFoundException("Resources not found with the given ids");
        }
        if(!checkout.getUser().getPaymentMethods().contains(paymentMethod.get())) {
            throw new ResourceNotFoundException("Payment Method not found for the given user");
        }
        paymentMethod.get().setEnabled(true);
        checkout.setPaymentMethod(paymentMethod.get());
        paymentRepository.save(paymentMethod.get());
        CheckoutDto checkoutDto = ObjectMapperUtils.map(checkoutRepository.save(checkout), CheckoutDto.class);
        return checkoutDto;
    }

    @Override
    public CheckoutDto changeAddressUser(Integer addressId, Integer checkoutId) {
        Optional<AddressUser> newAddressUser = addressRepository.findById(addressId);
        Checkout checkout = checkoutRepository.getReferenceById(checkoutId);
        if(newAddressUser == null || checkout == null || checkout.getAddressUser() == null) {
            throw new ResourceNotFoundException("Resources not found");
        }
        if(!checkout.getUser().getUserAddresses().contains(newAddressUser.get())) {
            throw new ResourceNotFoundException("Address not found for the given user");
        }
        if(checkout.getAddressUser().equals(newAddressUser.get())) {
            throw new ResourceNotFoundException("Address was already selected");
        }
        checkout.getAddressUser().setEnabled(false);
        addressRepository.save(checkout.getAddressUser());
        newAddressUser.get().setEnabled(true);
        addressRepository.save(newAddressUser.get());
        checkout.setAddressUser(newAddressUser.get());
        CheckoutDto checkoutDto = ObjectMapperUtils.map(checkoutRepository.save(checkout), CheckoutDto.class);
        return checkoutDto;
    }

    @Override
    public CheckoutDto changePaymentMethod(Integer paymentMethodId, Integer checkoutId) {
        Optional<PaymentMethod> paymentMethod = paymentRepository.findById(paymentMethodId);
        Checkout checkout = checkoutRepository.getReferenceById(checkoutId);
        if(paymentMethod == null || checkout == null || checkout.getPaymentMethod() == null) {
            throw new ResourceNotFoundException("Resources not found");
        }
        if(!checkout.getUser().getPaymentMethods().contains(paymentMethod.get())) {
            throw new ResourceNotFoundException("Payment Method not found for the given user");
        }
        if(checkout.getPaymentMethod().equals(paymentMethod.get())) {
            throw new ResourceNotFoundException("Payment Method was already selected");
        }
        checkout.getPaymentMethod().setEnabled(false);
        paymentRepository.save(checkout.getPaymentMethod());
        paymentMethod.get().setEnabled(true);
        paymentRepository.save(paymentMethod.get());
        checkout.setPaymentMethod(paymentMethod.get());
        CheckoutDto checkoutDto = ObjectMapperUtils.map(checkoutRepository.save(checkout), CheckoutDto.class);
        return checkoutDto;
    }


}
