package com.applaudo.csilva.service.impl;

import com.applaudo.csilva.dto.AddressDto;
import com.applaudo.csilva.dto.PaymentMethodDto;
import com.applaudo.csilva.dto.UserDto;
import com.applaudo.csilva.exception.ResourceFoundException;
import com.applaudo.csilva.exception.ResourceNotFoundException;
import com.applaudo.csilva.model.AddressUser;
import com.applaudo.csilva.model.User;
import com.applaudo.csilva.repository.UserRepository;
import com.applaudo.csilva.service.AddressService;
import com.applaudo.csilva.service.PaymentService;
import com.applaudo.csilva.service.UserService;
import com.applaudo.csilva.utils.ObjectMapperUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final AddressService addressService;

    private final PaymentService paymentService;

    @Override
    public UserDto getUserById(Integer id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return ObjectMapperUtils.map(user.get(), UserDto.class);
        }
        throw new ResourceNotFoundException("User not found with the given id");
    }

    @Override
    public UserDto getUserByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            return ObjectMapperUtils.map(user.get(), UserDto.class);
        }
        throw new ResourceNotFoundException("User not found with the given email");
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return ObjectMapperUtils.mapAll(users, UserDto.class);
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        Optional<User> userFound = userRepository.findByEmail(userDto.getEmail());
        if (!userFound.isPresent()) {
            User user = ObjectMapperUtils.map(userDto, User.class);
            List<AddressUser> addresses = user.getUserAddresses();
            if (addresses.size()>0) {
                addresses.stream().forEach(addressItem -> {
                    addressItem.setUser(user);
                });
            }
            User userCreated = userRepository.save(user);
            return ObjectMapperUtils.map(userCreated, UserDto.class);
        }
        throw new ResourceFoundException("User already exits with the given email");
    }

    @Override
    public UserDto updateUser(UserDto userDto, Integer id) {
        Optional<User> userFound = userRepository.findById(id);
        if (!userFound.isPresent()) {
            throw new ResourceNotFoundException("User not found with the given id");
        }
        if (userRepository.findByEmail(userDto.getEmail()).isPresent()) {
            throw new ResourceFoundException("User already exits with this email");
        }
        userFound.get().setFirstName(userDto.getFirstName());
        userFound.get().setLastName(userDto.getLastName());
        return ObjectMapperUtils.map(userRepository.save(userFound.get()), UserDto.class);
    }

    @Override
    public UserDto deleteUser(Integer id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            UserDto userDto = ObjectMapperUtils.map(user, UserDto.class);
            userRepository.deleteById(id);
            return userDto;
        }
        throw new ResourceNotFoundException("User not found with the given id");
    }

    @Override
    public List<AddressDto> getAddresses(Integer id) {
        return addressService.getAddressesByUser(id);
    }

    @Override
    public List<PaymentMethodDto> getPaymentMethods(Integer id) {
        return paymentService.getPaymentMethodsByUser(id);
    }

    @Override
    public AddressDto createAddressUser(AddressDto addressDto, Integer id) {
        return addressService.createAddressUser(addressDto, id);
    }

    @Override
    public PaymentMethodDto createPaymentMethodUser(PaymentMethodDto paymentMethodDto, Integer id) {
        return paymentService.createPaymentMethodUser(paymentMethodDto, id);
    }


}
