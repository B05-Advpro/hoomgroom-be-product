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
@CrossOrigin
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;
    private final JwtService jwtService;

    public CartController(CartService cartService, JwtService jwtService) {
        this.cartService = cartService;
        this.jwtService = jwtService;
    }

    @GetMapping("/get/{username}")
    public ResponseEntity<Cart> getCart(@RequestHeader(value = "Authorization") String token, @PathVariable String username) {
        token = token.substring(7);
        if (!jwtService.isTokenValid(token) || !jwtService.extractRole(token).equals("USER")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
        Cart cart = cartService.getCart(username);
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

    @PostMapping("/clear-cart/{username}")
    public ResponseEntity<String> clearCart(@RequestHeader(value = "Authorization") String token, @PathVariable String username) {
        token = token.substring(7);
        if (!jwtService.isTokenValid(token) || !jwtService.extractRole(token).equals("USER")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
        cartService.clearCart(username);
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
