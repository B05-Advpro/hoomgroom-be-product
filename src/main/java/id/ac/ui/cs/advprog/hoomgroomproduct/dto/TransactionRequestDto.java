package id.ac.ui.cs.advprog.hoomgroomproduct.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class TransactionRequestDto {
    private String username;
    private String promoCodeUsed;
    private String deliveryMethod;
}
