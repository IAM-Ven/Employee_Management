package com.thoughtworks.springbootemployee.dto;

import com.thoughtworks.springbootemployee.entity.Employee;

public class EmployeeResponseDto {
  private int id;
  private String name;
  private int age;
  private String gender;
  private int companyId;

  public void setId(int id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setAge(int age) {
    this.age = age;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

  public void setCompanyId(int companyId) {
    this.companyId = companyId;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public int getAge() {
    return age;
  }

  public String getGender() {
    return gender;
  }

  public int getCompanyId() {
    return companyId;
  }

  public static EmployeeResponseDto from(Employee employee) {
    EmployeeResponseDto employeeResponseDto = new EmployeeResponseDto();
    employeeResponseDto.setId(employee.getId());
    employeeResponseDto.setAge(employee.getAge());
    employeeResponseDto.setCompanyId(employee.getCompany().getCompanyId());
    employeeResponseDto.setName(employee.getName());
    employeeResponseDto.setGender(employee.getGender());
    return employeeResponseDto;
  }
}
