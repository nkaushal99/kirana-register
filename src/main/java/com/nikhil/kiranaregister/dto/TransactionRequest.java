package com.nikhil.kiranaregister.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class TransactionRequest {
  
  /**
   * amount must not be negative
   */
  @Positive
  @NotNull
  private double amount;
  
  /**
   * Current support for <em>INR</em> and <em>USD</em> only (case-insensitive)
   */
  @Pattern(regexp = "(?i)^INR$|^USD$", message = "only INR and USD supported")
  @NotBlank
  private String currency;
  
  /**
   * type can be either <em>credit</em> or <em>debit</em> (case-insensitive)
   */
  @Pattern(regexp = "(?i)^credit$|^debit$", message = "must either be credit or debit")
  @NotBlank
  private String type;
  
  /**
   * customerName cannot be blank
   */
  @NotBlank
  private String customerName;
  
  /**
   * transactionDate cannot be in future
   */
  @PastOrPresent
  @NotNull
  private LocalDate transactionDate;
  
  /**
   * Limit the length of notes upto 100 characters
   */
  @Size(max = 100)
  private String notes;
  
}
