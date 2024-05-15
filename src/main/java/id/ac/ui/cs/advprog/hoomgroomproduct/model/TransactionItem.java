package id.ac.ui.cs.advprog.hoomgroomproduct.model;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.UUID;

@Getter
@Entity
public class TransactionItem {
    @Id
    @GeneratedValue
    private UUID productId;

    private String name;
    private double price;
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Transaction transaction;

    public TransactionItem() {

    }

    public TransactionItem(UUID productId, String name, double price, int quantity) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }
}
