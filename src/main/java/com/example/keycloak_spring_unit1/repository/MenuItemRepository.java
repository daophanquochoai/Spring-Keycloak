package com.example.keycloak_spring_unit1.repository;

import com.example.keycloak_spring_unit1.entity.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
    List<MenuItem> findAllByMenuId( Long id );
}
