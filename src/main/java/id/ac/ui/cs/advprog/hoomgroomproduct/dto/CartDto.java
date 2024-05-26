package id.ac.ui.cs.advprog.hoomgroomproduct.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartDto {
    private String username;
    private String productId;
    private String name;
    private double price;
    private int quantity;
}
