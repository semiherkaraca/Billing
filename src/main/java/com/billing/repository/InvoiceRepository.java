package com.billing.repository;

import com.billing.model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

    Optional<Invoice> findByBillNo(String billNo);

    @Query("select i from Invoice i Where i.product.id in (?1)")
    List<Invoice> findByProduct(List<Long> productIds);
}
