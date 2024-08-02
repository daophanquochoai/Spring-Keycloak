package com.example.keycloak_spring_unit1.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MenuItem {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;
    private Long menuId;
    private String name;
    private String description;
    @Column( name = "type_name")
    private String type;
    @Column( name = "group_name")
    private String group;
    private BigDecimal price;
}
