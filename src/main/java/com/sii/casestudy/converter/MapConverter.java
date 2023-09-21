package com.sii.casestudy.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.MapType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.HashMap;
import java.util.Map;

@Converter
public class MapConverter implements AttributeConverter<Map<String, String>, String> {

  @Override
  public String convertToDatabaseColumn(Map<String, String> map) {
    // Convert the Map to a JSON or other suitable format and return it as a String.
    try {
      return new ObjectMapper().writeValueAsString(map);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public Map<String, String> convertToEntityAttribute(String dbData) {
    MapType type =
        TypeFactory.defaultInstance().constructMapType(HashMap.class, String.class, String.class);
      try {
          return new ObjectMapper().readValue(dbData, type);
      } catch (JsonProcessingException e) {
          throw new RuntimeException(e);
      }
  }
}
