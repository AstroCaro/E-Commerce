package com.applaudo.csilva.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "checkouts")
public class Checkout {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

//    private Double totalPrice;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dateCreated;

    @OneToOne
    @JoinColumn(name = "address_user_id")
    private AddressUser addressUser;

    @OneToOne
    @JoinColumn(name = "payment_method_id")
    private PaymentMethod paymentMethod;

    @NotNull
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "checkout", orphanRemoval = true)
    private List<OrderProduct> orderProducts = new ArrayList<>();

    @NotNull
    @JsonIgnore
    @ManyToOne(fetch=FetchType.LAZY, cascade={ CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "user_id")
    private User user;

    public Checkout(List<OrderProduct> orderProducts, User user) {
        this.orderProducts = orderProducts;
        this.user = user;
    }

    public double getTotalOrderPrice() {
        double totalPrice = 0.;
        for(OrderProduct orderProduct : orderProducts) {
            totalPrice += orderProduct.getPrice();
        }
        return totalPrice;
    }
}
