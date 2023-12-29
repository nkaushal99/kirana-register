package com.nikhil.kiranaregister.dto;

import com.nikhil.kiranaregister.enums.ResponseStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InterimResponse<T> {
  T data;
  ResponseStatus status;
}
