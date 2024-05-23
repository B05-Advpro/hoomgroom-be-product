package id.ac.ui.cs.advprog.hoomgroomproduct.repository;

import id.ac.ui.cs.advprog.hoomgroomproduct.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, String> {
}
