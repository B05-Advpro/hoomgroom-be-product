package id.ac.ui.cs.advprog.hoomgroomproduct.model;

import java.util.List;

public class TransactionBuilder {
    private List<TransactionItem> items;
    private double totalPrice;
    private String promoCodeUsed;
    private Long userId;
    private String deliveryMethod;

    public TransactionBuilder setItems(List<TransactionItem> items) {
        this.items = items;
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

    public TransactionBuilder setUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    public TransactionBuilder setDeliveryMethod(String deliveryMethod) {
        this.deliveryMethod = deliveryMethod;
        return this;
    }

    public Transaction build() {
        return new Transaction(items, totalPrice, promoCodeUsed, userId, deliveryMethod);
    }
}