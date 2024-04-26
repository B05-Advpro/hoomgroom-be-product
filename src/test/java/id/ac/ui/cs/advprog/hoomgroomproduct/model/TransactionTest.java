package id.ac.ui.cs.advprog.hoomgroomproduct.model;

import id.ac.ui.cs.advprog.hoomgroomproduct.enums.DeliveryMethod;
import id.ac.ui.cs.advprog.hoomgroomproduct.enums.DeliveryStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
class TransactionTest {
    Transaction transaction;
    Map<UUID, Integer> products;


    @BeforeEach
    void setUp() {
        this.products = new HashMap<>();
        this.products.put(UUID.fromString("ca1c1b7d-f5aa-4573-aeff-d01665cc88c8"), 1);

        this.transaction = new TransactionBuilder()
                .setProducts(this.products)
                .setPromoCodeUsed("BELANJAHEMAT20")
                .setPembeli(UUID.fromString("4f59c670-f83f-4d41-981f-37ee660a6e4c"))
                .setDeliveryMethod("MOTOR")
                .build();
    }

    @Test
    void testGetProducts() {
        Map<UUID, Integer> savedProducts = this.transaction.getProducts();
        assertTrue(savedProducts.containsKey(UUID.fromString("ca1c1b7d-f5aa-4573-aeff-d01665cc88c8")));
        int quantity = savedProducts.get(UUID.fromString("ca1c1b7d-f5aa-4573-aeff-d01665cc88c8"));
        assertEquals(1, quantity);
    }

    @Test
    void testGetProductsEmpty() {
        this.products.clear();

        assertThrows(IllegalArgumentException.class, () -> {
            this.transaction = new TransactionBuilder()
                    .setProducts(this.products)
                    .setPromoCodeUsed("BELANJAHEMAT20")
                    .setPembeli(UUID.fromString("4f59c670-f83f-4d41-981f-37ee660a6e4c"))
                    .setDeliveryMethod("MOTOR")
                    .build();
        });
    }
    @Test
    void testGetPromoCodeUsed() {
        assertEquals("BELANJAHEMAT20", this.transaction.getPromoCodeUsed());
    }

    @Test
    void testGetPembeli() {
        assertEquals(UUID.fromString("4f59c670-f83f-4d41-981f-37ee660a6e4c"), this.transaction.getPembeli());
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
                    .setProducts(this.products)
                    .setPromoCodeUsed("BELANJAHEMAT20")
                    .setPembeli(UUID.fromString("4f59c670-f83f-4d41-981f-37ee660a6e4c"))
                    .setDeliveryMethod("BECAK")
                    .build();
        });
    }
}
