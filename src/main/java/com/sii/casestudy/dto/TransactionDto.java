package com.sii.casestudy.dto;

import com.sii.casestudy.model.Transaction;
import jakarta.annotation.Nullable;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

public class TransactionDto {
  @Nullable private Integer id;
  @Nullable private Timestamp timestamp = new Timestamp(System.currentTimeMillis());
  private String type;
  private String actor;
  @Nullable private Map<String, String> data = new HashMap<>();

  @Nullable
  public Integer getId() {
    return id;
  }

  public void setId(@Nullable Integer id) {
    this.id = id;
  }

  public Timestamp getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Timestamp timestamp) {
    this.timestamp = timestamp;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getActor() {
    return actor;
  }

  public void setActor(String actor) {
    this.actor = actor;
  }

  public Map<String, String> getData() {
    return data;
  }

  public void setData(Map<String, String> data) {
    this.data = data;
  }

  public Transaction toTransaction() {
    Transaction transaction = new Transaction();
    if (this.id != null) transaction.setId(this.id);
    transaction.setTimestamp(new Timestamp(System.currentTimeMillis()));
    transaction.setType(this.type);
    transaction.setActor(this.actor);
    transaction.setData(this.data);
    return transaction;
  }
}
