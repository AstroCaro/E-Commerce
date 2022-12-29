package com.applaudo.csilva.repository;

import com.applaudo.csilva.model.Checkout;
import com.applaudo.csilva.model.OrderProduct;
import com.applaudo.csilva.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderProductRepository extends JpaRepository<OrderProduct, Integer> {

    OrderProduct findByCheckoutAndProduct(Checkout checkout, Product product);

    List<OrderProduct> findAllByCheckout_Id(Integer id);
}
