package id.ac.ui.cs.advprog.hoomgroomproduct.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import id.ac.ui.cs.advprog.hoomgroomproduct.dto.TransactionRequestDto;
import id.ac.ui.cs.advprog.hoomgroomproduct.model.Transaction;
import id.ac.ui.cs.advprog.hoomgroomproduct.model.TransactionBuilder;
import id.ac.ui.cs.advprog.hoomgroomproduct.model.TransactionItem;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TransactionController.class)
class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionService transactionService;

    Transaction transaction;

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
    }

    @Test
    void testCreateTransaction() throws Exception {
        TransactionRequestDto request = new TransactionRequestDto();
        request.setUserId(1L);
        request.setPromoCodeUsed("BELANJAHEMAT20");
        request.setDeliveryMethod("MOTOR");

        when(transactionService.create(any(TransactionRequestDto.class))).thenReturn(this.transaction);

        mockMvc.perform(post("/transaction/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(request)))
                .andExpect(status().isOk());

        verify(transactionService, times(1)).create(any(TransactionRequestDto.class));
    }

    @Test
    void testCreateTransactionEmptyCart() throws Exception {
        TransactionRequestDto request = new TransactionRequestDto();
        request.setUserId(1L);
        request.setPromoCodeUsed("BELANJAHEMAT20");
        request.setDeliveryMethod("MOTOR");

        when(transactionService.create(any(TransactionRequestDto.class))).thenThrow(new IllegalStateException());

        mockMvc.perform(post("/transaction/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(request)))
                        .andExpect(status().isBadRequest());
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
