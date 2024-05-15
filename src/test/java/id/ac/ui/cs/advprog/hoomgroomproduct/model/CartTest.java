package id.ac.ui.cs.advprog.hoomgroomproduct.model;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class CartTest {
    Cart cart;
    List<CartItem> cartItems;

    @BeforeEach
    void setUp() {
        CartItem cartItem = new CartItem(UUID.fromString("ca1c1b7d-f5aa-4573-aeff-d01665cc88c8"),
                "Meja", 25000, 1);

        this.cart = new Cart(UUID.fromString("4e900a31-6af6-472d-aa44-15e145ed6b8d"));
        this.cart.getItems().add(cartItem);
        this.cartItems = new ArrayList<>();
        this.cartItems.add(cartItem);
    }

    @Test
    void getUserId() {
        assertEquals(UUID.fromString("4e900a31-6af6-472d-aa44-15e145ed6b8d"), cart.getUserId());
    }

    @Test
    void getItems() {
        List<CartItem> savedItems = cart.getItems();
        assertEquals(cartItems, savedItems);
        assertEquals(cartItems.size(), savedItems.size());
        for (int i = 0; i < savedItems.size(); i++) {
            assertEquals(cartItems.get(i), savedItems.get(i));
        }
    }
}
