package id.ac.ui.cs.advprog.hoomgroomproduct.repository;

import id.ac.ui.cs.advprog.hoomgroomproduct.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CartRepository extends JpaRepository<Cart, String> {
    Optional<Cart> findByUserId(Long userId);
}
