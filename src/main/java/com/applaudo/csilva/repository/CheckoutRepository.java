package com.applaudo.csilva.repository;

import com.applaudo.csilva.model.Checkout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CheckoutRepository extends JpaRepository<Checkout, Integer> {

}
