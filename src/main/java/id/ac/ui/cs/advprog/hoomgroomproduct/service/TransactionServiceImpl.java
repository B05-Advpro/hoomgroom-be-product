package id.ac.ui.cs.advprog.hoomgroomproduct.service;

import id.ac.ui.cs.advprog.hoomgroomproduct.dto.TransactionItemRequestDto;
import id.ac.ui.cs.advprog.hoomgroomproduct.dto.TransactionRequestDto;
import id.ac.ui.cs.advprog.hoomgroomproduct.dto.TransactionStatusUpdateRequestDto;
import id.ac.ui.cs.advprog.hoomgroomproduct.model.Transaction;
import id.ac.ui.cs.advprog.hoomgroomproduct.model.TransactionBuilder;
import id.ac.ui.cs.advprog.hoomgroomproduct.model.TransactionItem;
import id.ac.ui.cs.advprog.hoomgroomproduct.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public Transaction create(TransactionRequestDto request) {

        // Getting request data
        UUID pembeli = UUID.fromString(request.getPembeli());
        String promoCodeUsed = request.getPromoCodeUsed();
        String deliverMethod = request.getDeliveryMethod();
        List<TransactionItemRequestDto> requestProducts = request.getProducts();
        List<TransactionItem> products = new ArrayList<>();

        // Inserting products from request into requestProducts
        for (TransactionItemRequestDto product : requestProducts) {
            TransactionItem transactionItem = new TransactionItem(UUID.fromString(product.getId()),
                    product.getName(),
                    product.getPrice(),
                    product.getQuantity());
            products.add(transactionItem);
        }

        // Calculating total price
        double totalPrice = 0;
        for (TransactionItem product : products) {
            totalPrice += product.getPrice() * product.getQuantity();
        }

        // Building & saving new transaction
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

    public Transaction nextStatus(TransactionStatusUpdateRequestDto request) {
        UUID transactionId = UUID.fromString(request.getId());
        Optional<Transaction> optionalTransaction = transactionRepository.findById(transactionId);

        if (optionalTransaction.isEmpty()) {
            throw new IllegalArgumentException("Transaction ID not found.");
        }

        Transaction transaction = optionalTransaction.get();
        transaction.setTransactionStatus(transaction.getTransactionStatus().nextStatus());
        transactionRepository.save(transaction);

        return transaction;
    }
}
