package com.sii.casestudy.model;

import com.sii.casestudy.converter.MapConverter;
import com.sii.casestudy.dto.TransactionDto;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Entity
public class Transaction {
  @Id @GeneratedValue private int id;
  private Timestamp timestamp = new Timestamp(System.currentTimeMillis());
  private String type;
  private String actor;

  @Convert(converter = MapConverter.class)
  private Map<String, String> data = new HashMap<>();

  public Transaction() {}

  public Transaction(Timestamp timestamp, String type, String actor, Map<String, String> data) {
    this.timestamp = timestamp;
    this.type = type;
    this.actor = actor;
    this.data = data;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
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

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Transaction that = (Transaction) o;
    return id == that.id
        && Objects.equals(timestamp, that.timestamp)
        && Objects.equals(type, that.type)
        && Objects.equals(actor, that.actor)
        && Objects.equals(data, that.data);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, timestamp, type, actor, data);
  }

  public TransactionDto toDto() {
    TransactionDto transactionDto = new TransactionDto();
    transactionDto.setId(this.id);
    transactionDto.setTimestamp(this.timestamp);
    transactionDto.setType(this.type);
    transactionDto.setActor(this.actor);
    transactionDto.setData(this.data);
    return transactionDto;
  }

  public Transaction copy() {
    Transaction transactionCopy = new Transaction();
    transactionCopy.setId(this.id);
    transactionCopy.setTimestamp(this.timestamp);
    transactionCopy.setType(this.type);
    transactionCopy.setActor(this.actor);
    transactionCopy.setData(this.data);
    return transactionCopy;
  }
}
