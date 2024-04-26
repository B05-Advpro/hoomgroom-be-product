package id.ac.ui.cs.advprog.hoomgroomproduct.model;

import java.util.Map;
import java.util.UUID;

public class TransactionBuilder {
    private Map<UUID, Integer> products;
    private String promoCodeUsed;
    private UUID pembeli;
    private String deliveryMethod;

    public TransactionBuilder setProducts(Map<UUID, Integer> products) {
        this.products = products;
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
        return new Transaction(products, promoCodeUsed, pembeli, deliveryMethod);
    }
}