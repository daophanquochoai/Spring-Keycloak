package com.example.keycloak_spring_unit1.repository;

import com.example.keycloak_spring_unit1.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByRestaurantId( Long restaurantId );
}
