package id.ac.ui.cs.advprog.hoomgroomproduct.service;

import id.ac.ui.cs.advprog.hoomgroomproduct.dto.TransactionRequestDto;
import id.ac.ui.cs.advprog.hoomgroomproduct.dto.TransactionStatusUpdateRequestDto;
import id.ac.ui.cs.advprog.hoomgroomproduct.model.Transaction;

public interface TransactionService {
    Transaction create(TransactionRequestDto request);

    /*
     * Updates transaction deliveryStatus into next state.
     *
     * @return a TransactionStatus object representing new transaction state.
     */
    Transaction nextStatus(TransactionStatusUpdateRequestDto request);
}
