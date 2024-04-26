package id.ac.ui.cs.advprog.hoomgroomproduct.enums;

import lombok.Getter;

@Getter
public enum DeliveryStatus {
    MENUNGGU_VERIFIKASI("MENUNGGU_VERIFIKASI"),
    DIPROSES("DIPROSES"),
    DIKIRIM("DIKIRIM"),
    TIBA("TIBA"),
    SELESAI("SELESAI");

    private final String value;

    private DeliveryStatus(String value) {
        this.value = value;
    }

    public static boolean contains(String param) {
        for (DeliveryStatus deliveryStatus : DeliveryStatus.values()) {
            if (deliveryStatus.name().equals(param)) {
                return true;
            }
        }
        return false;
    }
}