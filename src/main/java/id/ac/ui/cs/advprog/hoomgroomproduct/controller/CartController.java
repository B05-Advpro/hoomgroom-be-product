package id.ac.ui.cs.advprog.hoomgroomproduct.controller;

import id.ac.ui.cs.advprog.hoomgroomproduct.dto.CartDto;
import id.ac.ui.cs.advprog.hoomgroomproduct.dto.TopUpDto;
import id.ac.ui.cs.advprog.hoomgroomproduct.model.Cart;
import id.ac.ui.cs.advprog.hoomgroomproduct.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    CartService cartService;

    @GetMapping("/{userId}")
    public ResponseEntity<Cart> getCart(@PathVariable Long userId) {
        Cart cart = cartService.getCart(userId);
        return ResponseEntity.ok(cart);
    }

    @PostMapping("/add-items")
    public ResponseEntity<String> addItemToCart(@RequestBody CartDto request) {
        cartService.addItemToCart(request);
        return ResponseEntity.ok("Items added to cart succesfully");
    }

    @DeleteMapping("/delete-items")
    public ResponseEntity<String> deleteItemFromCart(@RequestBody CartDto request) {
        cartService.deleteItemFromCart(request);
        return ResponseEntity.ok("Items deleted from cart succesfully");
    }

    @PostMapping("/clear-cart/{userId}")
    public ResponseEntity<String> clearCart(@PathVariable Long userId) {
        cartService.clearCart(userId);
        return ResponseEntity.ok("Cart cleared successfully");
    }

    @PostMapping("/topup")
    public ResponseEntity<String> topUpWallet(@RequestBody TopUpDto request) {
        cartService.topUpWallet(request);
        return ResponseEntity.ok("Wallet updated successfully");
    }
}
