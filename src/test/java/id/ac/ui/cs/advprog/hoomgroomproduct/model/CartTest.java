package id.ac.ui.cs.advprog.hoomgroomproduct.model;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class CartTest {
    Cart cart;
    Map<UUID, CartItem> cartItems;

    @BeforeEach
    void setUp() {
        CartItem cartItem = new CartItem(UUID.fromString("ca1c1b7d-f5aa-4573-aeff-d01665cc88c8"),
                "Meja", 25000, 1);

        this.cart = new Cart(UUID.fromString("4e900a31-6af6-472d-aa44-15e145ed6b8d"));
        this.cart.getItems().put(UUID.fromString("4e900a31-6af6-472d-aa44-15e145ed6b8d"), cartItem);
        this.cartItems = new HashMap<>();
        this.cartItems.put(UUID.fromString("4e900a31-6af6-472d-aa44-15e145ed6b8d"), cartItem);
    }

    @Test
    void getUserId() {
        assertEquals(UUID.fromString("4e900a31-6af6-472d-aa44-15e145ed6b8d"), cart.getUserId());
    }

    @Test
    void getItems() {
        Map<UUID, CartItem> savedItems = cart.getItems();
        assertEquals(cartItems, savedItems);
        assertEquals(cartItems.size(), savedItems.size());
        for (Map.Entry<UUID, CartItem> entry : cartItems.entrySet()) {
            assertEquals(cartItems.get(entry.getKey()), savedItems.get(entry.getKey()));
        }
    }
}
