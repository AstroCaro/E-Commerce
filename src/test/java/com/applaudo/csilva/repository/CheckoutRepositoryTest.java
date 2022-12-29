package com.applaudo.csilva.repository;

import com.applaudo.csilva.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

@DataJpaTest
@DisplayName("When running Checkout Repository")
class CheckoutRepositoryTest {

    @Autowired
    private CheckoutRepository checkoutRepository;

    @Autowired
    private TestEntityManager entityManager;

    private Checkout checkout;
    private User user;
    private OrderProduct orderProduct;
    private Product product;
    private AddressUser address;
    private PaymentMethod paymentMethod;


    @BeforeEach
    void setUp() {
        this.user = User.builder()
                .email("juanperez@gmail.com")
                .firstName("juan")
                .lastName("perez")
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
                .user(user)
                .orderProducts(orderProducts)
                .build();
    }

    @Test
    void should_find_by_id_test_then_return_Checkout() {
        entityManager.persist(checkout);

        entityManager.flush();

        Checkout found = checkoutRepository.getReferenceById(checkout.getId());
        assertThat(found.getDateCreated(), is(checkout.getDateCreated()));
        assertThat(found.getUser(), is(checkout.getUser()));
        assertThat(found.getOrderProducts(), is(checkout.getOrderProducts()));
    }

    @Test
    void should_find_all_Checkouts() {
        entityManager.persist(user);
        entityManager.persist(product);
        entityManager.persist(orderProduct);
        entityManager.persist(checkout);

        entityManager.flush();

        List<Checkout> checkouts = checkoutRepository.findAll();
        assertThat(checkouts, hasSize(1));
    }

}