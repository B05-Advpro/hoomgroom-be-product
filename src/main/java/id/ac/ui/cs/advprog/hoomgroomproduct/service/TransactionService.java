package id.ac.ui.cs.advprog.hoomgroomproduct.service;

import id.ac.ui.cs.advprog.hoomgroomproduct.dto.TransactionRequestDto;
import id.ac.ui.cs.advprog.hoomgroomproduct.dto.TransactionStatusUpdateRequestDto;
import id.ac.ui.cs.advprog.hoomgroomproduct.model.Transaction;
import id.ac.ui.cs.advprog.hoomgroomproduct.model.TransactionItem;

import java.util.List;

public interface TransactionService {
    Transaction create(TransactionRequestDto request, String token);
    void updateSales(List<TransactionItem> items, String token);
    List<Transaction> getAll();
    List<Transaction> getTransactionByUsername(String username);

    /*
     * Updates transaction deliveryStatus into next state.
     *
     * @return a TransactionStatus object representing new transaction state.
     */
    Transaction nextStatus(TransactionStatusUpdateRequestDto request);
}
