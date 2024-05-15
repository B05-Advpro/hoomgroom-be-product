package id.ac.ui.cs.advprog.hoomgroomproduct.model;

import java.util.List;
import java.util.UUID;

public class TransactionBuilder {
    private List<TransactionItem> products;
    private double totalPrice;
    private String promoCodeUsed;
    private UUID pembeli;
    private String deliveryMethod;

    public TransactionBuilder setProducts(List<TransactionItem> products) {
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

    public TransactionBuilder setPembeli(UUID pembeli) {
        this.pembeli = pembeli;
        return this;
    }

    public TransactionBuilder setDeliveryMethod(String deliveryMethod) {
        this.deliveryMethod = deliveryMethod;
        return this;
    }

    public Transaction build() {
        return new Transaction(products, totalPrice, promoCodeUsed, pembeli, deliveryMethod);
    }
}