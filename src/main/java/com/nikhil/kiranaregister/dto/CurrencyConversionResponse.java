package com.nikhil.kiranaregister.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class CurrencyConversionResponse {
  private boolean success;
  private Map<String, Double> rates;
}

