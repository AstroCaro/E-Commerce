package com.applaudo.csilva.repository;

import com.applaudo.csilva.model.Checkout;
import com.applaudo.csilva.model.OrderProduct;
import com.applaudo.csilva.model.Product;
import com.applaudo.csilva.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@DataJpaTest
@DisplayName("When running Order Product Repository")
class OrderProductRepositoryTest {

    @Autowired
    private OrderProductRepository orderProductRepository;

    @Autowired
    private TestEntityManager entityManager;

    private Product product;
    private OrderProduct orderProduct;
    private Checkout checkout;
    private User user;

    @BeforeEach
    void setUp() {
        this.product = Product.builder()
                .name("Jean")
                .stock(5)
                .price(4.)
                .build();

        this.user = User.builder()
                .email("juanperez@gmail.com")
                .firstName("juan")
                .lastName("perez")
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

        orderProduct.setCheckout(checkout);

    }

    @Test
    void should_find_by_id_then_return_OrderProduct() {
        entityManager.persist(user);
        entityManager.persist(product);
        entityManager.persist(orderProduct);
        entityManager.persist(checkout);
        entityManager.flush();

        Optional<OrderProduct> found = orderProductRepository.findById(orderProduct.getId());
        assertThat(found.get().getProduct(), is(orderProduct.getProduct()));
        assertThat(found.get().getQuantity(), is(orderProduct.getQuantity()));
    }

    @Test
    void should_find_all_OrderProducts() {
        orderProduct.setCheckout(checkout);

        entityManager.persist(user);
        entityManager.persist(product);
        entityManager.persist(orderProduct);
        entityManager.persist(checkout);

        entityManager.flush();

        List<OrderProduct> orderProducts = orderProductRepository.findAll();
        assertThat(orderProducts, hasSize(1));
    }

    @Test
    void should_find_by_checkout_and_product_return_OrderProduct() {

        entityManager.persist(user);
        entityManager.persist(product);
        entityManager.persist(orderProduct);
        entityManager.persist(checkout);
        entityManager.flush();


        OrderProduct found = orderProductRepository.findByCheckoutAndProduct(checkout,product);
        assertThat(found.getCheckout(), is(equalTo(orderProduct.getCheckout())));
        assertThat(found.getProduct(), is(equalTo(orderProduct.getProduct())));
        assertThat(found.getQuantity(), is(equalTo(orderProduct.getQuantity())));

    }

    @Test
    void findByCheckoutAndProduct() {
    }

    @Test
    void findAllByCheckout_Id() {
    }
}