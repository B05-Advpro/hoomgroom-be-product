package id.ac.ui.cs.advprog.hoomgroomproduct.model.states;

import id.ac.ui.cs.advprog.hoomgroomproduct.model.Transaction;

public class TransactionMenungguVerifikasi implements TransactionStatus {

    public TransactionMenungguVerifikasi() {

    }

    public TransactionStatus nextStatus() {
        return new TransactionDiproses();
    }

}