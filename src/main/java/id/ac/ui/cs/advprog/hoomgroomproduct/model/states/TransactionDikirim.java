package id.ac.ui.cs.advprog.hoomgroomproduct.model.states;

public class TransactionDikirim implements TransactionStatus {

    public TransactionDikirim() {

    }

    @Override
    public TransactionStatus nextStatus() {
        return new TransactionTiba();
    }
}
