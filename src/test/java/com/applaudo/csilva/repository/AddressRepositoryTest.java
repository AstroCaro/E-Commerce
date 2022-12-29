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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@DataJpaTest
@DisplayName("When running Address Repository")
class AddressRepositoryTest {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private TestEntityManager entityManager;

    private AddressUser addressOne;
    private AddressUser addressTwo;
    private User user;


    @BeforeEach
    void setUp() {
        this.addressOne = AddressUser.builder()
                .address("Malvinas")
                .city("Neuquen")
                .enabled(false)
                .build();
        this.addressTwo = AddressUser.builder()
                .address("Santa Teresa")
                .city("Mainque")
                .enabled(false)
                .build();
        this.user = User.builder()
                .email("juanperez@gmail.com")
                .firstName("juan")
                .lastName("perez")
                .build();
    }

    @Test
    void should_find_by_id_then_return_Address_User() {
        entityManager.persist(addressOne);
        AddressUser found = addressRepository.getReferenceById(addressOne.getId());
        assertThat(found.getAddress(), is(addressOne.getAddress()));
        assertThat(found.getCity(), is(addressOne.getCity()));
    }

    @Test
    void should_find_all_Address_User() {
        entityManager.persist(addressOne);
        entityManager.flush();

        List<AddressUser> addresses = addressRepository.findAll();
        assertThat(addresses, hasSize(1));
    }

    @Test
    public void should_find_all_by_User_Id_return_AddressUserList() {
        addressOne.setUser(user);
        addressTwo.setUser(user);

        entityManager.persist(addressOne);
        entityManager.persist(addressTwo);

        entityManager.flush();

        List<AddressUser> addresses = addressRepository.findAllByUser_Id(user.getId());
        assertThat(addresses, hasSize(2));
    }

    @Test
    public void should_save_Address_User() {
        addressRepository.save(addressOne);
        assertThat(addressOne.getId(), is(notNullValue()));
    }

}
