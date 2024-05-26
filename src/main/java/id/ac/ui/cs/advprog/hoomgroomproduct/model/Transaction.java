package id.ac.ui.cs.advprog.hoomgroomproduct.model;

import id.ac.ui.cs.advprog.hoomgroomproduct.enums.DeliveryMethod;
import id.ac.ui.cs.advprog.hoomgroomproduct.enums.DeliveryStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Setter
    @OneToMany(mappedBy = "transaction", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TransactionItem> items;

    @Setter
    private double totalPrice;
    private LocalDateTime createdAt;
    private String promoCodeUsed;
    private String username;
    private String deliveryStatus;
    private String deliveryCode;
    private String deliveryMethod;

    public Transaction(String username, String promoCodeUsed, String deliveryMethod, List<TransactionItem> items, double totalPrice) {
        this.items = items;
        this.createdAt = LocalDateTime.now();
        this.totalPrice = totalPrice;
        this.username = username;
        this.promoCodeUsed = promoCodeUsed;
        this.deliveryStatus = DeliveryStatus.MENUNGGU_VERIFIKASI.getValue();
        this.deliveryCode = "";
        this.setDeliveryMethod(deliveryMethod);
    }
    public Transaction(String username, String promoCodeUsed, String deliveryMethod) {
        this(username, promoCodeUsed, deliveryMethod, new ArrayList<>(), 0);
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