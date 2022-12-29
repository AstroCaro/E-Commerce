package com.applaudo.csilva.repository;

import com.applaudo.csilva.model.AddressUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<AddressUser, Integer> {

    List<AddressUser> findAllByUser_Id(Integer user);

}
