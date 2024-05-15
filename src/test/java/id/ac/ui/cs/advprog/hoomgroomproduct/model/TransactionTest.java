package id.ac.ui.cs.advprog.hoomgroomproduct.model;

import id.ac.ui.cs.advprog.hoomgroomproduct.enums.DeliveryMethod;
import id.ac.ui.cs.advprog.hoomgroomproduct.enums.DeliveryStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
class TransactionTest {
    Transaction transaction;
    Map<UUID, TransactionItem> products;


    @BeforeEach
    void setUp() {
        this.products = new HashMap<>();
        TransactionItem product1 = new TransactionItem(UUID.fromString("ca1c1b7d-f5aa-4573-aeff-d01665cc88c8"),
                "Product 1", 15000, 2);
        TransactionItem product2 = new TransactionItem(UUID.fromString("df6c1b7d-f5aa-4573-aeff-d01665cc88c8"),
                "Product 2", 25000, 4);
        this.products.put(UUID.fromString("ca1c1b7d-f5aa-4573-aeff-d01665cc88c8"), product1);
        this.products.put(UUID.fromString("df6c1b7d-f5aa-4573-aeff-d01665cc88c8"), product2);

        this.transaction = new TransactionBuilder()
                .setItems(this.products)
                .setTotalPrice(130000)
                .setPromoCodeUsed("BELANJAHEMAT20")
                .setUserId(UUID.fromString("4f59c670-f83f-4d41-981f-37ee660a6e4c"))
                .setDeliveryMethod("MOTOR")
                .build();
    }

    @Test
    void testGetProducts() {
        List<TransactionItem> savedProducts = this.transaction.getItems();
        assertSame(this.products, savedProducts);
        assertEquals(this.products.size(), savedProducts.size());
        for (Map.Entry<UUID, TransactionItem> entry : this.products.entrySet()) {
            assertSame(this.products.get(entry.getKey()), savedProducts.get(entry.getKey()));
        }
    }

    @Test
    void testGetProductsEmpty() {
        this.products.clear();

        assertThrows(IllegalArgumentException.class, () -> {
            this.transaction = new TransactionBuilder()
                    .setItems(this.products)
                    .setTotalPrice(130000)
                    .setPromoCodeUsed("BELANJAHEMAT20")
                    .setUserId(UUID.fromString("4f59c670-f83f-4d41-981f-37ee660a6e4c"))
                    .setDeliveryMethod("MOTOR")
                    .build();
        });
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
    void testGetPembeli() {
        assertEquals(UUID.fromString("4f59c670-f83f-4d41-981f-37ee660a6e4c"), this.transaction.getUserId());
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
                    .setItems(this.products)
                    .setTotalPrice(130000)
                    .setPromoCodeUsed("BELANJAHEMAT20")
                    .setUserId(UUID.fromString("4f59c670-f83f-4d41-981f-37ee660a6e4c"))
                    .setDeliveryMethod("BECAK")
                    .build();
        });
    }
}
