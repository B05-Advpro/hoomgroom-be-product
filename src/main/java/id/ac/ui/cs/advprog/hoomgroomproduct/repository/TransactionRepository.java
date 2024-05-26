package id.ac.ui.cs.advprog.hoomgroomproduct.repository;

import id.ac.ui.cs.advprog.hoomgroomproduct.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String> {
    List<Transaction> findByUsername(String username);
}
