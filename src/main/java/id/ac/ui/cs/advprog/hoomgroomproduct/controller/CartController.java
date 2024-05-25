package id.ac.ui.cs.advprog.hoomgroomproduct.controller;

import id.ac.ui.cs.advprog.hoomgroomproduct.dto.CartDto;
import id.ac.ui.cs.advprog.hoomgroomproduct.dto.TopUpDto;
import id.ac.ui.cs.advprog.hoomgroomproduct.model.Cart;
import id.ac.ui.cs.advprog.hoomgroomproduct.service.CartService;
import id.ac.ui.cs.advprog.hoomgroomproduct.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    CartService cartService;

    @Autowired
    JwtService jwtService;

    @GetMapping("/get/{userId}")
    public ResponseEntity<Cart> getCart(@RequestHeader(value = "Authorization") String token, @PathVariable Long userId) {
        token = token.substring(7);
        if (!jwtService.isTokenValid(token) || !jwtService.extractRole(token).equals("USER")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
        Cart cart = cartService.getCart(userId);
        return ResponseEntity.ok(cart);
    }

    @PostMapping("/add-items")
    public ResponseEntity<String> addItemToCart(@RequestHeader(value = "Authorization") String token, @RequestBody CartDto request) {
        token = token.substring(7);
        if (!jwtService.isTokenValid(token) || !jwtService.extractRole(token).equals("USER")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
        cartService.addItemToCart(request);
        return ResponseEntity.ok("Items added to cart succesfully");
    }

    @DeleteMapping("/delete-items")
    public ResponseEntity<String> deleteItemFromCart(@RequestHeader(value = "Authorization") String token, @RequestBody CartDto request) {
        token = token.substring(7);
        if (!jwtService.isTokenValid(token) || !jwtService.extractRole(token).equals("USER")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
        cartService.deleteItemFromCart(request);
        return ResponseEntity.ok("Items deleted from cart succesfully");
    }

    @PostMapping("/clear-cart/{userId}")
    public ResponseEntity<String> clearCart(@RequestHeader(value = "Authorization") String token, @PathVariable Long userId) {
        token = token.substring(7);
        if (!jwtService.isTokenValid(token) || !jwtService.extractRole(token).equals("USER")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
        cartService.clearCart(userId);
        return ResponseEntity.ok("Cart cleared successfully");
    }

    @PostMapping("/topup")
    public ResponseEntity<String> topUpWallet(@RequestHeader(value = "Authorization") String token, @RequestBody TopUpDto request) {
        token = token.substring(7);
        if (!jwtService.isTokenValid(token) || !jwtService.extractRole(token).equals("USER")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
        cartService.topUpWallet(request);
        return ResponseEntity.ok("Wallet updated successfully");
    }
}
