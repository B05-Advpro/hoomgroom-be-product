package id.ac.ui.cs.advprog.hoomgroomproduct.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import id.ac.ui.cs.advprog.hoomgroomproduct.dto.TransactionRequestDto;
import id.ac.ui.cs.advprog.hoomgroomproduct.dto.TransactionStatusUpdateRequestDto;
import id.ac.ui.cs.advprog.hoomgroomproduct.model.Transaction;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
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

        this.transaction = new Transaction("dummy", "BELANJAHEMAT20", "MOTOR",
                items, 40000);

        this.request = new TransactionRequestDto();
        request.setUsername("dummy");
        request.setPromoCodeUsed("BELANJAHEMAT20");
        request.setDeliveryMethod("MOTOR");
    }

    @Test
    void testCreateTransaction() throws Exception {
        when(transactionService.create(any(TransactionRequestDto.class), anyString())).thenReturn(this.transaction);
        when(jwtService.isTokenValid(anyString())).thenReturn(true);
        when(jwtService.extractRole(anyString())).thenReturn("USER");

        mockMvc.perform(post("/transaction/create")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer jwtToken")
                .content(asJsonString(request)))
                .andExpect(status().isOk());

        verify(jwtService, times(1)).isTokenValid(anyString());
        verify(jwtService, times(1)).extractRole(anyString());
        verify(transactionService, times(1)).create(any(TransactionRequestDto.class), anyString());
    }

    @Test
    void testCreateTransactionInvalid() throws Exception {
        when(transactionService.create(any(TransactionRequestDto.class), anyString())).thenThrow(new IllegalStateException());
        when(jwtService.isTokenValid(anyString())).thenReturn(true);
        when(jwtService.extractRole(anyString())).thenReturn("USER");

        mockMvc.perform(post("/transaction/create")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer jwtToken")
                .content(asJsonString(request)))
                .andExpect(status().isBadRequest());

        verify(jwtService, times(1)).isTokenValid(anyString());
        verify(jwtService, times(1)).extractRole(anyString());
        verify(transactionService, times(1)).create(any(TransactionRequestDto.class), anyString());
    }

    @Test
    void testCreateTransactionInvalidToken() throws Exception {
        when(transactionService.create(any(TransactionRequestDto.class), anyString())).thenThrow(new IllegalStateException());
        when(jwtService.isTokenValid(anyString())).thenReturn(false);

        mockMvc.perform(post("/transaction/create")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer jwtToken")
                .content(asJsonString(request)))
                .andExpect(status().isForbidden());

        verify(jwtService, times(1)).isTokenValid(anyString());
        verify(jwtService, times(0)).extractRole(anyString());
        verify(transactionService, times(0)).create(any(TransactionRequestDto.class), anyString());
    }

    @Test
    void testCreateTransactionInvalidRole() throws Exception {
        when(transactionService.create(any(TransactionRequestDto.class), anyString())).thenThrow(new IllegalStateException());
        when(jwtService.isTokenValid(anyString())).thenReturn(true);
        when(jwtService.extractRole(anyString())).thenReturn("ADMIN");

        mockMvc.perform(post("/transaction/create")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer jwtToken")
                .content(asJsonString(request)))
                .andExpect(status().isForbidden());

        verify(jwtService, times(1)).isTokenValid(anyString());
        verify(jwtService, times(1)).extractRole(anyString());
        verify(transactionService, times(0)).create(any(TransactionRequestDto.class), anyString());
    }

    @Test
    void testGetAll() throws Exception {
        String username1 = "dummy1";
        String username2 = "dummy2";
        Transaction transaction1 = new Transaction(username1, "BELANJAHEMAT20", "MOTOR");
        Transaction transaction2 = new Transaction(username2, "BELANJAHEMAT20", "PESAWAT");

        when(transactionService.getAll()).thenReturn(Arrays.asList(transaction1, transaction2));

        mockMvc.perform(get("/transaction/get-all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].username").value(username1))
                .andExpect(jsonPath("$[1].username").value(username2));
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
    void testGetTransactionByUsername() throws Exception {
        String username = "dummy";
        Transaction transaction1 = new Transaction(username, "BELANJAHEMAT20", "MOTOR");
        Transaction transaction2 = new Transaction(username, "BELANJAHEMAT20", "PESAWAT");

        when(jwtService.isTokenValid(anyString())).thenReturn(true);
        when(jwtService.extractRole(anyString())).thenReturn("USER");
        when(transactionService.getTransactionByUsername(username)).thenReturn(Arrays.asList(transaction1, transaction2));

        mockMvc.perform(get("/transaction/get/{username}", username)
                .header("Authorization", "Bearer jwtToken"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].username").value(username))
                .andExpect(jsonPath("$[1].username").value(username));

        verify(jwtService, times(1)).isTokenValid(anyString());
        verify(jwtService, times(1)).extractRole(anyString());
        verify(transactionService, times(1)).getTransactionByUsername(username);
    }

    @Test
    void testGetTransactionByusernameEmpty() throws Exception {
        String username = "dummy";

        when(jwtService.isTokenValid(anyString())).thenReturn(true);
        when(jwtService.extractRole(anyString())).thenReturn("USER");
        when(transactionService.getTransactionByUsername(username)).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/transaction/get/{username}", username)
                .header("Authorization", "Bearer jwtToken"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());

        verify(jwtService, times(1)).isTokenValid(anyString());
        verify(jwtService, times(1)).extractRole(anyString());
        verify(transactionService, times(1)).getTransactionByUsername(username);
    }

    @Test
    void testGetTransactionByUsernameInvalidToken() throws Exception {
        String username = "dummy";

        when(jwtService.isTokenValid(anyString())).thenReturn(false);

        mockMvc.perform(get("/transaction/get/{username}", username)
                .header("Authorization", "Bearer jwtToken"))
                .andExpect(status().isForbidden());

        verify(jwtService, times(1)).isTokenValid(anyString());
        verify(jwtService, times(0)).extractRole(anyString());
        verify(transactionService, times(0)).getTransactionByUsername(username);
    }

    @Test
    void testGetTransactionByusernameInvalidRole() throws Exception {
        String username = "dummy";

        when(jwtService.isTokenValid(anyString())).thenReturn(true);
        when(jwtService.extractRole(anyString())).thenReturn("ADMIN");

        mockMvc.perform(get("/transaction/get/{username}", username)
                        .header("Authorization", "Bearer jwtToken"))
                .andExpect(status().isForbidden());

        verify(jwtService, times(1)).isTokenValid(anyString());
        verify(jwtService, times(1)).extractRole(anyString());
        verify(transactionService, times(0)).getTransactionByUsername(username);
    }

    @Test
    void testNextStatusTransaction() throws Exception {
        TransactionStatusUpdateRequestDto request = new TransactionStatusUpdateRequestDto();
        request.setId("4f59c670-f83f-4d41-981f-37ee660a6e4c");

        when(jwtService.isTokenValid(anyString())).thenReturn(true);
        when(jwtService.extractRole(anyString())).thenReturn("ADMIN");

        String role = jwtService.extractRole("test");

        doReturn(this.transaction).when(transactionService).nextStatus(request, role);

        mockMvc.perform(
                    post("/transaction/next")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(asJsonString(request))
                            .header("Authorization", "Bearer jwtToken")
                    )
                    .andExpect(status().isOk()
                );
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
