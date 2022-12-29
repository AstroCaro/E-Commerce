package com.applaudo.csilva.service.impl;

import com.applaudo.csilva.dto.AddressDto;
import com.applaudo.csilva.dto.CheckoutDto;
import com.applaudo.csilva.dto.OrderProductDto;
import com.applaudo.csilva.dto.UserDto;
import com.applaudo.csilva.model.*;
import com.applaudo.csilva.repository.*;
import com.applaudo.csilva.service.AddressService;
import com.applaudo.csilva.service.ProductService;
import com.applaudo.csilva.service.UserService;
import com.applaudo.csilva.utils.ObjectMapperUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("When running AddressServiceImpl")
class CheckoutServiceImplTest {

    @Mock
    private CheckoutRepository checkoutRepository;
    @Mock
    private UserService userService;
    @Mock
    private ProductService productService;
    @Mock
    private AddressService addressService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private AddressRepository addressRepository;
    @Mock
    private PaymentRepository paymentRepository;
    @Mock
    private OrderProductRepository orderProductRepository;

    private CheckoutServiceImpl checkoutServiceImpl;


    private AddressUser addressUser;
    private AddressDto addressDto;
    private User user;
    private UserDto userDto;
    private Checkout checkout;
    private CheckoutDto checkoutDto;
    private Product product;
    private OrderProduct orderProduct;
    private PaymentMethod paymentMethod;


    @BeforeEach
    void setUp() {
        checkoutServiceImpl = new CheckoutServiceImpl(checkoutRepository, userRepository, productRepository, orderProductRepository, addressRepository, paymentRepository);

        this.addressUser = AddressUser.builder()
                .address("Malvinas")
                .city("Neuquen")
                .enabled(false)
                .build();
        this.addressDto = AddressDto.builder()
                .address("Malvinas")
                .city("Neuquen")
                .enabled(false)
                .build();
        this.user = User.builder()
                .email("juanperez@gmail.com")
                .firstName("juan")
                .lastName("perez")
                .build();
        this.userDto = UserDto.builder()
                .email("juanperez@gmail.com")
                .firstName("juan")
                .lastName("perez")
                .build();

        this.paymentMethod = PaymentMethod.builder()
                .name("Paypal")
                .enabled(false)
                .build();

        this.product = Product.builder()
                .name("Jean")
                .stock(5)
                .price(4.)
                .build();

        this.orderProduct = OrderProduct.builder()
                .product(product)
                .quantity(5)
                .build();

        List<OrderProduct> orderProducts = new ArrayList<>();
        orderProducts.add(orderProduct);

        this.checkout = Checkout.builder()
                .paymentMethod(paymentMethod)
                .addressUser(addressUser)
                .orderProducts(orderProducts)
                .user(user)
                .build();

        List<OrderProductDto> orderProductDtos = ObjectMapperUtils.mapAll(orderProducts, OrderProductDto.class);
        this.checkoutDto = CheckoutDto.builder()
                .orderProducts(orderProductDtos)
                .userId(1).build();

        orderProduct.setCheckout(checkout);

    }
    
    @Test
    void getCheckoutById() {
        Mockito.mockStatic(ObjectMapperUtils.class).when(() -> ObjectMapperUtils.map(checkout, CheckoutDto.class)).thenReturn(checkoutDto);
        when(checkoutRepository.findById(anyInt())).thenReturn(Optional.of(checkout));
        CheckoutDto actualCheckoutDto = checkoutServiceImpl.getCheckoutById(1);
        assertThat(actualCheckoutDto, is(equalTo(checkoutDto)));
        verify(checkoutRepository).findById(anyInt());
    }

    @Test
    void getAllCheckouts() {
        List<Checkout> checkouts = new ArrayList<>();
        checkouts.add(checkout);
        List<CheckoutDto> checkoutDtos = new ArrayList<>();
        checkoutDtos.add(checkoutDto);
        Mockito.mockStatic(ObjectMapperUtils.class).when(() -> ObjectMapperUtils.mapAll(checkouts, CheckoutDto.class)).thenReturn(checkoutDtos);
        when(checkoutRepository.findAll()).thenReturn(checkouts);
        List<CheckoutDto> actualCheckoutDtos = checkoutServiceImpl.getAllCheckouts();
        assertThat(actualCheckoutDtos, is(equalTo(checkoutDtos)));
        verify(checkoutRepository).findAll();
    }

    @Test
    void createCheckout() {

    }

    @Test
    void createOrderProduct() {
    }


    @Test
    void deleteCheckoutById() {
        when(checkoutRepository.findById(anyInt())).thenReturn(Optional.ofNullable(checkout));
        Mockito.mockStatic(ObjectMapperUtils.class).when(() -> ObjectMapperUtils.map(checkout, CheckoutDto.class)).thenReturn(checkoutDto);
        doNothing().when(checkoutRepository).deleteById(anyInt());
        CheckoutDto checkoutDtoDeleted = checkoutServiceImpl.deleteCheckoutById(1);
        assertThat(checkoutDto, is(equalTo(checkoutDtoDeleted)));
    }


}