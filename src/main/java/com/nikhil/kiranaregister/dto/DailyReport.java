package com.nikhil.kiranaregister.dto;

import com.nikhil.kiranaregister.entity.Transaction;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class DailyReport {
  private LocalDate date;
  private List<Transaction> transactions;
}

