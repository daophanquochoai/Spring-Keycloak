package com.example.keycloak_spring_unit1.controller;

import com.example.keycloak_spring_unit1.entity.Menu;
import com.example.keycloak_spring_unit1.entity.MenuItem;
import com.example.keycloak_spring_unit1.entity.Restaurant;
import com.example.keycloak_spring_unit1.repository.MenuItemRepository;
import com.example.keycloak_spring_unit1.repository.MenuRepository;
import com.example.keycloak_spring_unit1.repository.RestaurantRepostory;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/restaurant")
@SecurityRequirement(name = "Keycloak")
@RequiredArgsConstructor
public class RestaurantController {
    private final RestaurantRepostory restaurantRepostory;
    private final MenuRepository menuRepository;
    private final MenuItemRepository menuItemRepository;
    @GetMapping("/public/list")
    //Public API
    public List<Restaurant> getRestaurants() {
        return restaurantRepostory.findAll();
    }

    @GetMapping("/public/menu/{restaurantId}")
    //Public API
    public Menu getMenu(@PathVariable Long restaurantId) {
        Menu menu = menuRepository.findByRestaurantId(restaurantId);
        menu.setMenuItems(menuItemRepository.findAllByMenuId(menu.getId()));
        return menu;
    }

    @PostMapping
    // admin can access (admin)
    public Restaurant createRestaurant(Restaurant restaurant) {
        return restaurantRepostory.save(restaurant);
    }

    @PostMapping("/menu")
    // manager can access (suresh)
    public Menu createMenu(Menu menu) {
        menuRepository.save(menu);
        menu.getMenuItems().forEach(menuItem -> {
            menuItem.setMenuId(menu.getId());
            menuItemRepository.save(menuItem);
        });
        return menu;
    }

    @PutMapping("/menu/item/{itemId}/{price}")
    // owner can access (amar)
    public MenuItem updateMenuItemPrice(@PathVariable Long itemId
            , @PathVariable BigDecimal price) {
        Optional<MenuItem> menuItem = menuItemRepository.findById(itemId);
        menuItem.get().setPrice(price);
        menuItemRepository.save(menuItem.get());
        return menuItem.get();
    }
}
