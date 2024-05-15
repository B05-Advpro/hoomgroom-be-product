package id.ac.ui.cs.advprog.hoomgroomproduct.repository;

import id.ac.ui.cs.advprog.hoomgroomproduct.model.TransactionItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TransactionItemRepository extends JpaRepository<TransactionItem, UUID> {
}
