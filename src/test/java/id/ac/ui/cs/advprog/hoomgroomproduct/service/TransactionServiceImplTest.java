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
import org.springframework.web.client.RestTemplate;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionServiceImplTest {

    @Mock
    CartService cartService;

    @Mock
    TransactionRepository transactionRepository;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    TransactionServiceImpl transactionService;

    Long userId;
    Cart cart;
    TransactionRequestDto request;

    @BeforeEach
    void setUp() {
        this.userId = 1L;
        String productId = "ca1c1b7d-f5aa-4573-aeff-d01665cc88c8";
        String name = "Meja";
        double price = 25000;
        int quantity = 2;
        CartItem cartItem = new CartItem(productId, name, price, quantity);

        this.cart = new Cart(this.userId);
        this.cart.setWallet(50000);
        this.cart.setTotalPrice(50000);
        this.cart.getItems().add(cartItem);

        this.request = new TransactionRequestDto();
        request.setUserId(1L);
        request.setPromoCodeUsed("BELANJAHEMAT20");
        request.setDeliveryMethod("MOTOR");
    }

    @Test
    void testUpdateSales() {
        List<TransactionItem> transactionItems = Arrays.asList(
                new TransactionItem("ca1c1b7d-f5aa-4573-aeff-d01665cc88c8", "Meja",
                        25000, 2)
        );

        transactionService.updateSales(transactionItems);

        Map<String, Integer> expectedSales = new HashMap<>();
        expectedSales.put("ca1c1b7d-f5aa-4573-aeff-d01665cc88c8", 2);

        verify(restTemplate).postForEntity(eq("https://api.b5-hoomgroom.com/admin/product/sold"), eq(expectedSales), eq(Void.class));
    }

    @Test
    void testCreateTransactionEmptyCart() {
        this.cart.getItems().clear();

        when(cartService.getCart(this.userId)).thenReturn(cart);

        Exception e = assertThrows(IllegalStateException.class, () -> {
            transactionService.create(this.request);
        });

        assertEquals("Cart is empty", e.getMessage());

        verify(cartService, times(1)).getCart(this.userId);
        verify(transactionRepository, times(0)).save(any(Transaction.class));
        verify(restTemplate, times(0)).postForEntity(anyString(), anyMap(), eq(Void.class));
    }

    @Test
    void testCreateTransactionNotEnoughBalance() {
        this.cart.setWallet(0);

        when(cartService.getCart(this.userId)).thenReturn(cart);

        Exception e = assertThrows(IllegalStateException.class, () -> {
            Transaction transaction = transactionService.create(this.request);
        });

        assertEquals("Not enough balance in wallet", e.getMessage());
        verify(cartService, times(1)).getCart(this.userId);
        verify(transactionRepository, times(0)).save(any(Transaction.class));
        verify(restTemplate, times(0)).postForEntity(anyString(), anyMap(), eq(Void.class));
    }
    @Test
    void testCreateTransactionSuccess() {
        when(cartService.getCart(this.userId)).thenReturn(cart);

        Transaction transaction = transactionService.create(this.request);
        assertNotNull(transaction);
        assertEquals(this.userId, transaction.getUserId());
        assertEquals(40000, transaction.getTotalPrice());
        verify(transactionRepository, times(1)).save(any(Transaction.class));

        Map<String, Integer> expectedSales = new HashMap<>();
        expectedSales.put("ca1c1b7d-f5aa-4573-aeff-d01665cc88c8", 2);

        verify(restTemplate).postForEntity(eq("https://api.b5-hoomgroom.com/admin/product/sold"), eq(expectedSales), eq(Void.class));
    }

    @Test
    void testCreateTransactionSuccessNoPromoCode() {
        this.request.setPromoCodeUsed("");

        when(cartService.getCart(this.userId)).thenReturn(cart);

        Transaction transaction = transactionService.create(this.request);
        assertNotNull(transaction);
        assertEquals(this.userId, transaction.getUserId());
        assertEquals(50000, transaction.getTotalPrice());
        verify(transactionRepository, times(1)).save(any(Transaction.class));

        Map<String, Integer> expectedSales = new HashMap<>();
        expectedSales.put("ca1c1b7d-f5aa-4573-aeff-d01665cc88c8", 2);

        verify(restTemplate).postForEntity(eq("https://api.b5-hoomgroom.com/admin/product/sold"), eq(expectedSales), eq(Void.class));
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
