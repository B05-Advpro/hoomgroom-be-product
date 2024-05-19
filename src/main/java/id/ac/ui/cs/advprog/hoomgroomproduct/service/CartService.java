package id.ac.ui.cs.advprog.hoomgroomproduct.service;

import id.ac.ui.cs.advprog.hoomgroomproduct.dto.CartDto;
import id.ac.ui.cs.advprog.hoomgroomproduct.model.Cart;

import java.util.UUID;

public interface CartService {
    public Cart getCart(Long userId);
    public void addItemToCart(CartDto request);
    public void deleteItemFromCart(CartDto request);
    public void clearCart(Long userId);
}
