package com.nikhil.kiranaregister.repository;

import com.nikhil.kiranaregister.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
  
  List<Transaction> findByTransactionDateBetween(LocalDate startDate, LocalDate endDate);
  
}
