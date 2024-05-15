package id.ac.ui.cs.advprog.hoomgroomproduct.service;

import id.ac.ui.cs.advprog.hoomgroomproduct.dto.TransactionItemRequestDto;
import id.ac.ui.cs.advprog.hoomgroomproduct.dto.TransactionRequestDto;
import id.ac.ui.cs.advprog.hoomgroomproduct.model.Transaction;
import id.ac.ui.cs.advprog.hoomgroomproduct.model.TransactionBuilder;
import id.ac.ui.cs.advprog.hoomgroomproduct.model.TransactionItem;
import id.ac.ui.cs.advprog.hoomgroomproduct.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public Transaction create(TransactionRequestDto request) {
        UUID pembeli = UUID.fromString(request.getUserId());
        String promoCodeUsed = request.getPromoCodeUsed();
        String deliverMethod = request.getDeliveryMethod();
        Map<String, TransactionItemRequestDto> requestProducts = request.getProducts();
        Map<UUID, TransactionItem> products = new HashMap<>();

        for (Map.Entry<String, TransactionItemRequestDto> entry : requestProducts.entrySet()) {
            TransactionItemRequestDto product = entry.getValue();
            TransactionItem transactionItem = new TransactionItem(UUID.fromString(product.getProductId()),
                    product.getName(),
                    product.getPrice(),
                    product.getQuantity());
            products.put(UUID.fromString(product.getProductId()) ,transactionItem);
        }

        double totalPrice = 0;
        for (Map.Entry<UUID, TransactionItem> entry : products.entrySet()) {
            TransactionItem product = entry.getValue();
            totalPrice += product.getPrice() * product.getQuantity();
        }

        Transaction transaction = new TransactionBuilder()
                .setUserId(pembeli)
                .setProducts(products)
                .setTotalPrice(totalPrice)
                .setDeliveryMethod(deliverMethod)
                .setPromoCodeUsed(promoCodeUsed)
                .build();

        transactionRepository.save(transaction);
        return transaction;
    }
}
