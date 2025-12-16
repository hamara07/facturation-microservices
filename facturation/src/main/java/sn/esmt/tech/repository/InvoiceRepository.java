package sn.esmt.tech.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sn.esmt.tech.entities.Invoice;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {



    List<Invoice> findByClientId(Long clientId);

    @Query("SELECT SUM(i.amount) FROM Invoice i WHERE i.client.id = :clientId")
    BigDecimal getTotalAmountByClient(@Param("clientId") Long clientId);

    @Query("SELECT SUM(i.amount) FROM Invoice i WHERE i.client.id = :clientId AND i.status = 'PENDING'")
    BigDecimal getTotalUnpaidAmountByClient(@Param("clientId") Long clientId);
}
