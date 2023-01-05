package com.billing.repository;

import com.billing.model.Product;
import com.billing.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByUser(Long email);

    Optional<Product> findByName(String productName);

    Optional<Product> findByUserAndName(User user, String name);

    @Query("select p.id from Product p where p.user.id = ?1")
    List<Long> getProductIdListByuser(Long userId);
}
