package id.ac.ui.cs.advprog.hoomgroomproduct.model;

import id.ac.ui.cs.advprog.hoomgroomproduct.enums.DeliveryMethod;
import id.ac.ui.cs.advprog.hoomgroomproduct.enums.DeliveryStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @OneToMany(mappedBy = "transaction", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TransactionItem> items;
    private LocalDateTime createdAt;
    private double totalPrice;
    private String promoCodeUsed;
    private Long userId;
    private String deliveryStatus;
    private String deliveryCode;
    private String deliveryMethod;

    public Transaction(List<TransactionItem> items, double totalPrice, String promoCodeUsed, Long userId, String deliveryMethod) {
        this.items = items;
        this.createdAt = LocalDateTime.now();
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