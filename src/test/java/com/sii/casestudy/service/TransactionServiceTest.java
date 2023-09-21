package com.sii.casestudy.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.sii.casestudy.model.Transaction;
import com.sii.casestudy.repository.TransactionRepository;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class TransactionServiceTest {
  @Autowired TransactionServiceImpl transactionService;

  @MockBean TransactionRepository transactionRepository;

  @Test
  public void createTransaction_successful() {
    // Given
    Transaction testTransaction = createTestTransaction();
    Transaction savedTransaction = testTransaction.copy();
    savedTransaction.setId(1);

    when(transactionRepository.save(testTransaction)).thenReturn(savedTransaction);

    // When
    Transaction createdTransaction = transactionService.createTransaction(testTransaction);

    // Then
    assertEquals(savedTransaction, createdTransaction);
  }

  @Test
  public void findTransactionById_successful() {
    // Given
    Transaction testTransaction = createTestTransaction();
    int transactionToFindId = 1;

    when(transactionRepository.findById(transactionToFindId))
        .thenReturn(Optional.of(testTransaction));

    // When
    Transaction findedTransaction = transactionService.findTransactionById(transactionToFindId);

    // Then
    assertEquals(testTransaction, findedTransaction);
  }

  @Test
  public void findTransactionById_shouldThrowExceptionWhenCannotFindTransaction() {
    // Given
    when(transactionRepository.findById(any()))
        .thenThrow(new NoSuchElementException("Transaction was not found."));

    // When
    Exception exception =
        assertThrows(NoSuchElementException.class, () -> transactionService.findTransactionById(1));

    // Then
    assertThat(exception.getMessage()).contains("Transaction was not found.");
  }

  @Test
  public void findAllTransactions_successful() {
    // Given
    List<Transaction> transactions =
        List.of(
            new Transaction(
                new Timestamp(System.currentTimeMillis()),
                "some-type-1",
                "some-actor-1",
                Map.of("key1-1", "value1-1", "key2-1", "value2-1")),
            new Transaction(
                new Timestamp(System.currentTimeMillis()),
                "some-type-2",
                "some-actor-2",
                Map.of("key1-2", "value1-2", "key2-2", "value2-2")));

    when(transactionRepository.findAll()).thenReturn(transactions);

    // When
    List<Transaction> findedTransactions = transactionService.findAllTransactions();

    // Then
    assertEquals(transactions.size(), findedTransactions.size());
  }

  @Test
  public void updateTransactionById_successful() {
    // Given
    Transaction newTransaction = new Transaction();
    newTransaction.setId(1);
    Transaction transactionToUpdate = createTestTransaction();

    when(transactionRepository.getReferenceById(any())).thenReturn(transactionToUpdate);
    when(transactionRepository.save(any())).thenReturn(newTransaction);

    // When
    Transaction updatedTransaction = transactionService.updateTransactionById(1, newTransaction);

    // Then
    assertEquals(1, updatedTransaction.getId());
  }

  private Transaction createTestTransaction() {
    return new Transaction(
        new Timestamp(System.currentTimeMillis()),
        "some-type",
        "some-actor",
        Map.of("key1", "value1", "key2", "value2"));
  }
}
