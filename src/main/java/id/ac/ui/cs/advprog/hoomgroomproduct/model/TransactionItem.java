package id.ac.ui.cs.advprog.hoomgroomproduct.model;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
public class TransactionItem {
    private final UUID id;
    private final String name;
    private final double price;
    private final int quantity;

    public TransactionItem(UUID id, String name, double price, int quantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }
}
