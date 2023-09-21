package com.sii.casestudy.repository;

import com.sii.casestudy.model.Transaction;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
  Optional<Transaction> findById(int transactionId);

  void deleteById(int transactionId);

  @Override
  @NonNull
  Transaction getReferenceById(@NonNull Integer id);
}
