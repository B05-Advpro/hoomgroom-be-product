package id.ac.ui.cs.advprog.hoomgroomproduct.model.states;

public class TransactionTiba implements TransactionStatus {

    public TransactionTiba() {

    }

    @Override
    public TransactionStatus nextStatus() {
        return new TransactionSelesai();
    }
}
