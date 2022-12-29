package com.applaudo.csilva.service.impl;

import com.applaudo.csilva.dto.AddressDto;
import com.applaudo.csilva.exception.ResourceFoundException;
import com.applaudo.csilva.exception.ResourceNotFoundException;
import com.applaudo.csilva.model.AddressUser;
import com.applaudo.csilva.model.User;
import com.applaudo.csilva.repository.AddressRepository;
import com.applaudo.csilva.repository.UserRepository;
import com.applaudo.csilva.service.AddressService;
import com.applaudo.csilva.utils.ObjectMapperUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    public AddressServiceImpl(AddressRepository addressRepository, UserRepository userRepository) {
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
    }

    @Override
    public AddressDto getAddressById(Integer id) {
        Optional<AddressUser> address = addressRepository.findById(id);
        if(address.isPresent()) {
            return ObjectMapperUtils.map(address.get(), AddressDto.class);
        }
        throw new ResourceNotFoundException("Address not found with the given id");
    }

    @Override
    public List<AddressDto> getAllAddress() {
        List<AddressUser> addresses = addressRepository.findAll();
        return ObjectMapperUtils.mapAll(addresses, AddressDto.class);
    }

    @Override
    public List<AddressDto> getAddressesByUser(Integer userId) {
        List<AddressUser> addressUserList = addressRepository.findAllByUser_Id(userId);
        return ObjectMapperUtils.mapAll(addressUserList, AddressDto.class);
    }

    @Override
    public AddressDto getEnabledAddressByUser(Integer userId) {
        List<AddressUser> addressUserList = addressRepository.findAllByUser_Id(userId);
        if (!addressUserList.isEmpty()) {
            Optional<AddressUser> address = addressUserList.stream().
                    filter(AddressUser::isEnabled).findFirst();
            return ObjectMapperUtils.map(address, AddressDto.class);
        }
        return null;
    }

    @Override
    public AddressDto createAddressUser(AddressDto addressDto, Integer id) {
        User user = userRepository.getReferenceById(id);
        AddressUser addressUser = ObjectMapperUtils.map(addressDto, AddressUser.class);

        if (user == null) {
            throw new ResourceNotFoundException("User not found with the given id");
        }
        addressUser.setUser(user);
        if (user.getUserAddresses().contains(addressUser)) {
            throw new ResourceFoundException("Address already exits for the user");
        }

        AddressUser addressCreated = addressRepository.save(addressUser);
        user.addAddress(addressCreated);

        return ObjectMapperUtils.map(addressCreated, AddressDto.class);
    }

    @Override
    public AddressDto deleteAddressById(Integer id) {
        Optional<AddressUser> address = addressRepository.findById(id);
        if (address.isPresent()) {
            AddressDto addressDto = ObjectMapperUtils.map(address, AddressDto.class);
            addressRepository.deleteById(id);
            return addressDto;
        }
        throw new ResourceNotFoundException("Address not found with the given id");
    }

}
