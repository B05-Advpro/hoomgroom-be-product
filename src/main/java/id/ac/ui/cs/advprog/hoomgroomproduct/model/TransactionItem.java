package id.ac.ui.cs.advprog.hoomgroomproduct.model;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
public class TransactionItem {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String productId;
    private String name;
    private double price;
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "transaction_id")
    private Transaction transaction;

    public TransactionItem() {

    }

    public TransactionItem(String productId, String name, double price, int quantity) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }
}
