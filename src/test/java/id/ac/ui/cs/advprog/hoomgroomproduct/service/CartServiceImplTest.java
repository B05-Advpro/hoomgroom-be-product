package id.ac.ui.cs.advprog.hoomgroomproduct.service;

import id.ac.ui.cs.advprog.hoomgroomproduct.dto.CartDto;
import id.ac.ui.cs.advprog.hoomgroomproduct.model.Cart;
import id.ac.ui.cs.advprog.hoomgroomproduct.model.CartItem;
import id.ac.ui.cs.advprog.hoomgroomproduct.repository.CartRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CartServiceImplTest {

    @Mock
    CartRepository cartRepository;

    @InjectMocks
    CartServiceImpl cartService;

    @Test
    void testGetNewCart() {
        Long userId = 1L;

        when(cartRepository.findByUserId(userId)).thenReturn(Optional.empty());
        when(cartRepository.save(any(Cart.class))).thenAnswer(i -> i.getArguments()[0]);

        Cart cart = cartService.getCart(userId);
        assertNotNull(cart);
        assertEquals(userId, cart.getUserId());
        verify(cartRepository, times(1)).findByUserId(userId);
        verify(cartRepository, times(1)).save(any(Cart.class));
    }

    @Test
    void testGetExistingCart() {
        Long userId = 1L;
        Cart existingCart = new Cart(userId);

        when(cartRepository.findByUserId(userId)).thenReturn(Optional.of(existingCart));

        Cart cart = cartService.getCart(userId);

        assertNotNull(cart);
        assertEquals(userId, cart.getUserId());
        verify(cartRepository, times(1)).findByUserId(userId);
        verify(cartRepository, times(0)).save(any(Cart.class));
    }

    @Test
    void testAddNewItemToCart() {
        Long userId = 1L;
        String productId = "ca1c1b7d-f5aa-4573-aeff-d01665cc88c8";
        String name = "Meja";
        double price = 25000;
        int quantity = 1;
        Cart cart = new Cart(userId);

        when(cartRepository.findByUserId(userId)).thenReturn((Optional.of(cart)));
        when(cartRepository.save(any(Cart.class))).thenAnswer(i -> i.getArguments()[0]);

        CartDto request = new CartDto();
        request.setUserId(userId);
        request.setProductId(productId);
        request.setName(name);
        request.setPrice(price);
        request.setQuantity(quantity);

        cartService.addItemToCart(request);

        List<CartItem> items = cart.getItems();
        assertEquals(1, items.size());
        assertEquals(productId, items.get(0).getProductId());
        assertEquals(quantity, items.get(0).getQuantity());
        verify(cartRepository, times(1)).findByUserId(userId);
        verify(cartRepository, times(1)).save(cart);
    }

    @Test
    void testAddExistingItemToCart() {
        Long userId = 1L;
        String productId = "ca1c1b7d-f5aa-4573-aeff-d01665cc88c8";
        String name = "Meja";
        double price = 25000;
        int initialQuantity = 1;
        int newQuantity = 3;
        Cart cart = new Cart(userId);
        CartItem cartIem = new CartItem(productId, name, price, initialQuantity);
        cart.getItems().add(cartIem);

        when(cartRepository.findByUserId(userId)).thenReturn((Optional.of(cart)));
        when(cartRepository.save(any(Cart.class))).thenAnswer(i -> i.getArguments()[0]);

        CartDto request = new CartDto();
        request.setUserId(userId);
        request.setProductId(productId);
        request.setName(name);
        request.setPrice(price);
        request.setQuantity(newQuantity);

        cartService.addItemToCart(request);

        List<CartItem> items = cart.getItems();
        assertEquals(1, items.size());
        assertEquals(productId, items.get(0).getProductId());
        assertEquals(initialQuantity + newQuantity, items.get(0).getQuantity());
        verify(cartRepository, times(1)).findByUserId(userId);
        verify(cartRepository, times(1)).save(cart);
    }

    @Test
    void testDeleteItemFromCart() {
        Long userId = 1L;
        String productId1 = "ca1c1b7d-f5aa-4573-aeff-d01665cc88c8";
        String name1 = "Meja";
        double price1 = 25000;
        int quantity1 = 1;
        CartItem cartIem1 = new CartItem(productId1, name1, price1, quantity1);

        String productId2 = "df6c1b7d-f5aa-4573-aeff-d01665cc88c8";
        String name2 = "Kursi";
        double price2 = 10000;
        int quantity2 = 2;
        CartItem cartIem2 = new CartItem(productId2, name2, price2, quantity2);

        Cart cart = new Cart(userId);
        cart.getItems().add(cartIem1);
        cart.getItems().add(cartIem2);

        when(cartRepository.findByUserId(userId)).thenReturn(Optional.of(cart));
        when(cartRepository.save(any(Cart.class))).thenAnswer(i -> i.getArguments()[0]);

        CartDto request = new CartDto();
        request.setUserId(userId);
        request.setProductId(productId1);

        cartService.deleteItemFromCart(request);

        assertEquals(1, cart.getItems().size());
        verify(cartRepository, times(1)).findByUserId(userId);
        verify(cartRepository, times(1)).save(cart);
    }

    @Test
    void testClearShoppingCart() {
        Long userId = 1L;
        String productId = "ca1c1b7d-f5aa-4573-aeff-d01665cc88c8";
        String name = "Meja";
        double price = 25000;
        int quantity = 1;
        Cart cart = new Cart(userId);
        CartItem cartItem = new CartItem(productId, name, price, quantity);
        cart.getItems().add(cartItem);

        when(cartRepository.findByUserId(userId)).thenReturn(Optional.of(cart));
        when(cartRepository.save(any(Cart.class))).thenAnswer(i -> i.getArguments()[0]);

        CartDto request = new CartDto();
        request.setUserId(userId);

        cartService.clearCart(request);

        assertTrue(cart.getItems().isEmpty());
        verify(cartRepository, times(1)).findByUserId(userId);
        verify(cartRepository, times(1)).save(cart);
    }
}
