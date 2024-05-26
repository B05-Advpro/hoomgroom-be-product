package id.ac.ui.cs.advprog.hoomgroomproduct.enums;

import lombok.Getter;

@Getter
public enum DeliveryMethod {
    MOTOR("MOTOR"),
    TRUK("TRUK"),
    PESAWAT("PESAWAT");

    private final String value;

    private DeliveryMethod(String value) {
        this.value = value;
    }

    public static boolean contains(String param) {
        for (DeliveryMethod deliveryMethod : DeliveryMethod.values()) {
            if (deliveryMethod.name().equals(param)) {
                return true;
            }
        }
        return false;
    }
}