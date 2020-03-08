package com.p3.poc.demo.ar.ledger.repository;

import com.p3.poc.demo.ar.ledger.entity.Ledger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Suriyanarayanan K
 * on 08/03/20 11:44 PM.
 */
@Repository
public interface LedgerRepository extends JpaRepository<Ledger, Long> {
}
