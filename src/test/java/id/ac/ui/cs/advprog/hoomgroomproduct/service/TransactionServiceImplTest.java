package id.ac.ui.cs.advprog.hoomgroomproduct.service;

import id.ac.ui.cs.advprog.hoomgroomproduct.dto.TransactionRequestDto;
import id.ac.ui.cs.advprog.hoomgroomproduct.model.*;
import id.ac.ui.cs.advprog.hoomgroomproduct.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionServiceImplTest {

    @Mock
    CartService cartService;

    @Mock
    TransactionRepository transactionRepository;

    @InjectMocks
    TransactionServiceImpl transactionService;

    @Test
    void testCreateTransactionEmptyCart() {
        Long userId = 1L;
        Cart cart = new Cart(userId);

        TransactionRequestDto request = new TransactionRequestDto();
        request.setUserId(1L);
        request.setPromoCodeUsed("BELANJAHEMAT20");
        request.setDeliveryMethod("MOTOR");

        when(cartService.getCart(userId)).thenReturn(cart);

        assertThrows(IllegalStateException.class, () -> {
            transactionService.create(request);
        });
    }

    @Test
    void testCreateTransactionNotEnoughBalance() {
        Long userId = 1L;
        String productId = "ca1c1b7d-f5aa-4573-aeff-d01665cc88c8";
        String name = "Meja";
        double price = 25000;
        int quantity = 2;
        Cart cart = new Cart(userId);
        CartItem cartItem = new CartItem(productId, name, price, quantity);
        cart.getItems().add(cartItem);

        TransactionRequestDto request = new TransactionRequestDto();
        request.setUserId(1L);
        request.setPromoCodeUsed("BELANJAHEMAT20");
        request.setDeliveryMethod("MOTOR");

        when(cartService.getCart(userId)).thenReturn(cart);

        assertThrows(IllegalStateException.class, () -> {
            Transaction transaction = transactionService.create(request);
        });

        verify(transactionRepository, times(0)).save(any(Transaction.class));
    }
    @Test
    void testCreateTransactionSuccess() {
        Long userId = 1L;
        String productId = "ca1c1b7d-f5aa-4573-aeff-d01665cc88c8";
        String name = "Meja";
        double price = 25000;
        int quantity = 2;
        Cart cart = new Cart(userId);
        cart.setWallet(40000);
        CartItem cartItem = new CartItem(productId, name, price, quantity);
        cart.getItems().add(cartItem);

        TransactionRequestDto request = new TransactionRequestDto();
        request.setUserId(1L);
        request.setPromoCodeUsed("BELANJAHEMAT20");
        request.setDeliveryMethod("MOTOR");

        when(cartService.getCart(userId)).thenReturn(cart);

        Transaction transaction = transactionService.create(request);
        assertNotNull(transaction);
        assertEquals(userId, transaction.getUserId());
        assertEquals(40000, transaction.getTotalPrice());
        verify(transactionRepository, times(1)).save(any(Transaction.class));
    }

    @Test
    void testGetAll() {
        Transaction transaction1 = new TransactionBuilder().setUserId(1L).setDeliveryMethod("MOTOR").build();
        Transaction transaction2 = new TransactionBuilder().setUserId(2L).setDeliveryMethod("MOTOR").build();
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction1);
        transactions.add(transaction2);

        when(transactionRepository.findAll()).thenReturn(transactions);

        List<Transaction> savedTransactions = transactionService.getAll();
        assertEquals(transactions, savedTransactions);
        for (int i = 0; i < transactions.size(); i++) {
            assertEquals(transactions.get(i).getUserId(), savedTransactions.get(i).getUserId());
        }
    }

    @Test
    void testGetTransactionByUserId() {
        Transaction transaction1 = new TransactionBuilder().setUserId(1L).setDeliveryMethod("MOTOR").build();
        Transaction transaction2 = new TransactionBuilder().setUserId(1L).setDeliveryMethod("MOTOR").build();
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction1);
        transactions.add(transaction2);

        when(transactionRepository.findByUserId(1L)).thenReturn(transactions);

        List<Transaction> savedTransactions = transactionService.getTransactionByUserId(1L);
        assertEquals(transactions, savedTransactions);
        for (int i = 0; i < transactions.size(); i++) {
            assertEquals(transactions.get(i).getUserId(), savedTransactions.get(i).getUserId());
        }
    }
}
