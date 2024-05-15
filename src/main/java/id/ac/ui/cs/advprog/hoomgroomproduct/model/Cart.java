package id.ac.ui.cs.advprog.hoomgroomproduct.model;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
public class Cart {
    private UUID userId;

    private List<CartItem> items;

    public Cart(UUID userId) {
        this.userId = userId;
        this.items = new ArrayList<>();
    }
}
