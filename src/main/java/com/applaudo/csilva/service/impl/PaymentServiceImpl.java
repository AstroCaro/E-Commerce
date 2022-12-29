package com.applaudo.csilva.service.impl;

import com.applaudo.csilva.dto.PaymentMethodDto;
import com.applaudo.csilva.exception.ResourceFoundException;
import com.applaudo.csilva.exception.ResourceNotFoundException;
import com.applaudo.csilva.model.PaymentMethod;
import com.applaudo.csilva.model.User;
import com.applaudo.csilva.repository.PaymentRepository;
import com.applaudo.csilva.repository.UserRepository;
import com.applaudo.csilva.service.PaymentService;
import com.applaudo.csilva.utils.ObjectMapperUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final UserRepository userRepository;

    @Override
    public PaymentMethodDto getPaymentMethodById(Integer id) {
        Optional<PaymentMethod> paymentMethod = paymentRepository.findById(id);
        if(paymentMethod.isPresent()) {
            return ObjectMapperUtils.map(paymentMethod.get(), PaymentMethodDto.class);
        }
        throw new ResourceNotFoundException("Payment Method not found with the given id");
    }

    @Override
    public List<PaymentMethodDto> getAllPaymentMethods() {
        List<PaymentMethod> paymentMethods = paymentRepository.findAll();
        return ObjectMapperUtils.mapAll(paymentMethods, PaymentMethodDto.class);
    }

    @Override
    public List<PaymentMethodDto> getPaymentMethodsByUser(Integer userId) {
        List<PaymentMethod> paymentMethodUserList = paymentRepository.findAllByUser_Id(userId);
        return ObjectMapperUtils.mapAll(paymentMethodUserList, PaymentMethodDto.class);
    }

//    @Override
//    public PaymentMethodDto createPaymentMethod(PaymentMethodDto paymentMethodDto) {
//        Optional<PaymentMethod> paymentMethodFound = paymentRepository.findByName(paymentMethodDto.getName());
//        if (!paymentMethodFound.isPresent()) {
//            PaymentMethod paymentMethodCreated = paymentRepository.save(ObjectMapperUtils.map(paymentMethodDto, PaymentMethod.class));
//            return ObjectMapperUtils.map(paymentMethodCreated, PaymentMethodDto.class);
//        }
//        throw new ResourceFoundException("Payment Method already exist");
//    }

    @Override
    public PaymentMethodDto createPaymentMethodUser(PaymentMethodDto paymentMethodDto, Integer id) {
        User user = userRepository.getReferenceById(id);
        PaymentMethod paymentMethod = ObjectMapperUtils.map(paymentMethodDto, PaymentMethod.class);
        if (user == null) {
            throw new ResourceNotFoundException("User not found with the given id");
        }
        paymentMethod.setUser(user);
        if (user.getPaymentMethods().contains(paymentMethod)) {
            throw new ResourceFoundException("Payment method already exits for the user");
        }
        PaymentMethod paymentMethodCreated = paymentRepository.save(paymentMethod);
        user.addPaymentMethod(paymentMethodCreated);

        return ObjectMapperUtils.map(paymentMethodCreated, PaymentMethodDto.class);
    }

    @Override
    public PaymentMethodDto deletePaymentMethodId(Integer id) {
        Optional<PaymentMethod> paymentMethod = paymentRepository.findById(id);
        if(paymentMethod.isPresent()) {
            PaymentMethodDto paymentMethodDto = ObjectMapperUtils.map(paymentMethod, PaymentMethodDto.class);
            paymentRepository.deleteById(id);
            return paymentMethodDto;
        }
        throw new ResourceNotFoundException("Payment Method not found with the given id");
    }
}
