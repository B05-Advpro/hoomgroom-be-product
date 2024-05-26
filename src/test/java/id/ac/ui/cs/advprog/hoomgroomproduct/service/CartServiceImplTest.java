package id.ac.ui.cs.advprog.hoomgroomproduct.service;

import id.ac.ui.cs.advprog.hoomgroomproduct.dto.CartDto;
import id.ac.ui.cs.advprog.hoomgroomproduct.dto.TopUpDto;
import id.ac.ui.cs.advprog.hoomgroomproduct.model.Cart;
import id.ac.ui.cs.advprog.hoomgroomproduct.model.CartItem;
import id.ac.ui.cs.advprog.hoomgroomproduct.repository.CartRepository;
import org.junit.jupiter.api.BeforeEach;
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

    String username = "anto";
    Cart cart;
    CartDto cartDto;

    @BeforeEach
    void setUp() {
        String productId = "ca1c1b7d-f5aa-4573-aeff-d01665cc88c8";
        String name = "Meja";
        double price = 25000;
        int quantity = 1;
        CartItem cartItem = new CartItem(productId, name, price, quantity);

        this.cart = new Cart(this.username);
        this.cart.getItems().add(cartItem);

        cartDto = new CartDto();
        cartDto.setUsername(this.username);
        cartDto.setProductId(productId);
        cartDto.setName(name);
        cartDto.setPrice(price);
        cartDto.setQuantity(quantity);
    }

    @Test
    void testGetNewCart() {
        when(cartRepository.findByUsername(this.username)).thenReturn(Optional.empty());
        when(cartRepository.save(any(Cart.class))).thenAnswer(i -> i.getArguments()[0]);

        Cart cart = cartService.getCart(this.username);

        assertNotNull(cart);
        assertEquals(this.username, cart.getUsername());
        verify(cartRepository, times(1)).findByUsername(this.username);
        verify(cartRepository, times(1)).save(any(Cart.class));
    }

    @Test
    void testGetExistingCart() {
        when(cartRepository.findByUsername(this.username)).thenReturn(Optional.of(this.cart));

        Cart cart = cartService.getCart(this.username);

        assertNotNull(cart);
        assertEquals(this.username, cart.getUsername());
        verify(cartRepository, times(1)).findByUsername(this.username);
        verify(cartRepository, times(0)).save(any(Cart.class));
    }

    @Test
    void testAddNewItemToCart() {
        this.cart.getItems().clear();

        when(cartRepository.findByUsername(this.username)).thenReturn((Optional.of(this.cart)));
        when(cartRepository.save(any(Cart.class))).thenAnswer(i -> i.getArguments()[0]);

        Cart savedCart = cartService.addItemToCart(this.cartDto);

        List<CartItem> items = savedCart.getItems();
        assertEquals(1, items.size());
        assertEquals(this.cartDto.getProductId(), items.get(0).getProductId());
        assertEquals(this.cartDto.getQuantity(), items.get(0).getQuantity());
        assertEquals(25000, savedCart.getTotalPrice());
        verify(cartRepository, times(1)).findByUsername(this.username);
        verify(cartRepository, times(1)).save(this.cart);
    }

    @Test
    void testAddExistingItemToCart() {
        int initialQuantity = this.cartDto.getQuantity();
        int newQuantity = 3;

        when(cartRepository.findByUsername(this.username)).thenReturn((Optional.of(this.cart)));
        when(cartRepository.save(any(Cart.class))).thenAnswer(i -> i.getArguments()[0]);

        this.cartDto.setQuantity(newQuantity);

        Cart savedCart = cartService.addItemToCart(this.cartDto);

        List<CartItem> items = savedCart.getItems();
        assertEquals(1, items.size());
        assertEquals(this.cartDto.getProductId(), items.get(0).getProductId());
        assertEquals(initialQuantity + newQuantity, items.get(0).getQuantity());
        assertEquals(100000, savedCart.getTotalPrice());
        verify(cartRepository, times(1)).findByUsername(username);
        verify(cartRepository, times(1)).save(cart);
    }

    @Test
    void testDeleteItemFromCart() {
        this.cart.setTotalPrice(25000);

        when(cartRepository.findByUsername(this.username)).thenReturn(Optional.of(this.cart));
        when(cartRepository.save(any(Cart.class))).thenAnswer(i -> i.getArguments()[0]);

        CartDto request = new CartDto();
        request.setUsername(this.username);
        request.setProductId("ca1c1b7d-f5aa-4573-aeff-d01665cc88c8");

        Cart savedCart = cartService.deleteItemFromCart(request);

        assertTrue(savedCart.getItems().isEmpty());
        assertEquals(0, savedCart.getTotalPrice());
        verify(cartRepository, times(1)).findByUsername(this.username);
        verify(cartRepository, times(1)).save(this.cart);
    }

    @Test
    void testClearShoppingCart() {
        this.cart.setTotalPrice(25000);

        when(cartRepository.findByUsername(this.username)).thenReturn(Optional.of(this.cart));
        when(cartRepository.save(any(Cart.class))).thenAnswer(i -> i.getArguments()[0]);

        Cart savedCart = cartService.clearCart(this.username);

        assertTrue(savedCart.getItems().isEmpty());
        assertEquals(0, savedCart.getTotalPrice());
        verify(cartRepository, times(1)).findByUsername(this.username);
        verify(cartRepository, times(1)).save(this.cart);
    }

    @Test
    void testTopUpWallet() {
        TopUpDto request = new TopUpDto();
        request.setUsername(this.username);
        request.setAmount(10000);

        when(cartRepository.findByUsername(this.username)).thenReturn(Optional.of(this.cart));
        when(cartRepository.save(any(Cart.class))).thenAnswer(i -> i.getArguments()[0]);

        Cart savedCart = cartService.topUpWallet(request);

        assertEquals(10000, savedCart.getWallet());
        verify(cartRepository, times(1)).findByUsername(this.username);
        verify(cartRepository, times(1)).save(this.cart);
    }
}
