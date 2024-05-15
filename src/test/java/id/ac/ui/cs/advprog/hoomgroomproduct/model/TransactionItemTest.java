package id.ac.ui.cs.advprog.hoomgroomproduct.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class TransactionItemTest {
    TransactionItem transactionItem;

    @BeforeEach
    void setUp() {
        transactionItem = new TransactionItem(UUID.fromString("ca1c1b7d-f5aa-4573-aeff-d01665cc88c8"),
                "Product 1", 15000, 2);
    }

    @Test
    void testGetId() {
        assertEquals(UUID.fromString("ca1c1b7d-f5aa-4573-aeff-d01665cc88c8"), transactionItem.getProductId());
    }

    @Test
    void testGetName() {
        assertEquals("Product 1", transactionItem.getName());
    }

    @Test
    void testGetPrice() {
        assertEquals(15000, transactionItem.getPrice());
    }

    @Test
    void testGetQuantity() {
        assertEquals(2, transactionItem.getQuantity());
    }
}
