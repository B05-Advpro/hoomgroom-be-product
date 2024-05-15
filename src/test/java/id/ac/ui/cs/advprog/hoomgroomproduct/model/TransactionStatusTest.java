package id.ac.ui.cs.advprog.hoomgroomproduct.model;

import id.ac.ui.cs.advprog.hoomgroomproduct.model.states.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
class TransactionStatusTest {
    Transaction transaction;
    List<TransactionItem> products;
    TransactionStatus transactionStatus;

    @Test
    void TestTransactionStatusOnCreate() {
        this.products = new ArrayList<>();
        TransactionItem product1 = new TransactionItem(UUID.fromString("ca1c1b7d-f5aa-4573-aeff-d01665cc88c8"),
                "Product 1", 15000, 2);
        TransactionItem product2 = new TransactionItem(UUID.fromString("df6c1b7d-f5aa-4573-aeff-d01665cc88c8"),
                "Product 2", 25000, 4);
        this.products.add(product1);
        this.products.add(product2);

        this.transaction = new TransactionBuilder()
                .setProducts(this.products)
                .setTotalPrice(130000)
                .setPromoCodeUsed("BELANJAHEMAT20")
                .setPembeli(UUID.fromString("4f59c670-f83f-4d41-981f-37ee660a6e4c"))
                .setDeliveryMethod("MOTOR")
                .build();

        assertSame(TransactionMenungguVerifikasi.class, this.transaction.getTransactionStatus().getClass());
    }

    @Test
    void TestTransactionMenungguVerifikasiStatus() {
        transactionStatus = new TransactionMenungguVerifikasi();
        transactionStatus = transactionStatus.nextStatus();

        assertSame(TransactionDiproses.class, transactionStatus.getClass());
    }

    @Test
    void TestTransactionDiprosesStatus() {
        transactionStatus = new TransactionDiproses();
        transactionStatus = transactionStatus.nextStatus();

        assertSame(TransactionDikirim.class, transactionStatus.getClass());
    }

    @Test
    void TestTransactionDikirimStatus() {
        transactionStatus = new TransactionDikirim();
        transactionStatus = transactionStatus.nextStatus();

        assertSame(TransactionTiba.class, transactionStatus.getClass());
    }

    @Test
    void TestTransactionTibaStatus() {
        transactionStatus = new TransactionTiba();
        transactionStatus = transactionStatus.nextStatus();

        assertSame(TransactionSelesai.class, transactionStatus.getClass());
    }

    @Test
    void TestTransactionSelesaiStatus() {
        transactionStatus = new TransactionSelesai();
        transactionStatus = transactionStatus.nextStatus();

        assertSame(TransactionSelesai.class, transactionStatus.getClass());
    }

}
