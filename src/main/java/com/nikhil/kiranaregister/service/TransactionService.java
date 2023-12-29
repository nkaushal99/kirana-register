package com.nikhil.kiranaregister.service;

import com.nikhil.kiranaregister.dto.DailyReport;
import com.nikhil.kiranaregister.dto.DailyReportsResponse;
import com.nikhil.kiranaregister.dto.InterimResponse;
import com.nikhil.kiranaregister.dto.TransactionRequest;
import com.nikhil.kiranaregister.entity.Transaction;
import com.nikhil.kiranaregister.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionService {
  
  private final TransactionRepository transactionRepository;
  
  private final CurrencyConversionService currencyConversionService;
  
  /**
   * Records a transaction done in the Kirana Store
   *
   * @param request {@link TransactionRequest}
   * @return {@link InterimResponse}
   */
  @Transactional
  public InterimResponse<Transaction> recordTransaction(TransactionRequest request) {
    // Convert the currency to USD
    InterimResponse<Double> conversionResponse =
      currencyConversionService.convertToUSD(request.getAmount(), request.getCurrency());
    
    InterimResponse<Transaction> response = new InterimResponse<>();
    switch (conversionResponse.getStatus()) {
      case SUCCESS:
        Transaction transaction = Transaction.builder()
          .amount(conversionResponse.getData())
          .currency("USD")
          .type(request.getType())
          .customerName(request.getCustomerName())
          .transactionDate(request.getTransactionDate())
          .notes(request.getNotes())
          .build();
        response.setData(transactionRepository.save(transaction));
      default:
        response.setStatus(conversionResponse.getStatus());
    }
    return response;
  }
  
  /**
   * Extracts the transactions done between the given time frame or in last 1 day if startDate
   * and endDate not mentioned
   *
   * @param startDate {@link LocalDate} Date from which the transactions need to be listed
   * @param endDate {@link LocalDate} Date upto which the transactions need to be listed
   * @return {@link DailyReportsResponse}
   */
  public DailyReportsResponse getDailyReports(LocalDate startDate, LocalDate endDate) {
    endDate = endDate != null ? endDate : LocalDate.now();
    startDate = startDate != null ? startDate : endDate.minusDays(1);
    List<Transaction> transactions =
      transactionRepository.findByTransactionDateBetween(startDate, endDate);
    
    // Group transactions by date
    Map<LocalDate, List<Transaction>> groupedTransactions = transactions.stream()
      .collect(Collectors.groupingBy(Transaction::getTransactionDate));
    
    // Create daily reports
    List<DailyReport> dailyReports = groupedTransactions.entrySet().stream()
      .map(entry -> new DailyReport(entry.getKey(), entry.getValue()))
      .collect(Collectors.toList());
    
    return new DailyReportsResponse(dailyReports);
  }
}

