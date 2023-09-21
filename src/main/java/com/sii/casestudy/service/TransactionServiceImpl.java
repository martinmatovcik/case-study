package com.sii.casestudy.service;

import com.sii.casestudy.model.Transaction;
import com.sii.casestudy.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImpl implements TransactionService {
  private final TransactionRepository transactionRepository;

  public TransactionServiceImpl(TransactionRepository transactionRepository) {
    this.transactionRepository = transactionRepository;
  }

  @Override
  public Transaction createTransaction(Transaction transaction) {
    return transactionRepository.save(transaction);
  }

  @Override
  public Transaction findTransactionById(int transactionId) {
    return transactionByIdOrThrowNotSuchElementException(transactionId);
  }

  @Override
  public List<Transaction> findAllTransactions() {
    return transactionRepository.findAll();
  }

  @Override
  @Transactional
  public Transaction updateTransactionById(int transactionToUpdateId, Transaction newTransaction) {
    Transaction transactionToUpdate = transactionRepository.getReferenceById(transactionToUpdateId);
    transactionToUpdate.setId(newTransaction.getId());
    transactionToUpdate.setTimestamp(newTransaction.getTimestamp());
    transactionToUpdate.setType(newTransaction.getType());
    transactionToUpdate.setActor(newTransaction.getActor());
    transactionToUpdate.setData(newTransaction.getData());
    return transactionRepository.save(transactionToUpdate);
  }

  @Override
  public void deleteTransactionById(int transactionId) {
    transactionByIdOrThrowNotSuchElementException(transactionId);
    transactionRepository.deleteById(transactionId);
  }

  private Transaction transactionByIdOrThrowNotSuchElementException(int transactionId) {
    return transactionRepository
        .findById(transactionId)
        .orElseThrow(() -> new NoSuchElementException("Transaction was not found."));
  }
}
