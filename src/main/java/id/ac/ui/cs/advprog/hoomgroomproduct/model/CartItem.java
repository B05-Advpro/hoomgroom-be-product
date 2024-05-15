package id.ac.ui.cs.advprog.hoomgroomproduct.model;

import lombok.Getter;

import java.util.UUID;

@Getter
public class CartItem {
    private UUID productId;
    private String name;
    private double price;
    private int quantity;

    public CartItem(UUID productId, String name, double price, int quantity) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }
}
