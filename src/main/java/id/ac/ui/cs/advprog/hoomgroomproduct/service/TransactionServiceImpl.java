package id.ac.ui.cs.advprog.hoomgroomproduct.service;

import id.ac.ui.cs.advprog.hoomgroomproduct.dto.TransactionRequestDto;
import id.ac.ui.cs.advprog.hoomgroomproduct.dto.TransactionStatusUpdateRequestDto;
import id.ac.ui.cs.advprog.hoomgroomproduct.model.Cart;
import id.ac.ui.cs.advprog.hoomgroomproduct.model.CartItem;
import id.ac.ui.cs.advprog.hoomgroomproduct.model.Transaction;
import id.ac.ui.cs.advprog.hoomgroomproduct.model.TransactionItem;
import id.ac.ui.cs.advprog.hoomgroomproduct.model.states.TransactionTiba;
import id.ac.ui.cs.advprog.hoomgroomproduct.repository.TransactionRepository;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final CartService cartService;
    private final RestTemplate restTemplate;

    public TransactionServiceImpl(TransactionRepository transactionRepository, CartService cartService, RestTemplate restTemplate) {
        this.transactionRepository = transactionRepository;
        this.cartService = cartService;
        this.restTemplate = restTemplate;
    }

    @Override
    public Transaction create(TransactionRequestDto request, String token) {
        String username = request.getUsername();
        String promoCodeUsed = request.getPromoCodeUsed();
        String deliverMethod = request.getDeliveryMethod();

        Cart cart = cartService.getCart(username);
        if (cart.getItems().isEmpty()) {
            throw new IllegalStateException("Cart is empty");
        }

        Transaction transaction = new Transaction(username, promoCodeUsed, deliverMethod);

        List<CartItem> items = cart.getItems();
        List<TransactionItem> transactionItems = new ArrayList<>();

        for (CartItem item : items) {
            TransactionItem transactionItem = new TransactionItem(item.getProductId(), item.getName(),
                    item.getPrice(), item.getQuantity());
            transactionItem.setTransaction(transaction);
            transactionItems.add(transactionItem);
        }
        transaction.setItems(transactionItems);

        double totalPrice = cart.getTotalPrice();
        if (promoCodeUsed != null && !promoCodeUsed.isEmpty()){
            double promoValue = Double.parseDouble(promoCodeUsed.substring(promoCodeUsed.length() - 2));
            totalPrice = totalPrice - (totalPrice * (promoValue/100));
        }
        transaction.setTotalPrice(totalPrice);

        double wallet = cart.getWallet();
        if (wallet < totalPrice) {
            throw new IllegalStateException("Not enough balance in wallet");
        } else {
            cart.setWallet(wallet - totalPrice);
        }

        transactionRepository.save(transaction);
        cartService.clearCart(username);
        updateSales(transactionItems, token);
        return transaction;
    }

    public void updateSales(List<TransactionItem> transactionItems, String token) {
        Map<String, Integer> salesUpdate = new HashMap<>();

        for (TransactionItem item : transactionItems) {
            salesUpdate.put(String.valueOf(item.getProductId()), item.getQuantity());
        }

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);

        HttpEntity<Map<String, Integer>> requestEntity = new HttpEntity<>(salesUpdate, headers);
        try {
            restTemplate.exchange("https://api.b5-hoomgroom.com/admin/product/sold", HttpMethod.POST, requestEntity, Void.class);
        } catch (Exception e) {
            throw new IllegalStateException();
        }
    }

    @Override
    public List<Transaction> getAll() {
        return transactionRepository.findAll();
    }

    @Override
    public List<Transaction> getTransactionByUsername(String username) {
        return transactionRepository.findByUsername(username);
    }

    @Override
    public Transaction nextStatus(TransactionStatusUpdateRequestDto request, String role) {
        String transactionId = request.getId();
        Optional<Transaction> optionalTransaction = transactionRepository.findById(transactionId);

        if (optionalTransaction.isEmpty()) {
            throw new IllegalArgumentException("Transaction ID not found.");
        }

        Transaction transaction = optionalTransaction.get();

        if (transaction.getTransactionStatus().getClass() == TransactionTiba.class) {
            if (role.equals("USER")) {
                transaction.setTransactionStatus(transaction.getTransactionStatus().nextStatus());
            }
        } else {
            if (role.equals("ADMIN")) {
                transaction.setTransactionStatus(transaction.getTransactionStatus().nextStatus());
            }
        }

        transactionRepository.save(transaction);

        return transaction;
    }
}
