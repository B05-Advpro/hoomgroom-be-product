package id.ac.ui.cs.advprog.hoomgroomproduct.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import id.ac.ui.cs.advprog.hoomgroomproduct.dto.TransactionItemRequestDto;
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
        TransactionItem product = new TransactionItem(UUID.fromString("ca1c1b7d-f5aa-4573-aeff-d01665cc88c8"),
                "Product 1", 15000, 2);
        items.add(product);

        this.transaction = new TransactionBuilder()
                .setItems(items)
                .setPromoCodeUsed("BELANJAHEMAT20")
                .setUserId(UUID.fromString("4f59c670-f83f-4d41-981f-37ee660a6e4c"))
                .setDeliveryMethod("MOTOR")
                .build();
    }

    @Test
    void testCreateTransaction() throws Exception {
        TransactionItemRequestDto requestProduct = new TransactionItemRequestDto();
        requestProduct.setProductId("ca1c1b7d-f5aa-4573-aeff-d01665cc88c8");
        requestProduct.setName("Product 1");
        requestProduct.setPrice(15000);
        requestProduct.setQuantity(2);

        List<TransactionItemRequestDto> requestProducts = new ArrayList<>();
        requestProducts.add(requestProduct);

        TransactionRequestDto request = new TransactionRequestDto();
        request.setItems(requestProducts);
        request.setUserId("4f59c670-f83f-4d41-981f-37ee660a6e4c");
        request.setPromoCodeUsed("BELANJAHEMAT20");
        request.setDeliveryMethod("MOTOR");

        doReturn(this.transaction).when(transactionService).create(request);

        mockMvc.perform(post("/transaction/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(request)))
                .andExpect(status().isOk());
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
