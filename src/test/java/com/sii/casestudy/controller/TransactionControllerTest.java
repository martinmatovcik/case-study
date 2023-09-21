package com.sii.casestudy.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sii.casestudy.model.Transaction;
import com.sii.casestudy.repository.TransactionRepository;
import com.sii.casestudy.service.TransactionServiceImpl;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@SpringBootTest
public class TransactionControllerTest {
  @Autowired TransactionController transactionController;
  @Autowired TransactionServiceImpl transactionService;
  @MockBean TransactionRepository transactionRepository;
  MockMvc mockMvc;
  ObjectMapper objectMapper = new ObjectMapper();

  @BeforeEach
  public void setup() {
    this.mockMvc = MockMvcBuilders.standaloneSetup(transactionController).build();
  }

  @Test
  public void createTransaction_successful() throws Exception {
    // Given
    Transaction testTransaction = createTestTransaction();
    Transaction savedTransaction = testTransaction.copy();
    savedTransaction.setId(1);

    when(transactionRepository.save(any())).thenReturn(savedTransaction);

    // When
    mockMvc
        .perform(
            post("/api/transactions")
                .content(objectMapper.writeValueAsString(testTransaction))
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .accept(MediaType.APPLICATION_JSON))
        // Then
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id", is(1)))
        .andExpect(jsonPath("$.type", is("some-type")))
        .andExpect(jsonPath("$.actor", is("some-actor")))
        .andExpect(jsonPath("$.data", is(Map.of("key1", "value1", "key2", "value2"))))
        .andDo(print());
  }

  @Test
  public void listById_successful() throws Exception {
    // Given
    Transaction testTransaction = createTestTransaction();
    testTransaction.setId(1);

    when(transactionRepository.save(any())).thenReturn(testTransaction);

    // When
    mockMvc
        .perform(
            get("/api/transactions/1")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .accept(MediaType.APPLICATION_JSON))
        // Then
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id", is(1)))
        .andExpect(jsonPath("$.type", is("some-type")))
        .andExpect(jsonPath("$.actor", is("some-actor")))
        .andExpect(jsonPath("$.data", is(Map.of("key1", "value1", "key2", "value2"))))
        .andDo(print());
  }

  @Test
  public void listAllTransactions_successful() throws Exception {
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
    mockMvc
        .perform(
            get("/api/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .accept(MediaType.APPLICATION_JSON))
        // Then
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.length()", is(2)))
        .andExpect(jsonPath("$[0].type", is("some-type-1")))
        .andExpect(jsonPath("$[0].actor", is("some-actor-1")))
        .andExpect(jsonPath("$[0].data", is(Map.of("key1-1", "value1-1", "key2-1", "value2-1"))))
        .andDo(print());
  }

  @Test
  public void updateTransaction_successful() throws Exception {
    // Given
    Transaction newTransaction = new Transaction();
    newTransaction.setId(1);
    Transaction transactionToUpdate = createTestTransaction();

    when(transactionRepository.getReferenceById(any())).thenReturn(transactionToUpdate);
    when(transactionRepository.save(any())).thenReturn(newTransaction);

    // When
    mockMvc
        .perform(
            put("/api/transactions/2")
                .content(objectMapper.writeValueAsString(newTransaction))
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .accept(MediaType.APPLICATION_JSON))
        // Then
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id", is(1)))
        .andDo(print());
  }

  private Transaction createTestTransaction() {
    return new Transaction(
        new Timestamp(System.currentTimeMillis()),
        "some-type",
        "some-actor",
        Map.of("key1", "value1", "key2", "value2"));
  }
}
