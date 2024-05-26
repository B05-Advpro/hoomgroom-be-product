package id.ac.ui.cs.advprog.hoomgroomproduct.service;

import java.util.List;
import java.util.UUID;

public interface ProductService {
    Product createProduct(Product product);
    List<Product> findAll();
    Product findById(UUID productId);
    Product editProduct(Product editedProduct);
    Product deleteProduct(UUID productId);
}