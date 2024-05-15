package id.ac.ui.cs.advprog.hoomgroomproduct.model;

import java.util.Map;
import java.util.UUID;

public class TransactionBuilder {
    private Map<UUID, TransactionItem> products;
    private double totalPrice;
    private String promoCodeUsed;
    private UUID userId;
    private String deliveryMethod;

    public TransactionBuilder setProducts(Map<UUID, TransactionItem> products) {
        this.products = products;
        return this;
    }

    public TransactionBuilder setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
        return this;
    }

    public TransactionBuilder setPromoCodeUsed(String promoCodeUsed) {
        this.promoCodeUsed = promoCodeUsed;
        return this;
    }

    public TransactionBuilder setUserId(UUID userId) {
        this.userId = userId;
        return this;
    }

    public TransactionBuilder setDeliveryMethod(String deliveryMethod) {
        this.deliveryMethod = deliveryMethod;
        return this;
    }

    public Transaction build() {
        return new Transaction(products, totalPrice, promoCodeUsed, userId, deliveryMethod);
    }
}