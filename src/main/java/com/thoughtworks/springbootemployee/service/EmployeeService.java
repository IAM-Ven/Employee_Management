package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.dto.EmployeeDto;
import com.thoughtworks.springbootemployee.dto.EmployeeRequestDto;
import com.thoughtworks.springbootemployee.entity.Company;
import com.thoughtworks.springbootemployee.entity.Employee;
import com.thoughtworks.springbootemployee.exception.CompanyNotFoundException;
import com.thoughtworks.springbootemployee.exception.EmployeeNotFoundException;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final CompanyRepository companyRepository;

    public EmployeeService(EmployeeRepository employeeRepository, CompanyRepository companyRepository) {
        this.employeeRepository = employeeRepository;
        this.companyRepository = companyRepository;
    }

    public Employee getEmployee(int id) {
        return employeeRepository.findById(id).orElseThrow(EmployeeNotFoundException::new);
    }

    public List<Employee> getEmployeeByGender(String gender) {
        return employeeRepository.findByGender(gender);
    }

    public Page<Employee> getEmployeeByPage(Pageable pageable) {
        return employeeRepository.findAll(pageable);
    }


    public Employee addEmployee(EmployeeRequestDto employeeRequestDto) {
        Company company = companyRepository.findById(employeeRequestDto.getCompanyId()).orElseThrow(CompanyNotFoundException::new);
        Employee employee = new Employee();
        employee.setGender(employeeRequestDto.getGender());
        employee.setAge(employeeRequestDto.getAge());
        employee.setName(employeeRequestDto.getName());
        employee.setCompany(company);
        return employeeRepository.save(employee);
    }

    public Employee updateEmployee(EmployeeDto employeeDto) {
        Company company = companyRepository.findById(employeeDto.getCompanyId()).get();
        Employee employee = employeeDto.to();
        employee.setCompany(company);
        return employeeRepository.save(employee);
    }

}
