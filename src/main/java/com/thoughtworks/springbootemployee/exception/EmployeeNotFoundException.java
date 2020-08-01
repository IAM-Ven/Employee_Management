package com.thoughtworks.springbootemployee.exception;

public class EmployeeNotFoundException extends RuntimeException {
  @Override
  public String getMessage() {
    return "EmployeeNotFoundException";
  }
}
