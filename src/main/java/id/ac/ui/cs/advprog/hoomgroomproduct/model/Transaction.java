package id.ac.ui.cs.advprog.hoomgroomproduct.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
public class Transaction {
    private UUID id;
    private Map<UUID, Integer> products;
    private double totalPrice;
    private String promoCodeUsed;
    private UUID pembeli;
    private String deliveryStatus;
    private String deliveryCode;
    private String deliveryMethod;

    public Transaction(Map<UUID, Integer> products, String promoCodeUsed, UUID pembeli, String deliveryMethod) {
        this.id = UUID.randomUUID();

        if (products.isEmpty()) {
            throw new IllegalArgumentException();
        } else {
            this.products = products;
        }

        this.promoCodeUsed = promoCodeUsed;
        this.pembeli = pembeli;
        this.deliveryStatus = "MENUNGGU VERIFIKASI";
        this.deliveryCode = "";
        this.setDeliveryMethod(deliveryMethod);
    }

    public void setDeliveryStatus(String status) {
        String[] statusList = {"MENUNGGU VERIFIKASI", "DIPROSES", "DIKIRIM", "TIBA", "SELESAI"};
        if (Arrays.stream(statusList).noneMatch(item -> (item.equals(status)))) {
            throw new IllegalArgumentException();
        } else {
            this.deliveryStatus = status;
        }
    }

    public void setDeliveryMethod(String method) {
        String[] statusList = {"MOTOR", "TRUK", "PESAWAT"};
        if (Arrays.stream(statusList).noneMatch(item -> (item.equals(method)))) {
            throw new IllegalArgumentException();
        } else {
            this.deliveryMethod = method;
        }
    }
}