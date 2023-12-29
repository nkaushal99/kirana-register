package com.nikhil.kiranaregister.controller;

import com.nikhil.kiranaregister.api.TransactionApi;
import com.nikhil.kiranaregister.dto.DailyReportsResponse;
import com.nikhil.kiranaregister.dto.InterimResponse;
import com.nikhil.kiranaregister.dto.TransactionRequest;
import com.nikhil.kiranaregister.entity.Transaction;
import com.nikhil.kiranaregister.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/transaction")
@RequiredArgsConstructor
public class TransactionController implements TransactionApi {
  
  private final TransactionService transactionService;
  
  @Override
  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Transaction> recordTransaction(
    @RequestBody @Valid TransactionRequest request) {
    
    InterimResponse<Transaction> transactionResponse =
      transactionService.recordTransaction(request);
    
    return switch (transactionResponse.getStatus()) {
      case INVALID_INPUT -> new ResponseEntity<>(HttpStatus.BAD_REQUEST);
      case IO_ERROR -> new ResponseEntity<>(HttpStatus.FAILED_DEPENDENCY);
      case SUCCESS -> new ResponseEntity<>(transactionResponse.getData(), HttpStatus.CREATED);
      default -> new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    };
  }
  
  @Override
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<DailyReportsResponse> getTransactions(
    @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    LocalDate startDate,
    @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    LocalDate endDate) {
    DailyReportsResponse dailyReports = transactionService.getDailyReports(startDate, endDate);
    return new ResponseEntity<>(dailyReports, HttpStatus.OK);
  }
}

