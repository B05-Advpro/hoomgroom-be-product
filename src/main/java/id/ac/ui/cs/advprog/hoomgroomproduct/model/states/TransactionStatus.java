package id.ac.ui.cs.advprog.hoomgroomproduct.model.states;

import id.ac.ui.cs.advprog.hoomgroomproduct.model.Transaction;

public interface TransactionStatus {
    /**
     * Creates a transaction object of the next state.
     * 
     * @return a Transaction object representing new transaction state.
     */
    TransactionStatus nextStatus();
}