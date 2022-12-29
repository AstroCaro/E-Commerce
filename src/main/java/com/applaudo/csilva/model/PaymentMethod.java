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
public class PaymentMethod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Payment method's name is mandatory")
    @Column(name = "name")
    private String name;

    @NotNull(message = "Enable field is mandatory")
    @Column(name = "enabled")
    private boolean enabled = false;

    @JsonIgnore
    @ManyToOne(fetch=FetchType.LAZY, cascade={ CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "user_id")
    private User user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PaymentMethod)) return false;

        PaymentMethod that = (PaymentMethod) o;

        if (enabled != that.enabled) return false;
        if (!name.equals(that.name)) return false;
        return user.getId().equals(that.user.getId());
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + (enabled ? 1 : 0);
        result = 31 * result + user.getId().hashCode();
        return result;
    }

//    public boolean pay(int paymentAmount){
//
//    }

}
