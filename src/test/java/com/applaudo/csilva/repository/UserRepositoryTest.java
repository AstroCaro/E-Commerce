package com.applaudo.csilva.repository;

import com.applaudo.csilva.model.AddressUser;
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
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
@DisplayName("When running User Repository")
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestEntityManager entityManager;

    private User user;

    @BeforeEach
    void setUp() {
        this.user = User.builder()
                .email("juanperez@gmail.com")
                .firstName("juan")
                .lastName("perez")
                .build();
    }

    @Test
    void should_find_by_id_then_return_User() {
        entityManager.persist(user);
        entityManager.flush();

        Optional<User> found = userRepository.findById(user.getId());
        assertThat(found.get().getEmail(), is(user.getEmail()));
        assertThat(found.get().getFirstName(), is(user.getFirstName()));
        assertThat(found.get().getLastName(), is(user.getLastName()));
    }

    @Test
    void should_find_all_User() {
        entityManager.persist(user);
        entityManager.flush();

        List<User> addresses = userRepository.findAll();
        assertThat(addresses, hasSize(1));
    }

    @Test
    void should_find_by_email_then_return_User() {
        entityManager.persist(user);
        entityManager.flush();

        Optional<User> found = userRepository.findByEmail(user.getEmail());
        assertThat(found.get().getEmail(), is(equalTo(user.getEmail())));
    }



}