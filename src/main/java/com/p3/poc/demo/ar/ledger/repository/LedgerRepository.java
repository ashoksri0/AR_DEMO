package com.p3.poc.demo.ar.ledger.repository;

import com.p3.poc.demo.ar.ledger.entity.Ledger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

/**
 * Created by Suriyanarayanan K
 * on 08/03/20 11:44 PM.
 */
@Repository
public interface LedgerRepository extends JpaRepository<Ledger, Long> {
    List<Ledger> findAllByInvoice_Id(Long Id);
    List<Ledger> findAllByUsers_IdAndTranscationDateBetweenOrderByTranscationDateDesc(Long userId, Date startDate, Date endDate);
    List<Ledger> findAllByUsers_IdAndInvoice_IdAndTranscationDateBetweenOrderByTranscationDateDesc(Long userId,Long invoiceId, Date startDate,
                                                                                                   Date endDate);
    List<Ledger> findAllByUsers_IdOrderByTranscationDate(Long userID);
}
