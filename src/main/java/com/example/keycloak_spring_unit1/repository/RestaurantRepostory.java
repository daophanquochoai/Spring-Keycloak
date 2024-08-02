package com.example.keycloak_spring_unit1.repository;

import com.example.keycloak_spring_unit1.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantRepostory extends JpaRepository<Restaurant, Long> {
}
