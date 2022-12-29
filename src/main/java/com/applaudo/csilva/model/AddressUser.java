package com.applaudo.csilva.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "addresses")
public class AddressUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Address is mandatory")
    @Column(name = "address")
    private String address;

    @NotBlank(message = "City is mandatory")
    @Column(name = "city")
    private String city;

    @NotNull
    @Column(name = "enabled")
    private boolean enabled;

    @JsonIgnore
    @ManyToOne(fetch=FetchType.LAZY, cascade={ CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "user_id")
    private User user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AddressUser)) return false;

        AddressUser that = (AddressUser) o;

        if (enabled != that.enabled) return false;
        if (!address.equals(that.address)) return false;
        if (!city.equals(that.city)) return false;
        return user.equals(that.user);
    }

    @Override
    public int hashCode() {
        int result = address.hashCode();
        result = 31 * result + city.hashCode();
        result = 31 * result + (enabled ? 1 : 0);
        result = 31 * result + user.hashCode();
        return result;
    }
}

