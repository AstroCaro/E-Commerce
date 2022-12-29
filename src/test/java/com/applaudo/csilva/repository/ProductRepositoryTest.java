package com.applaudo.csilva.repository;

import com.applaudo.csilva.model.Product;
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

@DataJpaTest
@DisplayName("When running Product Repository")
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private TestEntityManager entityManager;

    private Product product;

    @BeforeEach
    void setUp() {
        this.product = Product.builder()
                .name("Jean")
                .stock(5)
                .price(4.)
                .build();
    }

    @Test
    void should_find_by_id_then_return_Product() {
        entityManager.persist(product);
        entityManager.flush();

        Optional<Product> found = productRepository.findById(product.getId());
        assertThat(found.get().getName(), is(product.getName()));
    }

    @Test
    void should_find_all_Products() {
        entityManager.persist(product);
        entityManager.flush();

        List<Product> products = productRepository.findAll();
        assertThat(products, hasSize(1));
    }

    @Test
    void should_find_by_name_then_return_Product() {
        entityManager.persist(product);
        entityManager.flush();

        Optional<Product> found = productRepository.findByName(product.getName());
        assertThat(found.get().getName(), is(equalTo(product.getName())));
    }
}