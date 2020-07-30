package com.thoughtworks.springbootemployee.dto;

public class EmployeeRequestDto {

    private String name;
    private int age;
    private int companyId;
    private String gender;

    public EmployeeRequestDto() {
    }

    public EmployeeRequestDto(String name, int age, int companyId, String gender) {
        this.name = name;
        this.age = age;
        this.companyId = companyId;
        this.gender = gender;
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

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
