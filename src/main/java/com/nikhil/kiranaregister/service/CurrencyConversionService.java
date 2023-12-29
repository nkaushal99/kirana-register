package com.nikhil.kiranaregister.service;

import com.nikhil.kiranaregister.dto.CurrencyConversionResponse;
import com.nikhil.kiranaregister.dto.InterimResponse;
import com.nikhil.kiranaregister.enums.ResponseStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@Slf4j
public class CurrencyConversionService {
  
  private final RestTemplate restTemplate;
  
  @Value("${currency-conversion-api.url}")
  private String currencyConversionApiUrl;
  
  /**
   * Convert the amount to USD
   * @param amount value of the money exchanged
   * @param fromCurrency currency of the money exchanged
   * @return {@link InterimResponse}
   */
  public InterimResponse<Double> convertToUSD(double amount, String fromCurrency) {
    
    InterimResponse<Double> response = new InterimResponse<>();
    
    // Make a request to the currency conversion API
    String apiUrl = new StringBuilder(currencyConversionApiUrl)
      .append("?base=")
      .append(fromCurrency)
      .append("&currencies=USD")
      .append("&amount=")
      .append(amount)
      .append("&places=3")
      .toString();
    try {
      CurrencyConversionResponse
        apiResponse = restTemplate.getForObject(apiUrl, CurrencyConversionResponse.class);
      
      if (apiResponse != null && apiResponse.isSuccess()) {
        response.setData(apiResponse.getRates().get("USD"));
        response.setStatus(ResponseStatus.SUCCESS);
      } else {
        response.setStatus(ResponseStatus.INVALID_INPUT);
      }
    } catch (ResourceAccessException rae) {
      log.error("Could not connect to the Forex API!", rae);
      rae.printStackTrace();
      response.setStatus(ResponseStatus.IO_ERROR);
    } catch (Exception e) {
      log.error("Error while converting currency!", e);
      e.printStackTrace();
      response.setStatus(ResponseStatus.SERVER_ERROR);
    }
    return response;
  }
}
