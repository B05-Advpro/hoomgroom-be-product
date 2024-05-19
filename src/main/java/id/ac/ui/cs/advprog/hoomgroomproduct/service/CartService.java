package id.ac.ui.cs.advprog.hoomgroomproduct.service;

import id.ac.ui.cs.advprog.hoomgroomproduct.dto.CartDto;
import id.ac.ui.cs.advprog.hoomgroomproduct.dto.TopUpDto;
import id.ac.ui.cs.advprog.hoomgroomproduct.model.Cart;

import java.util.UUID;

public interface CartService {
    public Cart getCart(Long userId);
    public Cart addItemToCart(CartDto request);
    public Cart deleteItemFromCart(CartDto request);
    public Cart clearCart(Long userId);
    public Cart topUpWallet(Long userId, double amount);
}
