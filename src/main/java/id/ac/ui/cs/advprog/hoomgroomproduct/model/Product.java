package id.ac.ui.cs.advprog.hoomgroomproduct.model;

import lombok.Setter;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter @Setter
public class Product {
    private String productId;
    private String productName;
    private List<String> tag;
    private String description;
    private String picture;
    private double realPrice;
    private double discountPrice;
}