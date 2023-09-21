package com.sii.casestudy.controller;

import com.sii.casestudy.dto.TransactionDto;
import com.sii.casestudy.model.Transaction;
import com.sii.casestudy.service.TransactionService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {
  private final TransactionService transactionService;

  public TransactionController(TransactionService transactionService) {
    this.transactionService = transactionService;
  }

  @PostMapping
  public ResponseEntity<TransactionDto> createTransaction(
      @RequestBody TransactionDto transactionToCreateDto) {
    return ResponseEntity.status(201)
        .body(transactionService.createTransaction(transactionToCreateDto.toTransaction()).toDto());
  }

  @GetMapping("/{transactionId}")
  public ResponseEntity<TransactionDto> listTransactionById(@PathVariable int transactionId) {
    return ResponseEntity.status(200)
        .body(transactionService.findTransactionById(transactionId).toDto());
  }

  @GetMapping
  public ResponseEntity<List<TransactionDto>> listAllTransactions() {
    return ResponseEntity.status(200)
        .body(
            transactionService.findAllTransactions().stream()
                .map(Transaction::toDto)
                .collect(Collectors.toList()));
  }

  @PutMapping("/{transactionId}")
  public ResponseEntity<TransactionDto> updateTransaction(
      @PathVariable int transactionId, @RequestBody TransactionDto transactionToUpdateDto) {
    return ResponseEntity.status(200)
        .body(
            transactionService
                .updateTransactionById(transactionId, transactionToUpdateDto.toTransaction())
                .toDto());
  }

  @DeleteMapping("/{transactionId}")
  public ResponseEntity<String> deleteTransaction(@PathVariable int transactionId) {
    transactionService.deleteTransactionById(transactionId);
    return ResponseEntity.status(200).body("Deleted successfully.");
  }
}
