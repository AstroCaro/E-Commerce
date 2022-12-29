package com.applaudo.csilva.service.impl;

import com.applaudo.csilva.dto.AddressDto;
import com.applaudo.csilva.dto.UserDto;
import com.applaudo.csilva.model.AddressUser;
import com.applaudo.csilva.model.User;
import com.applaudo.csilva.repository.AddressRepository;
import com.applaudo.csilva.repository.UserRepository;
import com.applaudo.csilva.utils.ObjectMapperUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatNoException;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("When running AddressServiceImpl")
class AddressServiceImplTest {

    AddressUser addressUser;
    AddressDto addressDto;
    User user;
    UserDto userDto;


    @Mock
    private AddressRepository addressRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AddressServiceImpl addressServiceImpl;

    @BeforeEach
    void setUp() {
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
    }

    @Test
    void shouldGetAddressById_Successfully() {
        when(addressRepository.findById(anyInt())).thenReturn(Optional.of(addressUser));
        AddressDto actualAddressDto = addressServiceImpl.getAddressById(1);
        assertThat(actualAddressDto, is(equalTo(addressDto)));
        verify(addressRepository).findById(anyInt());
    }

    @Test
    void shouldGetAllAddresses_Successfully() {
        List<AddressUser> addresses = new ArrayList<>();
        addresses.add(addressUser);

        when(addressRepository.findAll()).thenReturn(addresses);
        List<AddressDto> addressesActual = addressServiceImpl.getAllAddress();
        assertThat(addressesActual, is(equalTo(ObjectMapperUtils.mapAll(addresses, AddressDto.class))));
        verify(addressRepository).findAll();
    }

    @Test
    void shouldGetAddressesByUser_Successfully() {
        List<AddressUser> addresses = new ArrayList<>();
        addresses.add(addressUser);
        when(addressRepository.findAllByUser_Id(anyInt())).thenReturn(addresses);
        List<AddressDto> addressesActual = addressServiceImpl.getAddressesByUser(1);
        assertThat(addressesActual, is(equalTo(ObjectMapperUtils.mapAll(addresses, AddressDto.class))));
        verify(addressRepository).findAllByUser_Id(anyInt());
    }

    //TODO
    @Test
    void getEnabledAddressByUser() {
    }

    @Test
    @Disabled
    void shouldCreateAddressUser_Successfully() {
        List<AddressUser> addresses = new ArrayList<>();
        addresses.add(addressUser);
        final User user = spy(User.builder()
                .email("juanperez@gmail.com")
                .firstName("juan")
                .lastName("perez")
                .userAddresses(addresses)
                .build());

        when(userRepository.getReferenceById(1)).thenReturn(user);
        when(addressRepository.save(any(AddressUser.class))).thenReturn(addressUser);
        assertThatNoException().isThrownBy(() -> doReturn(false).when(user.getUserAddresses()).contains(any(AddressUser.class)));
        System.out.println(user);

        AddressDto addressCreated = addressServiceImpl.createAddressUser(addressDto, 1);
        assertThat(addressCreated, is(equalTo(addressDto)));
    }

    @Test
    void deleteAddressById() {
        when(addressRepository.findById(anyInt())).thenReturn(Optional.ofNullable(addressUser));
        doNothing().when(addressRepository).deleteById(anyInt());

        AddressDto addressDeleted = addressServiceImpl.deleteAddressById(1);
        assertThat(addressDto, is(equalTo(addressDeleted)));
    }
}