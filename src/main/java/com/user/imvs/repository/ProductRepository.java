package com.user.imvs.repository;

import com.user.imvs.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {

 @Override
 long count();

 List<Product> findByStockQuantityIsLessThanEqual(Integer stockQuantity);
 @Query("SELECT SUM(p.price * p.stockQuantity) FROM Product p")
 Double getTotalInventoryValuation();
 Optional<Product> findTopByOrderByCreatedAtDesc();
}
