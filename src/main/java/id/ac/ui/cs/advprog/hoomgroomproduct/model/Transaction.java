package id.ac.ui.cs.advprog.hoomgroomproduct.model;

import id.ac.ui.cs.advprog.hoomgroomproduct.enums.DeliveryMethod;
import id.ac.ui.cs.advprog.hoomgroomproduct.enums.DeliveryStatus;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.Map;
import java.util.UUID;

@Getter
@Entity
public class Transaction {
    @Id
    @GeneratedValue
    private UUID id;

    @OneToMany(mappedBy = "transaction", cascade = CascadeType.ALL)
    @MapKey(name = "productId")
    private Map<UUID, TransactionItem> products;
    private double totalPrice;
    private String promoCodeUsed;
    private UUID userId;
    private String deliveryStatus;
    private String deliveryCode;
    private String deliveryMethod;

    public Transaction() {}

    public Transaction(Map<UUID, TransactionItem> products, double totalPrice, String promoCodeUsed, UUID userId, String deliveryMethod) {
        this.id = UUID.randomUUID();

        if (products.isEmpty()) {
            throw new IllegalArgumentException();
        } else {
            this.products = products;
        }
        this.totalPrice = totalPrice;
        this.promoCodeUsed = promoCodeUsed;
        this.userId = userId;
        this.deliveryStatus = DeliveryStatus.MENUNGGU_VERIFIKASI.getValue();
        this.deliveryCode = "";
        this.setDeliveryMethod(deliveryMethod);
    }

    public void setDeliveryStatus(String status) {
        if (DeliveryStatus.contains(status)) {
            this.deliveryStatus = status;
        } else {
            throw new IllegalArgumentException();
        }
    }

    public void setDeliveryMethod(String method) {
        if (DeliveryMethod.contains(method)) {
            this.deliveryMethod = method;
        } else {
            throw new IllegalArgumentException();
        }
    }
}