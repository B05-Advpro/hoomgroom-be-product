package id.ac.ui.cs.advprog.hoomgroomproduct.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Entity
@NoArgsConstructor
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String productId;
    private String name;
    private double price;

    @Setter
    private int quantity;

    @Setter
    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    public CartItem(String productId, String name, double price, int quantity) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }
}
