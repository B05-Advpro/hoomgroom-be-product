package id.ac.ui.cs.advprog.hoomgroomproduct.service;

import id.ac.ui.cs.advprog.hoomgroomproduct.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    ProductRepository productRepository;

    @InjectMocks
    ProductServiceImpl productServiceImpl;

    @BeforeEach
    void setUp() {

    }

    Product createProduct(UUID productId, String productName, String description, List<String> tag, String picture, Double realPrice, Double discountPrice){
        Product product = new Product();
        product.setProductId(productId);
        product.setProductName(productName);
        product.setDescription(description);
        product.setPicture(picture);
        product.setRealPrice(realPrice);
        product.setDiscountPrice(discountPrice);
        return product;
    }

    @Test
    void testCreateProduct(){
        List<String> tags = Arrays.asList("Interior", "Furniture");
        Product product = createProduct(
                UUID.fromString("6f1238f8-d13a-4e5b-936f-e63523894012"),
                "Wooden Chair",
                "This chair is made from holy wood", 
                tags,
                "https://hoomgroom-product.com/single_wooden_chair.jpg",
                50000.0,
                40000.0);

        when(productRepository.createProduct(product)).thenReturn(product);
        Product savedProduct = productServiceImpl.createProduct(product);
        assertEquals(product.getProductId(), savedProduct.getProductId());
        verify(productRepository, times(1)).createProduct(product);
    }

    @Test
    void testFindAllProduct(){
        List<String> tags = Arrays.asList("Interior", "Furniture");
        List<Product> productList = new ArrayList<>();
        Product product1 = createProduct(
                UUID.fromString("6f1238f8-d13a-4e5b-936f-e63523894012"),
                "Wooden Chair",
                "This chair is made from holy wood", 
                tags,
                "https://hoomgroom-product.com/single_wooden_chair.jpg",
                50000.0,
                40000.0);

        Product product2 = createProduct(
                UUID.fromString("6f1238f8-d13a-4e5b-936f-e55156158105"),
                "Double Wooden Chair",
                "This comfy chair fits more person than our single wooden chair.",
                tags,
                "https://hoomgroom-product.com/double_wooden_chair.jpg",
                70000.0,
                50000.0);

        productList.add(product1);
        productList.add(product2);

        Iterator<Product> iterator = productList.iterator();
        when(productRepository.findAll()).thenReturn(iterator);

        List<Product> result = productServiceImpl.findAll();
        assertEquals(productList.size(), result.size());
        for (int i = 0; i < productList.size(); i++) {
            assertEquals(productList.get(i), result.get(i));
        }
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void testFindProductById(){
        List<String> tags = Arrays.asList("Interior", "Furniture");
        Product product = createProduct(
                UUID.fromString("6f1238f8-d13a-4e5b-936f-e63523894012"),
                "Wooden Chair",
                "This chair is made from holy wood", 
                tags,
                "https://hoomgroom-product.com/single_wooden_chair.jpg",
                50000.0,
                40000.0);

        when(productRepository.findById(product.getProductId())).thenReturn(product);

        Product foundProduct = productServiceImpl.findById(product.getProductId());

        assertEquals(product, foundProduct);
        verify(productRepository, times(1)).findById(product.getProductId());
    }

    @Test
    void testEditProduct(){
        List<String> tags = Arrays.asList("Interior", "Furniture");
        Product product = createProduct(
                UUID.fromString("6f1238f8-d13a-4e5b-936f-e63523894012"),
                "Wooden Chair",
                "This chair is made from holy wood", 
                tags,
                "https://hoomgroom-product.com/single_wooden_chair.jpg",
                50000.0,
                40000.0);

        when(productRepository.editProduct(product)).thenReturn(product);

        Product editedProduct = productServiceImpl.editProduct(product);

        assertEquals(product, editedProduct);
        verify(productRepository, times(1)).editProduct(product);
    }

    @Test
    void testDeleteProduct(){
        List<String> tags = Arrays.asList("Interior", "Furniture");
        Product product = createProduct(
                UUID.fromString("6f1238f8-d13a-4e5b-936f-e63523894012"),
                "Wooden Chair",
                "This chair is made from holy wood", 
                tags,
                "https://hoomgroom-product.com/single_wooden_chair.jpg",
                50000.0,
                40000.0);

        when(productRepository.deleteProduct(product.getProductId())).thenReturn(product);
        Product deletedProduct = productServiceImpl.deleteProduct(product.getProductId());
        assertEquals(product, deletedProduct);
        verify(productRepository, times(1)).deleteProduct(product.getProductId());
    }
}