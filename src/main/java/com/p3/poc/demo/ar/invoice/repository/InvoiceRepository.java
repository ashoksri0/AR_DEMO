package com.p3.poc.demo.ar.invoice.repository;

import com.p3.poc.demo.ar.invoice.entity.Invoice;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

/**
 * Created by Suriyanarayanan K
 * on 08/03/20 11:32 PM.
 */
@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    List<Invoice> findAllByUsers_IdAndInvoiceDateBetweenOrderByInvoiceDate(Long id, Date startDate,Date endDate, Pageable pagable);
    List<Invoice> findAllByUsers_IdAndInvoiceDateBetweenOrderByInvoiceDateDesc(Long id, Date startDate,Date endDate, Pageable pagable);
}
