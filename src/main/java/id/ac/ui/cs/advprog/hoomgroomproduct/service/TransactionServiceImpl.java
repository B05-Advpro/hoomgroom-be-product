package id.ac.ui.cs.advprog.hoomgroomproduct.service;

import id.ac.ui.cs.advprog.hoomgroomproduct.dto.TransactionItemRequestDto;
import id.ac.ui.cs.advprog.hoomgroomproduct.dto.TransactionRequestDto;
import id.ac.ui.cs.advprog.hoomgroomproduct.model.Transaction;
import id.ac.ui.cs.advprog.hoomgroomproduct.model.TransactionBuilder;
import id.ac.ui.cs.advprog.hoomgroomproduct.model.TransactionItem;
import id.ac.ui.cs.advprog.hoomgroomproduct.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public Transaction create(TransactionRequestDto request) {
        UUID pembeli = UUID.fromString(request.getPembeli());
        String promoCodeUsed = request.getPromoCodeUsed();
        String deliverMethod = request.getDeliveryMethod();
        List<TransactionItemRequestDto> requestProducts = request.getProducts();
        List<TransactionItem> products = new ArrayList<>();

        for (TransactionItemRequestDto product : requestProducts) {
            TransactionItem transactionItem = new TransactionItem(UUID.fromString(product.getId()),
                    product.getName(),
                    product.getPrice(),
                    product.getQuantity());
            products.add(transactionItem);
        }

        double totalPrice = 0;
        for (TransactionItem product : products) {
            totalPrice += product.getPrice() * product.getQuantity();
        }

        Transaction transaction = new TransactionBuilder()
                .setPembeli(pembeli)
                .setProducts(products)
                .setTotalPrice(totalPrice)
                .setDeliveryMethod(deliverMethod)
                .setPromoCodeUsed(promoCodeUsed)
                .build();

        transactionRepository.save(transaction);
        return transaction;
    }
}
