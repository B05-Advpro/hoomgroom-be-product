package id.ac.ui.cs.advprog.hoomgroomproduct.model;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class CartTest {
    Cart cart;
    List<CartItem> cartItems;

    @BeforeEach
    void setUp() {
        CartItem cartItem = new CartItem("ca1c1b7d-f5aa-4573-aeff-d01665cc88c8",
                "Meja", 25000, 1);
        this.cart = new Cart(1L);
        this.cart.getItems().add(cartItem);
        this.cart.setTotalPrice(25000);
        this.cartItems = new ArrayList<>();
        this.cartItems.add(cartItem);
    }

    @Test
    void getUserId() {
        assertEquals(1L, cart.getUserId());
    }

    @Test
    void getItems() {
        List<CartItem> savedItems = cart.getItems();
        assertEquals(cartItems, savedItems);
        assertEquals(cartItems.size(), savedItems.size());
        for (int i = 0; i < cartItems.size(); i++) {
            assertEquals(cartItems.get(i), savedItems.get(i));
        }
    }

    @Test
    void getWallet() {
        assertEquals(0, this.cart.getWallet());
    }

    @Test
    void setWallet() {
        this.cart.setWallet(50000);
        assertEquals(50000, this.cart.getWallet());
    }

    @Test
    void getTotalPrice() {
        assertEquals(25000, this.cart.getTotalPrice());
    }

    @Test
    void setTotalPrice() {
        this.cart.setTotalPrice(50000);
        assertEquals(50000, this.cart.getTotalPrice());
    }
}
