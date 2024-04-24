package id.ac.ui.cs.advprog.hoomgroomproduct.repository;


import id.ac.ui.cs.advprog.hoomgroomproduct.model.Product;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;


import org.springframework.stereotype.Repository;


@Repository
public class ProductRepository {
    private List<Product> productRepo = new ArrayList<>();


    public Product createProduct(Product product) {
        if (product.getProductId() == null) {
            UUID uuid = UUID.randomUUID();
            product.setProductId(uuid);
            productRepo.add(product);
            return product;
        }
        return null;
    }

    public Product editProduct(Product editedProduct) {
        UUID productId = editedProduct.getProductId();
        Product searchedProduct = findById(productId);
        int indexOfProduct = productRepo.indexOf(searchedProduct);
        productRepo.set(indexOfProduct, editedProduct);
        return editedProduct;
    }


    public Product deleteProduct(UUID productId) {
        Product product = findById(productId);
        productRepo.remove(product);
        return product;
    }


    public Iterator<Product> findAll() {
        return productRepo.iterator();
    }


    public Product findById(UUID productId) {
        return productRepo.stream()
                .filter(product -> product.getProductId().equals(productId))
                .findFirst()
                .orElseThrow(() ->
                        new IllegalArgumentException(productId + "can't be found")
                );
    }


    
}