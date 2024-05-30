package id.ac.ui.cs.advprog.hoomgroomproduct.model.states;

import id.ac.ui.cs.advprog.hoomgroomproduct.model.Transaction;
import jakarta.persistence.Embeddable;

@Embeddable
public interface TransactionStatus {
    /*
     * Creates a transaction object of the next state.
     * 
     * @return a TransactionStatus object representing new transaction state.
     */
    TransactionStatus nextStatus();
}