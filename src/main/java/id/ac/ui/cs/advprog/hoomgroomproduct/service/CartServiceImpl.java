package id.ac.ui.cs.advprog.hoomgroomproduct.service;

import id.ac.ui.cs.advprog.hoomgroomproduct.model.Cart;
import id.ac.ui.cs.advprog.hoomgroomproduct.model.CartItem;
import id.ac.ui.cs.advprog.hoomgroomproduct.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public void addItemToCart(Long userId, String productId, String name, double price, int quantity) {
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
            savedItem.setQuantity(savedItem.getQuantity() + quantity);
        }

        cartRepository.save(cart);
    }

    public void deleteItemFromCart(Long userId, String productId) {
        Cart cart = getCart(userId);
        cart.getItems().removeIf(item -> item.getProductId().equals(productId));
        cartRepository.save(cart);
    }

    public void clearCart(Long userId) {
        Cart cart = getCart(userId);
        cart.getItems().clear();
        cartRepository.save(cart);
    }
}
