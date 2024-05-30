package id.ac.ui.cs.advprog.hoomgroomproduct.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;

@Getter
@Entity
@NoArgsConstructor
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String username;

    @Setter
    private double wallet;

    @Setter
    private double totalPrice;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItem> items;

    public Cart(String username) {
        this.username = username;
        this.items = new ArrayList<>();
        this.wallet = 0;
        this.totalPrice = 0;
    }
}
