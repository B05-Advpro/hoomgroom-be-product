package id.ac.ui.cs.advprog.hoomgroomproduct.repository;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.Iterator;
import java.util.UUID;


import static org.junit.jupiter.api.Assertions.*;




@ExtendWith(MockitoExtension.class)
class ProductRepositoryTest {


    @InjectMocks
    ProductRepository productRepository;


    @BeforeEach
    void setUp() {
    }


    @Test
    void testCreateAndFind() {
        Product product = new Product();
        product.setProductId(UUID.fromString("6f1238f8-d13a-4e5b-936f-e63523894012"));
        product.setProductName("Wooden Chair");
        product.setDescription("This chair is made from holy wood");
        product.setPicture("https://hoomgroom-product.com/single_wooden_chair.jpg");
        product.setRealPrice(75000.0);
        product.setDiscountPrice(50000.0);
        productRepository.createProduct(product);


        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product.getProductId(), savedProduct.getProductId());
        assertEquals(product.getProductName(), savedProduct.getProductName());
        assertEquals(product.getDescription(), savedProduct.getDescription());
        assertEquals(product.getPicture(), savedProduct.getPicture());
        assertEquals(product.getRealPrice(), savedProduct.getRealPrice());
        assertEquals(product.getDiscountPrice(), savedProduct.getDiscountPrice());
    }


    @Test
    void testFindAllIfEmpty() {
        Iterator<Product> productIterator = productRepository.findAll();
        assertFalse(productIterator.hasNext());
    }


    @Test
    void testFindAllIfMoreThanOneProduct() {
        Product product1 = new Product();
        product1.setProductId(UUID.fromString("6f1238f8-d13a-4e5b-936f-e63523894012"));
        product1.setProductName("Wooden Chair");
        product1.setDescription("This chair is made from holy wood");
        product1.setPicture("https://hoomgroom-product.com/single_wooden_chair.jpg");
        
        product1.setRealPrice(75000.0);
        product1.setDiscountPrice(50000.0);
        productRepository.createProduct(product1);


        Product product2 = new Product();
        product2.setProductId(UUID.fromString("857b3c84-8eab-4296-8ca9-6773ffd86517"));
        product2.setProductName("Queen Size Bed Olympic");
        product2.setDescription("This bed made from best material with best design. Fit for more than 2 person");
        product2.setPicture("https://hoomgroom-product.com/queen_size_bed.jpg");
        product2.setRealPrice(1000000.0);
        product2.setDiscountPrice(900000.0);
        productRepository.createProduct(product2);


        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product1.getProductId(), savedProduct.getProductId());
        savedProduct = productIterator.next();
        assertEquals(product2.getProductId(), savedProduct.getProductId());
        assertFalse(productIterator.hasNext());

    }

    @Test
    void testFindById() {
        Product product = new Product();
        UUID productId = UUID.fromString("6f1238f8-d13a-4e5b-936f-e63523894012");
        product.setProductId(productId);
        product.setProductName("Wooden Chair");
        product.setDescription("This chair is made from holy wood");
        product.setPicture("https://hoomgroom-product.com/single_wooden_chair.jpg");
        
        product.setRealPrice(75000.0);
        product.setDiscountPrice(50000.0);
        productRepository.createProduct(product);

        Product foundProduct = productRepository.findById(productId);

        assertNotNull(foundProduct);
        assertEquals(productId, foundProduct.getProductId());
        assertEquals("Wooden Chair", foundProduct.getProductName());
        assertEquals("This chair is made from holy wood", foundProduct.getDescription());
        assertEquals("https://hoomgroom-product.com/single_wooden_chair.jpg", foundProduct.getPicture());
        assertEquals(75000.0, foundProduct.getRealPrice());
        assertEquals(50000.0, foundProduct.getDiscountPrice());
    }

    @Test
    void testFindByIdNotFound() {
        UUID nonExistentId = UUID.fromString("857b3c84-8eab-4296-8ca9-6773ffd86517");
        assertThrows(IllegalArgumentException.class, () ->
                productRepository.findById(nonExistentId)
        );
    }


    @Test
    void testEditProduct(){
        Product product = new Product();
        product.setProductId(UUID.fromString("6f1238f8-d13a-4e5b-936f-e63523894012"));
        product.setProductName("Wooden Chair");
        product.setDescription("This chair is made from holy wood");
        product.setPicture("https://hoomgroom-product.com/single_wooden_chair.jpg");
        
        product.setRealPrice(75000.0);
        product.setDiscountPrice(50000.0);
        productRepository.createProduct(product);


        Product editedProduct = new Product();
        editedProduct.setProductId(UUID.fromString("6f1238f8-d13a-4e5b-936f-e63523894012"));
        editedProduct.setProductName("Double Wooden Chair");
        editedProduct.setDescription("This comfy chair fits more person than our single wooden chair.");
        editedProduct.setPicture("https://hoomgroom-product.com/double_wooden_chair.jpg");
        editedProduct.setRealPrice(70000.0);
        editedProduct.setDiscountPrice(50000.0);
        productRepository.editProduct(editedProduct);


        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(editedProduct.getProductId(),savedProduct.getProductId());
        assertEquals(editedProduct.getProductName(), savedProduct.getProductName());
        assertEquals(editedProduct.getDescription(),savedProduct.getDescription());
        assertEquals(editedProduct.getPicture(),savedProduct.getPicture());
        
        assertEquals(editedProduct.getRealPrice(),savedProduct.getRealPrice());
        assertEquals(editedProduct.getDiscountPrice(),savedProduct.getDiscountPrice());
    }


    @Test
    void testEditProductWithMoreThanOneItem(){
        Product product1 = new Product();
        product1.setProductId(UUID.fromString("6f1238f8-d13a-4e5b-936f-e63523894012"));
        product1.setProductName("Wooden Chair");
        product1.setDescription("This chair is made from holy wood");
        product1.setPicture("https://hoomgroom-product.com/single_wooden_chair.jpg");
        
        product1.setRealPrice(75000.0);
        product1.setDiscountPrice(50000.0);
        productRepository.createProduct(product1);


        Product product2 = new Product();
        product2.setProductId(UUID.fromString("857b3c84-8eab-4296-8ca9-6773ffd86517"));
        product2.setProductName("Queen Size Bed Olympic");
        product2.setDescription("This bed made from best material with best design. Fit for more than 2 person");
        product2.setPicture("https://hoomgroom-product.com/queen_size_bed.jpg");
        
        product2.setRealPrice(1000000.0);
        product2.setDiscountPrice(900000.0);
        productRepository.createProduct(product2);


        Product editedProduct = new Product();
        editedProduct.setProductId(UUID.fromString("6f1238f8-d13a-4e5b-936f-e63523894012"));
        editedProduct.setProductName("Double Wooden Chair");
        editedProduct.setDescription("This comfy chair fits more person than our single wooden chair.");
        editedProduct.setPicture("https://hoomgroom-product.com/double_wooden_chair.jpg");        
        editedProduct.setRealPrice(70000.0);
        editedProduct.setDiscountPrice(50000.0);
        productRepository.editProduct(editedProduct);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(editedProduct.getProductId(),savedProduct.getProductId());
        assertEquals(editedProduct.getProductName(), savedProduct.getProductName());
        assertEquals(editedProduct.getDescription(),savedProduct.getDescription());
        assertEquals(editedProduct.getPicture(),savedProduct.getPicture());
        assertEquals(editedProduct.getRealPrice(),savedProduct.getRealPrice());
        assertEquals(editedProduct.getDiscountPrice(),savedProduct.getDiscountPrice());

        savedProduct = productIterator.next();
        assertEquals(product2.getProductId(),savedProduct.getProductId());
        assertEquals(product2.getProductName(), savedProduct.getProductName());
        assertEquals(product2.getDescription(),savedProduct.getDescription());
        assertEquals(product2.getPicture(),savedProduct.getPicture());
        assertEquals(product2.getRealPrice(),savedProduct.getRealPrice());
        assertEquals(product2.getDiscountPrice(),savedProduct.getDiscountPrice());
    }

    @Test
    void testEditProductNotFound() {
        Product editedProduct = new Product();
        editedProduct.setProductId(UUID.fromString("6f1238f8-d13a-4e5b-936f-e63523894012"));
        editedProduct.setProductName("Double Wooden Chair");
        editedProduct.setDescription("This comfy chair fits more person than our single wooden chair.");
        editedProduct.setPicture("https://hoomgroom-product.com/double_wooden_chair.jpg");
        editedProduct.setRealPrice(70000.0);
        editedProduct.setDiscountPrice(50000.0);

        assertThrows(IllegalArgumentException.class, () ->
                productRepository.editProduct(editedProduct));
    }

    @Test
    void testEditProductWithNullId() {
        Product editedProduct = new Product();
        editedProduct.setProductName("Double Wooden Chair");
        editedProduct.setDescription("This comfy chair fits more person than our single wooden chair.");
        editedProduct.setPicture("https://hoomgroom-product.com/double_wooden_chair.jpg");
        editedProduct.setRealPrice(70000.0);
        editedProduct.setDiscountPrice(50000.0);

        assertThrows(IllegalArgumentException.class, () ->
                productRepository.editProduct(editedProduct));
    }

    @Test
    void testDeleteProduct(){
        Product product = new Product();
        product.setProductId(UUID.fromString("6f1238f8-d13a-4e5b-936f-e63523894012"));
        product.setProductName("Double Wooden Chair");
        product.setDescription("This comfy chair fits more person than our single wooden chair.");
        product.setPicture("https://hoomgroom-product.com/double_wooden_chair.jpg");
        
        product.setRealPrice(70000.0);
        product.setDiscountPrice(50000.0);
        productRepository.createProduct(product);

        Product deletedProduct = productRepository.deleteProduct(product.getProductId());
        assertEquals(product, deletedProduct);

        Iterator<Product> productIterator  = productRepository.findAll();
        assertFalse(productIterator.hasNext());

    }

    @Test
    void testDeleteProductWithMoreThanOneItem(){
        Product product1 = new Product();
        product1.setProductId(UUID.fromString("6f1238f8-d13a-4e5b-936f-e63523894012"));
        product1.setProductName("Wooden Chair");
        product1.setDescription("This chair is made from holy wood");
        product1.setPicture("https://hoomgroom-product.com/single_wooden_chair.jpg");
        
        product1.setRealPrice(75000.0);
        product1.setDiscountPrice(50000.0);
        productRepository.createProduct(product1);


        Product product2 = new Product();
        product2.setProductId(UUID.fromString("857b3c84-8eab-4296-8ca9-6773ffd86517"));
        product2.setProductName("Queen Size Bed Olympic");
        product2.setDescription("This bed made from best material with best design. Fit for more than 2 person");
        product2.setPicture("https://hoomgroom-product.com/queen_size_bed.jpg");
        
        product2.setRealPrice(1000000.0);
        product2.setDiscountPrice(900000.0);
        productRepository.createProduct(product2);

        Product deletedProduct = productRepository.deleteProduct(product1.getProductId());
        assertEquals(product1, deletedProduct);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product2.getProductId(),savedProduct.getProductId());
        assertEquals(product2.getProductName(), savedProduct.getProductName());
        assertEquals(product2.getDescription(),savedProduct.getDescription());
        assertEquals(product2.getPicture(),savedProduct.getPicture());
        assertEquals(product2.getRealPrice(),savedProduct.getRealPrice());
        assertEquals(product2.getDiscountPrice(),savedProduct.getDiscountPrice());
    }

    @Test
    void testDeleteProductWithNullId() {
        assertThrows(IllegalArgumentException.class, () ->
                productRepository.deleteProduct(null));
    }

    @Test
    void testDeleteProductNotFound() {
        assertThrows(IllegalArgumentException.class, () ->
                productRepository.deleteProduct(UUID.fromString("6f1238f8-d13a-4e5b-936f-e63523894012")));
    }

}