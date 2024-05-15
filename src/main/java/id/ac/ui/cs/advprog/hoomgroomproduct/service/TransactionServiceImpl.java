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
        List<TransactionItemRequestDto> requestProducts = request.getItems();
        List<TransactionItem> items = new ArrayList<>();

        for (TransactionItemRequestDto item : requestProducts) {
            TransactionItem transactionItem = new TransactionItem(UUID.fromString(item.getProductId()),
                    item.getName(),
                    item.getPrice(),
                    item.getQuantity());
            items.add(transactionItem);
        }

        double totalPrice = 0;
        for (TransactionItem item : items) {
            totalPrice += item.getPrice() * item.getQuantity();
        }

        Transaction transaction = new TransactionBuilder()
                .setUserId(pembeli)
                .setItems(items)
                .setTotalPrice(totalPrice)
                .setDeliveryMethod(deliverMethod)
                .setPromoCodeUsed(promoCodeUsed)
                .build();

        transactionRepository.save(transaction);
        return transaction;
    }
}
