package id.ac.ui.cs.advprog.hoomgroomproduct.service;

import id.ac.ui.cs.advprog.hoomgroomproduct.dto.TransactionRequestDto;
import id.ac.ui.cs.advprog.hoomgroomproduct.model.*;
import id.ac.ui.cs.advprog.hoomgroomproduct.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private CartService cartService;

    @Override
    public Transaction create(TransactionRequestDto request) {
        Long userId = request.getUserId();
        String promoCodeUsed = request.getPromoCodeUsed();
        String deliverMethod = request.getDeliveryMethod();
        Cart cart = cartService.getCart(userId);

        if (cart.getItems().isEmpty()) {
            throw new IllegalStateException("Cart is empty");
        }

        List<CartItem> items = cart.getItems();
        List<TransactionItem> transactionItems = new ArrayList<>();

        double totalPrice = 0;
        for (CartItem item : items) {
            TransactionItem transactionItem = new TransactionItem(item.getProductId(), item.getName(),
                    item.getPrice(), item.getQuantity());
            totalPrice += item.getPrice() * item.getQuantity();
            transactionItems.add(transactionItem);
        }

        double promoValue = Double.parseDouble(promoCodeUsed.substring(promoCodeUsed.length() - 2));
        double newPrice = totalPrice - (totalPrice * (promoValue/100));

        Transaction transaction = new TransactionBuilder()
                .setUserId(userId)
                .setItems(transactionItems)
                .setTotalPrice(newPrice)
                .setDeliveryMethod(deliverMethod)
                .setPromoCodeUsed(promoCodeUsed)
                .build();

        transactionRepository.save(transaction);
        return transaction;
    }
}
