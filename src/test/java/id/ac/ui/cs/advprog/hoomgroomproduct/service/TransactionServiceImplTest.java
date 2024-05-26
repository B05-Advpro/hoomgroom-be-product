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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
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

    String username;
    Cart cart;
    TransactionRequestDto request;
    String token;

    @BeforeEach
    void setUp() {
        this.username = "anto";
        String productId = "ca1c1b7d-f5aa-4573-aeff-d01665cc88c8";
        String name = "Meja";
        double price = 25000;
        int quantity = 2;
        CartItem cartItem = new CartItem(productId, name, price, quantity);

        this.cart = new Cart(this.username);
        this.cart.setWallet(50000);
        this.cart.setTotalPrice(50000);
        this.cart.getItems().add(cartItem);

        this.request = new TransactionRequestDto();
        request.setUsername("anto");
        request.setPromoCodeUsed("BELANJAHEMAT20");
        request.setDeliveryMethod("MOTOR");

        this.token = "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiVVNFUiIsInN1YiI6ImR1bW15IiwiaWF0IjoxNzE2NzE3NzQ3LCJleHAiOjE3MTY3MTkxODd9.ofBphwX4VOnz_u9DhykKMdfd2rCBaQjJ9TCKcdG4qcg";
    }

    @Test
    void testUpdateSales() {
        List<TransactionItem> transactionItems = Arrays.asList(
                new TransactionItem("ca1c1b7d-f5aa-4573-aeff-d01665cc88c8", "Meja",
                        25000, 2)
        );

        transactionService.updateSales(transactionItems, this.token);

        Map<String, Integer> expectedSales = new HashMap<>();
        expectedSales.put("ca1c1b7d-f5aa-4573-aeff-d01665cc88c8", 2);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);

        HttpEntity<Map<String, Integer>> requestEntity = new HttpEntity<>(expectedSales, headers);

        verify(restTemplate).exchange(eq("https://api.b5-hoomgroom.com/admin/product/sold"), eq(HttpMethod.POST), eq(requestEntity), eq(Void.class));
    }

    @Test
    void testCreateTransactionEmptyCart() {
        this.cart.getItems().clear();

        when(cartService.getCart(this.username)).thenReturn(cart);

        Exception e = assertThrows(IllegalStateException.class, () -> {
            transactionService.create(this.request, this.token);
        });

        assertEquals("Cart is empty", e.getMessage());

        verify(cartService, times(1)).getCart(this.username);
        verify(transactionRepository, times(0)).save(any(Transaction.class));
        verify(restTemplate, times(0)).postForEntity(anyString(), anyMap(), eq(Void.class));
    }

    @Test
    void testCreateTransactionNotEnoughBalance() {
        this.cart.setWallet(0);

        when(cartService.getCart(this.username)).thenReturn(cart);

        Exception e = assertThrows(IllegalStateException.class, () -> {
            Transaction transaction = transactionService.create(this.request, this.token);
        });

        assertEquals("Not enough balance in wallet", e.getMessage());
        verify(cartService, times(1)).getCart(this.username);
        verify(transactionRepository, times(0)).save(any(Transaction.class));
        verify(restTemplate, times(0)).postForEntity(anyString(), anyMap(), eq(Void.class));
    }
    @Test
    void testCreateTransactionSuccess() {
        when(cartService.getCart(this.username)).thenReturn(cart);

        Transaction transaction = transactionService.create(this.request, this.token);
        assertNotNull(transaction);
        assertEquals(this.username, transaction.getUsername());
        assertEquals(40000, transaction.getTotalPrice());
        verify(transactionRepository, times(1)).save(any(Transaction.class));

        Map<String, Integer> expectedSales = new HashMap<>();
        expectedSales.put("ca1c1b7d-f5aa-4573-aeff-d01665cc88c8", 2);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);

        HttpEntity<Map<String, Integer>> requestEntity = new HttpEntity<>(expectedSales, headers);

        verify(restTemplate).exchange(eq("https://api.b5-hoomgroom.com/admin/product/sold"), eq(HttpMethod.POST), eq(requestEntity), eq(Void.class));
    }

    @Test
    void testCreateTransactionSuccessNoPromoCode() {
        this.request.setPromoCodeUsed("");

        when(cartService.getCart(this.username)).thenReturn(cart);

        Transaction transaction = transactionService.create(this.request, this.token);
        assertNotNull(transaction);
        assertEquals(this.username, transaction.getUsername());
        assertEquals(50000, transaction.getTotalPrice());
        verify(transactionRepository, times(1)).save(any(Transaction.class));

        Map<String, Integer> expectedSales = new HashMap<>();
        expectedSales.put("ca1c1b7d-f5aa-4573-aeff-d01665cc88c8", 2);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);

        HttpEntity<Map<String, Integer>> requestEntity = new HttpEntity<>(expectedSales, headers);

        verify(restTemplate).exchange(eq("https://api.b5-hoomgroom.com/admin/product/sold"), eq(HttpMethod.POST), eq(requestEntity), eq(Void.class));
    }

    @Test
    void testGetAll() {
        Transaction transaction1 = new TransactionBuilder().setUsername("anto").setDeliveryMethod("MOTOR").build();
        Transaction transaction2 = new TransactionBuilder().setUsername("budi").setDeliveryMethod("MOTOR").build();
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction1);
        transactions.add(transaction2);

        when(transactionRepository.findAll()).thenReturn(transactions);

        List<Transaction> savedTransactions = transactionService.getAll();
        assertEquals(transactions, savedTransactions);
        for (int i = 0; i < transactions.size(); i++) {
            assertEquals(transactions.get(i).getUsername(), savedTransactions.get(i).getUsername());
        }
    }

    @Test
    void testGetTransactionByUsername() {
        Transaction transaction1 = new TransactionBuilder().setUsername("anto").setDeliveryMethod("MOTOR").build();
        Transaction transaction2 = new TransactionBuilder().setUsername("anto").setDeliveryMethod("MOTOR").build();
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction1);
        transactions.add(transaction2);

        when(transactionRepository.findByUsername("anto")).thenReturn(transactions);

        List<Transaction> savedTransactions = transactionService.getTransactionByUsername("anto");
        assertEquals(transactions, savedTransactions);
        for (int i = 0; i < transactions.size(); i++) {
            assertEquals(transactions.get(i).getUsername(), savedTransactions.get(i).getUsername());
        }
    }
}
