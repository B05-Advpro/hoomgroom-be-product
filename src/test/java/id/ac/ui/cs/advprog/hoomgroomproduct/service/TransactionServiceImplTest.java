package id.ac.ui.cs.advprog.hoomgroomproduct.service;

import id.ac.ui.cs.advprog.hoomgroomproduct.dto.TransactionItemRequestDto;
import id.ac.ui.cs.advprog.hoomgroomproduct.dto.TransactionRequestDto;
import id.ac.ui.cs.advprog.hoomgroomproduct.model.Transaction;
import id.ac.ui.cs.advprog.hoomgroomproduct.model.TransactionBuilder;
import id.ac.ui.cs.advprog.hoomgroomproduct.model.TransactionItem;
import id.ac.ui.cs.advprog.hoomgroomproduct.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionServiceImplTest {
    Transaction transaction;

    List<TransactionItem> products;
    @Mock
    TransactionRepository transactionRepository;

    @InjectMocks
    TransactionServiceImpl transactionService;

    @BeforeEach
    void setUp() {
        this.products = new ArrayList<>();
        TransactionItem product = new TransactionItem(UUID.fromString("ca1c1b7d-f5aa-4573-aeff-d01665cc88c8"),
                "Product 1", 15000, 2);
        this.products.add(product);

        this.transaction = new TransactionBuilder()
                .setProducts(this.products)
                .setPromoCodeUsed("BELANJAHEMAT20")
                .setPembeli(UUID.fromString("4f59c670-f83f-4d41-981f-37ee660a6e4c"))
                .setDeliveryMethod("MOTOR")
                .build();
    }

    @Test
    void testCreateTransaction() {
        TransactionItemRequestDto requestProduct = new TransactionItemRequestDto();
        requestProduct.setId("ca1c1b7d-f5aa-4573-aeff-d01665cc88c8");
        requestProduct.setName("Product 1");
        requestProduct.setPrice(15000);
        requestProduct.setQuantity(2);

        List<TransactionItemRequestDto> requestProducts = new ArrayList<>();
        requestProducts.add(requestProduct);

        TransactionRequestDto request = new TransactionRequestDto();
        request.setProducts(requestProducts);
        request.setPembeli("4f59c670-f83f-4d41-981f-37ee660a6e4c");
        request.setPromoCodeUsed("BELANJAHEMAT20");
        request.setDeliveryMethod("MOTOR");

        doReturn(this.transaction).when(transactionRepository).save(any(Transaction.class));

        Transaction result = transactionService.create(request);
        verify(transactionRepository, times(1)).save(any(Transaction.class));
        assertNotNull(result);
        assertEquals(this.transaction.getPromoCodeUsed(), result.getPromoCodeUsed());
        assertEquals(this.transaction.getPembeli(), result.getPembeli());
        assertEquals(this.transaction.getDeliveryMethod(), result.getDeliveryMethod());
    }
}
