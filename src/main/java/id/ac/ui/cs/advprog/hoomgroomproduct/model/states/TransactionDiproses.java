package id.ac.ui.cs.advprog.hoomgroomproduct.model.states;

public class TransactionDiproses implements  TransactionStatus {

    public TransactionDiproses() {

    }

    @Override
    public TransactionStatus nextStatus() {
        return new TransactionDikirim();
    }
}
