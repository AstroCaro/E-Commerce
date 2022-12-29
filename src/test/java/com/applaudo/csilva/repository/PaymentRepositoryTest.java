package com.applaudo.csilva.repository;

import com.applaudo.csilva.model.PaymentMethod;
import com.applaudo.csilva.model.Product;
import com.applaudo.csilva.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@DisplayName("When running Payment Repository")
class PaymentRepositoryTest {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private TestEntityManager entityManager;

    private PaymentMethod paymentMethod;
    private User user;

    @BeforeEach
    void setUp() {
        this.paymentMethod = PaymentMethod.builder()
                .name("Paypal")
                .enabled(false)
                .build();

        this.user = User.builder()
                .email("juanperez@gmail.com")
                .firstName("juan")
                .lastName("perez")
                .build();

        paymentMethod.setUser(user);

    }

    @Test
    void should_find_by_id_then_return_PaymentMethod() {
        entityManager.persist(paymentMethod);
        entityManager.flush();

        Optional<PaymentMethod> found = paymentRepository.findById(paymentMethod.getId());
        assertThat(found.get().getName(), is(paymentMethod.getName()));
        assertThat(found.get().isEnabled(), is(paymentMethod.isEnabled()));
    }

    @Test
    void should_find_all_PaymentMethod() {
        entityManager.persist(paymentMethod);
        entityManager.flush();

        List<PaymentMethod> paymentMethods = paymentRepository.findAll();
        assertThat(paymentMethods, hasSize(1));
    }

    @Test
    void should_find_by_User_Id_then_return_PaymentMethod() {
        entityManager.persist(paymentMethod);
        entityManager.flush();

        List<PaymentMethod> paymentMethods = paymentRepository.findAllByUser_Id(paymentMethod.getUser().getId());
        assertThat(paymentMethods, hasSize(1));
    }
}