package id.ac.ui.cs.advprog.hoomgroomproduct.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import id.ac.ui.cs.advprog.hoomgroomproduct.dto.CartDto;
import id.ac.ui.cs.advprog.hoomgroomproduct.service.CartService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.mockito.Mockito.*;

@WebMvcTest(CartController.class)
class CartControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CartService cartService;

    CartDto request;

    @BeforeEach
    void setUp() {
        Long userId = 1L;
        String productId = "ca1c1b7d-f5aa-4573-aeff-d01665cc88c8";
        String name = "Meja";
        double price = 25000;
        int quantity = 1;

        this.request = new CartDto();
        this.request.setUserId(userId);
        this.request.setProductId(productId);
        this.request.setName(name);
        this.request.setPrice(price);
        this.request.setQuantity(quantity);
    }

    @Test
    void testAddItemToCart() throws Exception {
        mockMvc.perform(post("/cart/add-items")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(this.request)))
                .andExpect(status().isOk());

        verify(cartService, times(1)).addItemToCart(any(CartDto.class));
    }

    @Test
    void testDeleteItemFromCart() throws Exception {
        mockMvc.perform(delete("/cart/delete-items")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(this.request)))
                .andExpect(status().isOk());

        verify(cartService, times(1)).deleteItemFromCart(any(CartDto.class));
    }

    @Test
    void testClearCart() throws Exception {
        Long userId = request.getUserId();
        mockMvc.perform(post("/cart/clear-cart/{userId}", userId))
                .andExpect(status().isOk());

        verify(cartService, times(1)).clearCart(userId);
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
