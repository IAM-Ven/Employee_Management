package com.thoughtworks.springbootemployee.dto;

import com.thoughtworks.springbootemployee.entity.Employee;

public class EmployeeDto {
  private int id;
  private String name;
  private int age;
  private String gender;
  private int companyId;

  public Employee to() {
    Employee employee = new Employee();
    employee.setId(this.id);
    employee.setName(this.name);
    employee.setAge(this.age);
    employee.setGender(this.gender);
    return employee;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }

  public String getGender() {
    return gender;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

  public int getCompanyId() {
    return companyId;
  }

  public void setCompanyId(int companyId) {
    this.companyId = companyId;
  }
}
