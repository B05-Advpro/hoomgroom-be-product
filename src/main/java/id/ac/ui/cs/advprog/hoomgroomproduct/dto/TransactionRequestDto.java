package id.ac.ui.cs.advprog.hoomgroomproduct.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TransactionRequestDto {
    private String pembeli;
    private String promoCodeUsed;
    private String deliveryMethod;
    private List<TransactionItemRequestDto> products;
}
