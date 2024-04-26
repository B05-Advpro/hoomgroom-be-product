package id.ac.ui.cs.advprog.hoomgroomproduct.service;

import id.ac.ui.cs.advprog.hoomgroomproduct.model.Transaction;
import id.ac.ui.cs.advprog.hoomgroomproduct.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public Transaction create(Transaction transaction) {
        transactionRepository.save(transaction);
        return transaction;
    }
}
