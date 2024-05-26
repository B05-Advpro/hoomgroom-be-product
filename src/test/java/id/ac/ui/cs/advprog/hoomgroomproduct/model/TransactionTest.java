package id.ac.ui.cs.advprog.hoomgroomproduct.model;

import id.ac.ui.cs.advprog.hoomgroomproduct.enums.DeliveryMethod;
import id.ac.ui.cs.advprog.hoomgroomproduct.enums.DeliveryStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
class TransactionTest {
    Transaction transaction;
    List<TransactionItem> items;


    @BeforeEach
    void setUp() {
        this.items = new ArrayList<>();
        TransactionItem product1 = new TransactionItem("ca1c1b7d-f5aa-4573-aeff-d01665cc88c8",
                "Product 1", 15000, 2);
        TransactionItem product2 = new TransactionItem("df6c1b7d-f5aa-4573-aeff-d01665cc88c8",
                "Product 2", 25000, 4);
        this.items.add(product1);
        this.items.add(product2);

        this.transaction = new TransactionBuilder()
                .setItems(this.items)
                .setTotalPrice(130000)
                .setPromoCodeUsed("BELANJAHEMAT20")
                .setUsername("dummy")
                .setDeliveryMethod("MOTOR")
                .build();
    }

    @Test
    void testGetProducts() {
        List<TransactionItem> savedProducts = this.transaction.getItems();
        assertSame(this.items, savedProducts);
        assertEquals(this.items.size(), savedProducts.size());
        for (int i = 0; i < this.items.size(); i++) {
            assertEquals(this.items.get(i), savedProducts.get(i));
        }
    }

    @Test
    void testCreatedAt() {
        assertNotNull(this.transaction.getCreatedAt());
    }

    @Test
    void testGetTotalPrice() {
        assertEquals(130000, this.transaction.getTotalPrice());
    }
    @Test
    void testGetPromoCodeUsed() {
        assertEquals("BELANJAHEMAT20", this.transaction.getPromoCodeUsed());
    }

    @Test
    void testGetUsername() {
        assertEquals("dummy", this.transaction.getUsername());
    }

    @Test
    void testGetDeliveryStatus() {
        assertEquals(DeliveryStatus.MENUNGGU_VERIFIKASI.getValue(), this.transaction.getDeliveryStatus());
    }

    @Test
    void testSetInvalidDeliveryStatus() {
        assertThrows(IllegalArgumentException.class, () -> this.transaction.setDeliveryStatus("MEOW"));
    }

    @Test
    void testGetDeliveryCode() {
        assertEquals("", this.transaction.getDeliveryCode());
    }

    @Test
    void testGetDeliveryMethod() {
        assertEquals(DeliveryMethod.MOTOR.getValue(), this.transaction.getDeliveryMethod());
    }

    @Test
    void testInvalidDeliveryMethod() {
        assertThrows(IllegalArgumentException.class, () -> {
            this.transaction = new TransactionBuilder()
                    .setItems(this.items)
                    .setTotalPrice(130000)
                    .setPromoCodeUsed("BELANJAHEMAT20")
                    .setUsername("dummy")
                    .setDeliveryMethod("BECAK")
                    .build();
        });
    }
}
