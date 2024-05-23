package id.ac.ui.cs.advprog.hoomgroomproduct.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class TransactionRequestDto {
    private Long userId;
    private String promoCodeUsed;
    private String deliveryMethod;
}
