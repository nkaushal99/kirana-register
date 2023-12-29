package com.nikhil.kiranaregister.api;

import com.nikhil.kiranaregister.dto.DailyReportsResponse;
import com.nikhil.kiranaregister.dto.TransactionRequest;
import com.nikhil.kiranaregister.entity.Transaction;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Tag(name = "Transaction", description = "Transaction API")
public interface TransactionApi {
  
  @Operation(summary = "Record a transaction")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "201", description = "Transaction recorded successfully"),
    @ApiResponse(responseCode = "400", description = "Bad request"),
    @ApiResponse(responseCode = "424", description = "Third Party Error"),
    @ApiResponse(responseCode = "500", description = "Internal Server Error")
  })
  ResponseEntity<Transaction> recordTransaction(@RequestBody TransactionRequest request);
  
  @Operation(summary = "Get transactions with daily reports")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Successfully retrieved transactions"),
    @ApiResponse(responseCode = "400", description = "Bad request")
  })
  ResponseEntity<DailyReportsResponse> getTransactions(
    @Parameter(in = ParameterIn.QUERY,
      description = "Date from which the transactions need to be listed. If not specified, "
        + "the previous date of endDate will be taken as the value",
      example = "2023-12-29")
    @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    LocalDate startDate,
    
    @Parameter(in = ParameterIn.QUERY,
      description = "Date upto which the transactions need to be listed. If not specified, "
        + "current date will be taken as the value",
      example = "2023-12-29")
    @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    LocalDate endDate);
}
