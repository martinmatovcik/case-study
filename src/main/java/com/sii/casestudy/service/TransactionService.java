package com.sii.casestudy.service;

import com.sii.casestudy.model.Transaction;

import java.util.List;

public interface TransactionService {
  Transaction createTransaction(Transaction transaction);

  Transaction findTransactionById(int transactionId);

  List<Transaction> findAllTransactions();

  Transaction updateTransactionById(int transactionId, Transaction newTransaction);

  void deleteTransactionById(int transactionId);
}
