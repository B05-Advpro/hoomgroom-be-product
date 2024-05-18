package id.ac.ui.cs.advprog.hoomgroomproduct.controller;

import id.ac.ui.cs.advprog.hoomgroomproduct.dto.CartDto;
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

    @PostMapping("/add-items")
    public ResponseEntity<String> addItemToCart(@RequestBody CartDto request) {
        try {
            cartService.addItemToCart(request);
            return ResponseEntity.ok("Items added to cart succesfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/delete-items")
    public ResponseEntity<String> deleteItemFromCart(@RequestBody CartDto request) {
        try {
            cartService.deleteItemFromCart(request);
            return ResponseEntity.ok("Items deleted from cart succesfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/clear-cart")
    public ResponseEntity<String> clearCart(@RequestBody CartDto request) {
        try {
            cartService.clearCart(request);
            return ResponseEntity.ok("Cart cleared successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
