package com.applaudo.csilva.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Email(message = "Invalid email address format")
    @NotBlank(message = "Email field must not be empty")
    @Column(name = "email", unique = true)
    private String email;

    @NotBlank(message = "First name is mandatory")
    @Column(name = "first_name")
    private String firstName;

    @NotBlank(message = "Last name is mandatory")
    @Column(name = "last_name")
    private String lastName;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", orphanRemoval = true)
    private List<AddressUser> userAddresses = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", orphanRemoval = true)
    private List<PaymentMethod> paymentMethods = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", orphanRemoval = true)
    private List<Checkout> checkouts;

    public User(String email, String firstName, String lastName, List<AddressUser> userAddresses) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userAddresses = userAddresses;
    }

    public void addAddress(AddressUser addressUser) {
        this.userAddresses.add(addressUser);
        addressUser.setUser(this);
    }

    public void addPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethods.add(paymentMethod);
        paymentMethod.setUser(this);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", userAddresses=" + userAddresses +
                ", paymentMethodList=" + paymentMethods +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        User user = (User) o;

        if (email != null ? !email.equals(user.email) : user.email != null) return false;
        if (firstName != null ? !firstName.equals(user.firstName) : user.firstName != null) return false;
        if (lastName != null ? !lastName.equals(user.lastName) : user.lastName != null) return false;
        if (userAddresses != null ? !userAddresses.equals(user.userAddresses) : user.userAddresses != null)
            return false;
        return paymentMethods != null ? paymentMethods.equals(user.paymentMethods) : user.paymentMethods == null;
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();

    }
}
