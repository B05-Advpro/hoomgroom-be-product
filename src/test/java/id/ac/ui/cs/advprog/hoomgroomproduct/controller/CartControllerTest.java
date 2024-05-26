package id.ac.ui.cs.advprog.hoomgroomproduct.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import id.ac.ui.cs.advprog.hoomgroomproduct.dto.CartDto;
import id.ac.ui.cs.advprog.hoomgroomproduct.dto.TopUpDto;
import id.ac.ui.cs.advprog.hoomgroomproduct.model.Cart;
import id.ac.ui.cs.advprog.hoomgroomproduct.service.CartService;
import id.ac.ui.cs.advprog.hoomgroomproduct.service.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.mockito.Mockito.*;

@WebMvcTest(CartController.class)
class CartControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CartService cartService;

    @MockBean
    private JwtService jwtService;

    CartDto request;
    TopUpDto topUpRequest;

    @BeforeEach
    void setUp() {
        String username = "anto";
        String productId = "ca1c1b7d-f5aa-4573-aeff-d01665cc88c8";
        String name = "Meja";
        double price = 25000;
        int quantity = 1;

        this.request = new CartDto();
        this.request.setUsername(username);
        this.request.setProductId(productId);
        this.request.setName(name);
        this.request.setPrice(price);
        this.request.setQuantity(quantity);

        double amount = 50000;

        this.topUpRequest = new TopUpDto();
        this.topUpRequest.setUsername(username);
        this.topUpRequest.setAmount(amount);
    }

    @Test
    void testGetCartSuccess() throws Exception {
        String username = request.getUsername();
        Cart cart = new Cart(username);

        when(cartService.getCart(username)).thenReturn(cart);
        when(jwtService.isTokenValid(anyString())).thenReturn(true);
        when(jwtService.extractRole(anyString())).thenReturn("USER");

        mockMvc.perform(get("/cart/get/{username}", username)
                .header("Authorization", "Bearer jwtToken"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value(username))
                .andExpect(jsonPath("$.items").isEmpty());

        verify(cartService, times(1)).getCart(username);
        verify(jwtService, times(1)).isTokenValid(anyString());
        verify(jwtService, times(1)).extractRole(anyString());
    }

    @Test
    void testGetCartInvalidToken() throws Exception {
        String username = request.getUsername();
        Cart cart = new Cart(username);

        when(jwtService.isTokenValid(anyString())).thenReturn(false);

        mockMvc.perform(get("/cart/get/{username}", username)
                        .header("Authorization", "Bearer jwtToken"))
                .andExpect(status().isForbidden());

        verify(jwtService, times(1)).isTokenValid(anyString());
        verify(jwtService, times(0)).extractRole(anyString());
        verify(cartService, times(0)).getCart(username);
    }

    @Test
    void testGetCartInvalidRole() throws Exception {
        String username = request.getUsername();
        Cart cart = new Cart(username);

        when(jwtService.isTokenValid(anyString())).thenReturn(true);
        when(jwtService.extractRole(anyString())).thenReturn("ADMIN");

        mockMvc.perform(get("/cart/get/{username}", username)
                        .header("Authorization", "Bearer jwtToken"))
                .andExpect(status().isForbidden());

        verify(jwtService, times(1)).isTokenValid(anyString());
        verify(jwtService, times(1)).extractRole(anyString());
        verify(cartService, times(0)).getCart(username);
    }

    @Test
    void testAddItemToCartSuccess() throws Exception {
        when(jwtService.isTokenValid(anyString())).thenReturn(true);
        when(jwtService.extractRole(anyString())).thenReturn("USER");

        mockMvc.perform(post("/cart/add-items")
                .header("Authorization", "Bearer jwtToken")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(this.request)))
                .andExpect(status().isOk());

        verify(cartService, times(1)).addItemToCart(any(CartDto.class));
        verify(jwtService, times(1)).isTokenValid(anyString());
        verify(jwtService, times(1)).extractRole(anyString());
    }

    @Test
    void testAddItemToCartInvalidToken() throws Exception {
        when(jwtService.isTokenValid(anyString())).thenReturn(false);

        mockMvc.perform(post("/cart/add-items")
                .header("Authorization", "Bearer jwtToken")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(this.request)))
                .andExpect(status().isForbidden());

        verify(jwtService, times(1)).isTokenValid(anyString());
        verify(jwtService, times(0)).extractRole(anyString());
        verify(cartService, times(0)).addItemToCart(any(CartDto.class));
    }

    @Test
    void testAddItemToCartInvalidRole() throws Exception {
        when(jwtService.isTokenValid(anyString())).thenReturn(true);
        when(jwtService.extractRole(anyString())).thenReturn("ADMIN");

        mockMvc.perform(post("/cart/add-items")
                .header("Authorization", "Bearer jwtToken")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(this.request)))
                .andExpect(status().isForbidden());

        verify(jwtService, times(1)).isTokenValid(anyString());
        verify(jwtService, times(1)).extractRole(anyString());
        verify(cartService, times(0)).addItemToCart(any(CartDto.class));
    }

    @Test
    void testDeleteItemFromCartSuccess() throws Exception {
        when(jwtService.isTokenValid(anyString())).thenReturn(true);
        when(jwtService.extractRole(anyString())).thenReturn("USER");

        mockMvc.perform(delete("/cart/delete-items")
                .header("Authorization", "Bearer jwtToken")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(this.request)))
                .andExpect(status().isOk());

        verify(jwtService, times(1)).isTokenValid(anyString());
        verify(jwtService, times(1)).extractRole(anyString());
        verify(cartService, times(1)).deleteItemFromCart(any(CartDto.class));
    }

    @Test
    void testDeleteItemFromCartInvalidToken() throws Exception {
        when(jwtService.isTokenValid(anyString())).thenReturn(false);

        mockMvc.perform(delete("/cart/delete-items")
                .header("Authorization", "Bearer jwtToken")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(this.request)))
                .andExpect(status().isForbidden());

        verify(jwtService, times(1)).isTokenValid(anyString());
        verify(jwtService, times(0)).extractRole(anyString());
        verify(cartService, times(0)).deleteItemFromCart(any(CartDto.class));
    }

    @Test
    void testDeleteItemFromCartInvalidRole() throws Exception {
        when(jwtService.isTokenValid(anyString())).thenReturn(true);
        when(jwtService.extractRole(anyString())).thenReturn("ADMIN");

        mockMvc.perform(delete("/cart/delete-items")
                .header("Authorization", "Bearer jwtToken")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(this.request))).
                andExpect(status().isForbidden());

        verify(jwtService, times(1)).isTokenValid(anyString());
        verify(jwtService, times(1)).extractRole(anyString());
        verify(cartService, times(0)).deleteItemFromCart(any(CartDto.class));
    }

    @Test
    void testClearCartSuccess() throws Exception {
        when(jwtService.isTokenValid(anyString())).thenReturn(true);
        when(jwtService.extractRole(anyString())).thenReturn("USER");

        mockMvc.perform(post("/cart/clear-cart/{username}", request.getUsername())
                .header("Authorization", "Bearer jwtToken"))
                .andExpect(status().isOk());

        verify(jwtService, times(1)).isTokenValid(anyString());
        verify(jwtService, times(1)).extractRole(anyString());
        verify(cartService, times(1)).clearCart(request.getUsername());
    }

    @Test
    void testClearCartInvalidToken() throws Exception {
        when(jwtService.isTokenValid(anyString())).thenReturn(false);

        mockMvc.perform(post("/cart/clear-cart/{username}", request.getUsername())
                .header("Authorization", "Bearer jwtToken"))
                .andExpect(status().isForbidden());

        verify(jwtService, times(1)).isTokenValid(anyString());
        verify(jwtService, times(0)).extractRole(anyString());
        verify(cartService, times(0)).clearCart(request.getUsername());
    }

    @Test
    void testClearCartInvalidRole() throws Exception {
        when(jwtService.isTokenValid(anyString())).thenReturn(true);
        when(jwtService.extractRole(anyString())).thenReturn("ADMIN");

        mockMvc.perform(post("/cart/clear-cart/{username}", request.getUsername())
                .header("Authorization", "Bearer jwtToken"))
                .andExpect(status().isForbidden());

        verify(jwtService, times(1)).isTokenValid(anyString());
        verify(jwtService, times(1)).extractRole(anyString());
        verify(cartService, times(0)).clearCart(request.getUsername());
    }

    @Test
    void testTopUpSuccess() throws Exception {
        when(jwtService.isTokenValid(anyString())).thenReturn(true);
        when(jwtService.extractRole(anyString())).thenReturn("USER");

        mockMvc.perform(post("/cart/topup")
                .header("Authorization", "Bearer jwtToken")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(topUpRequest)))
                .andExpect(status().isOk());

        verify(jwtService, times(1)).isTokenValid(anyString());
        verify(jwtService, times(1)).extractRole(anyString());
        verify(cartService, times(1)).topUpWallet(any(TopUpDto.class));
    }

    @Test
    void testTopUpInvalidToken() throws Exception {
        when(jwtService.isTokenValid(anyString())).thenReturn(false);

        mockMvc.perform(post("/cart/topup")
                .header("Authorization", "Bearer jwtToken")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(topUpRequest)))
                .andExpect(status().isForbidden());

        verify(jwtService, times(1)).isTokenValid(anyString());
        verify(jwtService, times(0)).extractRole(anyString());
        verify(cartService, times(0)).topUpWallet(any(TopUpDto.class));
    }

    @Test
    void testTopUpInvalidRole() throws Exception {
        when(jwtService.isTokenValid(anyString())).thenReturn(true);
        when(jwtService.extractRole(anyString())).thenReturn("ADMIN");

        mockMvc.perform(post("/cart/topup")
                .header("Authorization", "Bearer jwtToken")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(topUpRequest)))
                .andExpect(status().isForbidden());

        verify(jwtService, times(1)).isTokenValid(anyString());
        verify(jwtService, times(1)).extractRole(anyString());
        verify(cartService, times(0)).topUpWallet(any(TopUpDto.class));
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
