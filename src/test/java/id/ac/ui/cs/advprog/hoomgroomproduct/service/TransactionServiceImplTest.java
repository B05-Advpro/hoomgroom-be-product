package id.ac.ui.cs.advprog.hoomgroomproduct.service;

import id.ac.ui.cs.advprog.hoomgroomproduct.model.Transaction;
import id.ac.ui.cs.advprog.hoomgroomproduct.model.TransactionBuilder;
import id.ac.ui.cs.advprog.hoomgroomproduct.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionServiceImplTest {
    Transaction transaction;

    Map<UUID, Integer> products;
    @Mock
    TransactionRepository transactionRepository;

    @InjectMocks
    TransactionServiceImpl transactionService;

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
    void testCreateTransaction() {
        doReturn(this.transaction).when(transactionRepository).save(any(Transaction.class));

        Transaction result = transactionService.create(this.transaction);
        verify(transactionRepository, times(1)).save(this.transaction);
        assertEquals(this.transaction.getId(), result.getId());
    }
}
