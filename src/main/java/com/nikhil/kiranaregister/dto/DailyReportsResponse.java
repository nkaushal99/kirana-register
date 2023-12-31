package com.nikhil.kiranaregister.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class DailyReportsResponse {
  private List<DailyReport> dailyReports;
}
