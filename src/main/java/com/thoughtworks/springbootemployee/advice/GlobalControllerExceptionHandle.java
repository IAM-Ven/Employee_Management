package com.thoughtworks.springbootemployee.advice;

import com.thoughtworks.springbootemployee.exception.CompanyNotFoundException;
import com.thoughtworks.springbootemployee.exception.EmployeeNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
@ResponseBody
public class GlobalControllerExceptionHandle {

  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ExceptionHandler(CompanyNotFoundException.class)
  public String handleCompanyNotFound(CompanyNotFoundException exception) {
    return exception.getMessage();
  }

  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ExceptionHandler(EmployeeNotFoundException.class)
  public String handleEmployeeNotFound(EmployeeNotFoundException exception) {
    return exception.getMessage();
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public List<String> handleValidation(MethodArgumentNotValidException exception) {
    return exception.getBindingResult().getFieldErrors().stream()
        .map(fieldError -> fieldError.getField() + " : " + fieldError.getDefaultMessage())
        .collect(Collectors.toList());
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(Exception.class)
  public void handleOthers() {}
}
