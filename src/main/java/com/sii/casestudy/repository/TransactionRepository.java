package com.sii.casestudy.repository;

import com.sii.casestudy.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
  Optional<Transaction> findById(int transactionId);

  void deleteById(int transactionId);

  @Override
  @NonNull
  Transaction getReferenceById(@NonNull Integer id);
}
