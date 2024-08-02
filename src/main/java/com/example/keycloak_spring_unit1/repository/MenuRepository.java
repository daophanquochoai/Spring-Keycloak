package com.example.keycloak_spring_unit1.repository;

import com.example.keycloak_spring_unit1.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {
    Menu findByRestaurantId( Long restaurantId );
}
