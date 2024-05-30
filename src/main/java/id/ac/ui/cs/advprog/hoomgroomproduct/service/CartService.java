package id.ac.ui.cs.advprog.hoomgroomproduct.service;

import id.ac.ui.cs.advprog.hoomgroomproduct.dto.CartDto;
import id.ac.ui.cs.advprog.hoomgroomproduct.dto.TopUpDto;
import id.ac.ui.cs.advprog.hoomgroomproduct.model.Cart;

public interface CartService {
    Cart getCart(String username);
    Cart addItemToCart(CartDto request);
    Cart deleteItemFromCart(CartDto request);
    Cart clearCart(String username);
    Cart topUpWallet(TopUpDto request);
}
