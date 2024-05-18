package id.ac.ui.cs.advprog.hoomgroomproduct.service;

import id.ac.ui.cs.advprog.hoomgroomproduct.model.Cart;

import java.util.UUID;

public interface CartService {
    public Cart getCart(Long userId);
    public void addItemToCart(Long userId, String productId, String name, double price, int quantity);
    public void deleteItemFromCart(Long userId, String productId);
    public void clearCart(Long userId);
}
