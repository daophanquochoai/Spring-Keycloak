package com.example.keycloak_spring_unit1.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "`order`")
public class Order{

    @Id
    @GeneratedValue
    public Long id;

    private Long restaurantId;

    private BigDecimal total;

    @Transient
    private List<OrderItem> orderItems;
}
