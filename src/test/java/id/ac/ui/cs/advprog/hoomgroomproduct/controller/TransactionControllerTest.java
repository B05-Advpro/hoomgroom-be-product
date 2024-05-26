package id.ac.ui.cs.advprog.hoomgroomproduct.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import id.ac.ui.cs.advprog.hoomgroomproduct.dto.TransactionRequestDto;
import id.ac.ui.cs.advprog.hoomgroomproduct.model.Transaction;
import id.ac.ui.cs.advprog.hoomgroomproduct.model.TransactionBuilder;
import id.ac.ui.cs.advprog.hoomgroomproduct.model.TransactionItem;
import id.ac.ui.cs.advprog.hoomgroomproduct.service.JwtService;
import id.ac.ui.cs.advprog.hoomgroomproduct.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TransactionController.class)
class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionService transactionService;

    @MockBean
    private JwtService jwtService;

    Transaction transaction;
    TransactionRequestDto request;

    @BeforeEach
    void setUp() {
        List<TransactionItem> items = new ArrayList<>();
        TransactionItem product = new TransactionItem("ca1c1b7d-f5aa-4573-aeff-d01665cc88c8",
                "Meja", 25000, 2);
        items.add(product);

        this.transaction = new TransactionBuilder()
                .setItems(items)
                .setPromoCodeUsed("BELANJAHEMAT20")
                .setTotalPrice(40000)
                .setUserId(1L)
                .setDeliveryMethod("MOTOR")
                .build();

        this.request = new TransactionRequestDto();
        request.setUserId(1L);
        request.setPromoCodeUsed("BELANJAHEMAT20");
        request.setDeliveryMethod("MOTOR");
    }

    @Test
    void testCreateTransaction() throws Exception {
        when(transactionService.create(any(TransactionRequestDto.class))).thenReturn(this.transaction);
        when(jwtService.isTokenValid(anyString())).thenReturn(true);
        when(jwtService.extractRole(anyString())).thenReturn("USER");

        mockMvc.perform(post("/transaction/create")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer jwtToken")
                .content(asJsonString(request)))
                .andExpect(status().isOk());

        verify(jwtService, times(1)).isTokenValid(anyString());
        verify(jwtService, times(1)).extractRole(anyString());
        verify(transactionService, times(1)).create(any(TransactionRequestDto.class));
    }

    @Test
    void testCreateTransactionInvalid() throws Exception {
        when(transactionService.create(any(TransactionRequestDto.class))).thenThrow(new IllegalStateException());
        when(jwtService.isTokenValid(anyString())).thenReturn(true);
        when(jwtService.extractRole(anyString())).thenReturn("USER");

        mockMvc.perform(post("/transaction/create")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer jwtToken")
                .content(asJsonString(request)))
                .andExpect(status().isBadRequest());

        verify(jwtService, times(1)).isTokenValid(anyString());
        verify(jwtService, times(1)).extractRole(anyString());
        verify(transactionService, times(1)).create(any(TransactionRequestDto.class));
    }

    @Test
    void testCreateTransactionInvalidToken() throws Exception {
        when(transactionService.create(any(TransactionRequestDto.class))).thenThrow(new IllegalStateException());
        when(jwtService.isTokenValid(anyString())).thenReturn(false);

        mockMvc.perform(post("/transaction/create")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer jwtToken")
                .content(asJsonString(request)))
                .andExpect(status().isForbidden());

        verify(jwtService, times(1)).isTokenValid(anyString());
        verify(jwtService, times(0)).extractRole(anyString());
        verify(transactionService, times(0)).create(any(TransactionRequestDto.class));
    }

    @Test
    void testCreateTransactionInvalidRole() throws Exception {
        when(transactionService.create(any(TransactionRequestDto.class))).thenThrow(new IllegalStateException());
        when(jwtService.isTokenValid(anyString())).thenReturn(true);
        when(jwtService.extractRole(anyString())).thenReturn("ADMIN");

        mockMvc.perform(post("/transaction/create")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer jwtToken")
                .content(asJsonString(request)))
                .andExpect(status().isForbidden());

        verify(jwtService, times(1)).isTokenValid(anyString());
        verify(jwtService, times(1)).extractRole(anyString());
        verify(transactionService, times(0)).create(any(TransactionRequestDto.class));
    }

    @Test
    void testGetAll() throws Exception {
        Long userId1 = 1L;
        Long userId2 = 2L;
        Transaction transaction1 = new TransactionBuilder().setUserId(userId1).setDeliveryMethod("MOTOR").build();
        Transaction transaction2 = new TransactionBuilder().setUserId(userId2).setDeliveryMethod("PESAWAT").build();

        when(transactionService.getAll()).thenReturn(Arrays.asList(transaction1, transaction2));

        mockMvc.perform(get("/transaction/get-all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].userId").value(userId1))
                .andExpect(jsonPath("$[1].userId").value(userId2));
    }

    @Test
    void testGetAllEmpty() throws Exception {
        when(transactionService.getAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/transaction/get-all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void testGetTransactionByUserId() throws Exception {
        long userId = 1L;
        Transaction transaction1 = new TransactionBuilder().setUserId(userId).setDeliveryMethod("MOTOR").build();
        Transaction transaction2 = new TransactionBuilder().setUserId(userId).setDeliveryMethod("PESAWAT").build();

        when(jwtService.isTokenValid(anyString())).thenReturn(true);
        when(jwtService.extractRole(anyString())).thenReturn("USER");
        when(transactionService.getTransactionByUserId(userId)).thenReturn(Arrays.asList(transaction1, transaction2));

        mockMvc.perform(get("/transaction/get/{userId}", userId)
                .header("Authorization", "Bearer jwtToken"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].userId").value(userId))
                .andExpect(jsonPath("$[1].userId").value(userId));

        verify(jwtService, times(1)).isTokenValid(anyString());
        verify(jwtService, times(1)).extractRole(anyString());
        verify(transactionService, times(1)).getTransactionByUserId(userId);
    }

    @Test
    void testGetTransactionByUserIdEmpty() throws Exception {
        Long userId = 1L;

        when(jwtService.isTokenValid(anyString())).thenReturn(true);
        when(jwtService.extractRole(anyString())).thenReturn("USER");
        when(transactionService.getTransactionByUserId(userId)).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/transaction/get/{userId}", userId)
                .header("Authorization", "Bearer jwtToken"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());

        verify(jwtService, times(1)).isTokenValid(anyString());
        verify(jwtService, times(1)).extractRole(anyString());
        verify(transactionService, times(1)).getTransactionByUserId(userId);
    }

    @Test
    void testGetTransactionByUserIdInvalidToken() throws Exception {
        Long userId = 1L;

        when(jwtService.isTokenValid(anyString())).thenReturn(false);

        mockMvc.perform(get("/transaction/get/{userId}", userId)
                .header("Authorization", "Bearer jwtToken"))
                .andExpect(status().isForbidden());

        verify(jwtService, times(1)).isTokenValid(anyString());
        verify(jwtService, times(0)).extractRole(anyString());
        verify(transactionService, times(0)).getTransactionByUserId(userId);
    }

    @Test
    void testGetTransactionByUserIdInvalidRole() throws Exception {
        Long userId = 1L;

        when(jwtService.isTokenValid(anyString())).thenReturn(true);
        when(jwtService.extractRole(anyString())).thenReturn("ADMIN");

        mockMvc.perform(get("/transaction/get/{userId}", userId)
                        .header("Authorization", "Bearer jwtToken"))
                .andExpect(status().isForbidden());

        verify(jwtService, times(1)).isTokenValid(anyString());
        verify(jwtService, times(1)).extractRole(anyString());
        verify(transactionService, times(0)).getTransactionByUserId(userId);
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
