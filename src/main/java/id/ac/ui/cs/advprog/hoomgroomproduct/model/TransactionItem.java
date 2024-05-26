package id.ac.ui.cs.advprog.hoomgroomproduct.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Entity
@NoArgsConstructor
public class TransactionItem {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String productId;
    private String name;
    private double price;
    private int quantity;

    @Setter
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private Transaction transaction;

    public TransactionItem(String productId, String name, double price, int quantity) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }
}
