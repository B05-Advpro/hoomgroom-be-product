package id.ac.ui.cs.advprog.hoomgroomproduct.model;

import id.ac.ui.cs.advprog.hoomgroomproduct.enums.DeliveryMethod;
import id.ac.ui.cs.advprog.hoomgroomproduct.model.states.TransactionMenungguVerifikasi;
import id.ac.ui.cs.advprog.hoomgroomproduct.model.states.TransactionStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
public class Transaction {

    @Id
    @GeneratedValue
    private UUID id;

    @Transient
    private List<TransactionItem> products;
    private double totalPrice;
    private String promoCodeUsed;
    private UUID pembeli;
    private String deliveryCode;
    private String deliveryMethod;
    private TransactionStatus transactionStatus;

    public Transaction() {

    }

    public Transaction(List<TransactionItem> products, double totalPrice,
                       String promoCodeUsed, UUID pembeli, String deliveryMethod) {
        this.id = UUID.randomUUID();

        if (products.isEmpty()) {
            throw new IllegalArgumentException();
        } else {
            this.products = products;
        }

        this.totalPrice = totalPrice;
        this.promoCodeUsed = promoCodeUsed;
        this.pembeli = pembeli;
        this.deliveryCode = "";
        this.setDeliveryMethod(deliveryMethod);
        this.transactionStatus = new TransactionMenungguVerifikasi();
    }

    public void setDeliveryMethod(String method) {
        if (DeliveryMethod.contains(method)) {
            this.deliveryMethod = method;
        } else {
            throw new IllegalArgumentException();
        }
    }
}