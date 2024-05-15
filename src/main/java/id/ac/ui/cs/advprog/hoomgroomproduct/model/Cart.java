package id.ac.ui.cs.advprog.hoomgroomproduct.model;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
@Entity
public class Cart {
    @Id
    private UUID userId;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private Map<UUID, CartItem> items;

    public Cart() {

    }

    public Cart(UUID userId) {
        this.userId = userId;
        this.items = new HashMap<>();
    }
}
