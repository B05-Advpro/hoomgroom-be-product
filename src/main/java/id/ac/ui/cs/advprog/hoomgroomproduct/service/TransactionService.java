package id.ac.ui.cs.advprog.hoomgroomproduct.service;

import id.ac.ui.cs.advprog.hoomgroomproduct.dto.TransactionRequestDto;
import id.ac.ui.cs.advprog.hoomgroomproduct.model.Transaction;
import id.ac.ui.cs.advprog.hoomgroomproduct.model.TransactionItem;

import java.util.List;

public interface TransactionService {
    public Transaction create(TransactionRequestDto request);
    public void updateSales(List<TransactionItem> items);
    public List<Transaction> getAll();
    public List<Transaction> getTransactionByUserId(Long userId);
}
