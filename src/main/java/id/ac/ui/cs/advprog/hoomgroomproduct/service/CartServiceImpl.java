package id.ac.ui.cs.advprog.hoomgroomproduct.service;

import id.ac.ui.cs.advprog.hoomgroomproduct.dto.CartDto;
import id.ac.ui.cs.advprog.hoomgroomproduct.dto.TopUpDto;
import id.ac.ui.cs.advprog.hoomgroomproduct.model.Cart;
import id.ac.ui.cs.advprog.hoomgroomproduct.model.CartItem;
import id.ac.ui.cs.advprog.hoomgroomproduct.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private CartRepository cartRepository;

    public Cart getCart(Long userId) {
        return cartRepository.findByUserId(userId)
                .orElseGet(() -> {
                    Cart cart = new Cart(userId);
                    return cartRepository.save(cart);
                });
    }

    public Cart addItemToCart(CartDto request) {
        Long userId = request.getUserId();
        String productId = request.getProductId();
        String name = request.getName();
        double price = request.getPrice();
        int quantity = request.getQuantity();

        Cart cart = getCart(userId);
        List<CartItem> cartItems = cart.getItems();
        CartItem savedItem = null;
        for(CartItem item : cartItems) {
            if (item.getProductId().equals(productId)) {
                savedItem = item;
            }
        }

        if (savedItem == null) {
            savedItem = new CartItem(productId, name, price, quantity);
            savedItem.setCart(cart);
            cart.getItems().add(savedItem);
        } else {
            quantity = savedItem.getQuantity() + quantity;
            savedItem.setQuantity(quantity);
        }

        cart.setTotalPrice(cart.getTotalPrice() + price * quantity);
        return cartRepository.save(cart);
    }

    public Cart deleteItemFromCart(CartDto request) {
        Long userId = request.getUserId();
        String productId = request.getProductId();

        Cart cart = getCart(userId);
        Iterator<CartItem> iterator = cart.getItems().iterator();
        while(iterator.hasNext()) {
            CartItem item = iterator.next();
            if (item.getProductId().equals(productId)) {
                cart.setTotalPrice(cart.getTotalPrice() - (item.getPrice() * item.getQuantity()));
                iterator.remove();
            }
        }
        return cartRepository.save(cart);
    }

    public Cart clearCart(Long userId) {
        Cart cart = getCart(userId);
        cart.getItems().clear();
        cart.setTotalPrice(0);
        return cartRepository.save(cart);
    }

    public Cart topUpWallet(TopUpDto request) {
        Long userId = request.getUserId();
        double amount = request.getAmount();
        Cart cart = getCart(userId);
        cart.setWallet(cart.getWallet() + amount);
        return cartRepository.save(cart);
    }
}
