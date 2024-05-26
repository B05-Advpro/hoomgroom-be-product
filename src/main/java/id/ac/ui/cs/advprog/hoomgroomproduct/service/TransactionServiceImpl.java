package id.ac.ui.cs.advprog.hoomgroomproduct.service;

import id.ac.ui.cs.advprog.hoomgroomproduct.dto.TransactionRequestDto;
import id.ac.ui.cs.advprog.hoomgroomproduct.model.*;
import id.ac.ui.cs.advprog.hoomgroomproduct.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private CartService cartService;

    @Autowired
    private RestTemplate restTemplate;

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

        for (CartItem item : items) {
            TransactionItem transactionItem = new TransactionItem(item.getProductId(), item.getName(),
                    item.getPrice(), item.getQuantity());
            transactionItems.add(transactionItem);
        }

        double totalPrice = cart.getTotalPrice();
        if (promoCodeUsed != null && !promoCodeUsed.isEmpty()){
            double promoValue = Double.parseDouble(promoCodeUsed.substring(promoCodeUsed.length() - 2));
            totalPrice = totalPrice - (totalPrice * (promoValue/100));
        }

        double wallet = cart.getWallet();
        if (wallet < totalPrice) {
            throw new IllegalStateException("Not enough balance in wallet");
        } else {
            cart.setWallet(wallet - totalPrice);
        }

        Transaction transaction = new TransactionBuilder()
                .setUserId(userId)
                .setItems(transactionItems)
                .setTotalPrice(totalPrice)
                .setDeliveryMethod(deliverMethod)
                .setPromoCodeUsed(promoCodeUsed)
                .build();

        transactionItems.forEach(item -> item.setTransaction(transaction));
        transactionRepository.save(transaction);
        cartService.clearCart(userId);
        updateSales(transactionItems);
        return transaction;
    }

    public void updateSales(List<TransactionItem> transactionItems) {
        Map<String, Integer> salesUpdate = new HashMap<>();

        for (TransactionItem item : transactionItems) {
            salesUpdate.put(String.valueOf(item.getProductId()), item.getQuantity());
        }

        try {
            restTemplate.postForEntity("https://api.b5-hoomgroom.com/admin/product/sold", salesUpdate, Void.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Transaction> getAll() {
        return transactionRepository.findAll();
    }

    @Override
    public List<Transaction> getTransactionByUserId(Long userId) {
        return transactionRepository.findByUserId(userId);
    }
}
