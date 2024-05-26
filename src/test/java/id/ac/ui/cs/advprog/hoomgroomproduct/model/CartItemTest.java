package id.ac.ui.cs.advprog.hoomgroomproduct.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class CartItemTest {
    CartItem cartItem;

    @BeforeEach
    void setUp() {
        cartItem = new CartItem("ca1c1b7d-f5aa-4573-aeff-d01665cc88c8", "Meja",
                25000, 1);
    }

    @Test
    void getProductId() {
        assertEquals("ca1c1b7d-f5aa-4573-aeff-d01665cc88c8", cartItem.getProductId());
    }

    @Test
    void getName() {
        assertEquals("Meja", cartItem.getName());
    }

    @Test
    void getPrice() {
        assertEquals(25000, cartItem.getPrice());
    }

    @Test
    void getQuantity() {
        assertEquals(1, cartItem.getQuantity());
    }
}
