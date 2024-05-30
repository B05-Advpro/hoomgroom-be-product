package id.ac.ui.cs.advprog.hoomgroomproduct.model.states;

public class TransactionSelesai implements TransactionStatus {

    public TransactionSelesai() {

    }

    @Override
    public TransactionStatus nextStatus() {
        return this;
    }
}
