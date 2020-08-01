package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.dto.EmployeeRequestDto;
import com.thoughtworks.springbootemployee.entity.Company;
import com.thoughtworks.springbootemployee.entity.Employee;
import com.thoughtworks.springbootemployee.exception.CompanyNotFoundException;
import com.thoughtworks.springbootemployee.exception.EmployeeNotFoundException;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {
  @Mock EmployeeRepository employeeRepository;
  @Mock CompanyRepository companyRepository;
  @InjectMocks EmployeeService employeeService;

  @Test
  public void should_get_1_employee_when_get_employee_given_employee_id_1() {
    // given
    int employeeId = 1;
    Employee employee = new Employee();
    when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));

    // when
    Employee getEmployee = employeeService.getEmployee(employeeId);

    // then
    assertNotNull(getEmployee);
  }

  @Test
  public void should_throw_exception_when_get_employee_given_employee_id_1() {
    // given
    int employeeId = 1;
    Employee employee = new Employee();
    when(employeeRepository.findById(employeeId)).thenReturn(Optional.empty());
    // when
    EmployeeNotFoundException employeeNotFoundException =
        assertThrows(EmployeeNotFoundException.class, () -> employeeService.getEmployee(1));

    // then
    assertEquals("EmployeeNotFoundException", employeeNotFoundException.getMessage());
  }

  @Test
  public void should_return_1_employee_when_add_employee_given_1_employee_request_dto() {
    // given
    Company company = new Company();

    EmployeeRequestDto employeeRequestDto = new EmployeeRequestDto();
    employeeRequestDto.setName("LLL");
    employeeRequestDto.setAge(11);
    employeeRequestDto.setGender("male");
    employeeRequestDto.setCompanyId(1);

    Employee employee = new Employee();
    employee.setName("LLL");
    employee.setAge(11);
    employee.setGender("male");
    employee.setCompany(company);

    when(companyRepository.findById(anyInt())).thenReturn(Optional.of(company));
    when(employeeRepository.save(any(Employee.class))).thenReturn(employee);
    // when
    Employee employeeSaved = employeeService.addEmployee(employeeRequestDto);
    // then
    assertEquals("LLL", employeeSaved.getName());
  }

  @Test
  public void
      should_throw_exception_when_add_employee_given_1_employee_dto_and_company_is_not_exist() {
    // given
    EmployeeRequestDto employeeRequestDto = new EmployeeRequestDto();
    employeeRequestDto.setName("LLL");
    employeeRequestDto.setAge(11);
    employeeRequestDto.setGender("male");
    employeeRequestDto.setCompanyId(1);
    when(companyRepository.findById(anyInt())).thenReturn(Optional.empty());
    // when
    CompanyNotFoundException companyNotFoundException =
        assertThrows(
            CompanyNotFoundException.class, () -> employeeService.addEmployee(employeeRequestDto));
    // then
    assertEquals("CompanyNotFoundException", companyNotFoundException.getMessage());
  }

  @Test
  public void should_return_employee_when_update_employee_given_1_employee_request_dto() {
    // given
    Company company = new Company();

    int id = 1;
    Employee employee = new Employee();
    employee.setName("LLL");
    employee.setAge(11);
    employee.setGender("male");

    EmployeeRequestDto employeeRequestDto = new EmployeeRequestDto();
    employeeRequestDto.setName("LLL");
    employeeRequestDto.setAge(11);
    employeeRequestDto.setGender("male");
    employeeRequestDto.setCompanyId(1);
    when(companyRepository.findById(anyInt())).thenReturn(Optional.of(company));
    // when
    Employee updateEmployee = employeeService.updateEmployee1(id, employeeRequestDto);
    // then
    assertEquals("LLL", updateEmployee.getName());
  }

  @Test
  public void should_return_employee_when_employee_by_gender_given_male() {
    // given
    Employee employee = new Employee();
    employee.setGender("male");
    when(employeeRepository.findByGender("male")).thenReturn(Collections.singletonList(employee));
    // when
    List<Employee> maleEmployee = employeeService.getEmployeeByGender("male");
    // then
    assertEquals(1, maleEmployee.size());
  }

  @Test
  public void
      should_return_1_employee_when_get_employees_by_page_and_given_page_0_size_1_and_1_employee() {
    // given
    Employee employee = new Employee();
    Pageable pageable = PageRequest.of(0, 1, Sort.Direction.DESC, "name");
    when(employeeRepository.findAll(pageable))
        .thenReturn(new PageImpl<>(Collections.singletonList(employee)));
    // when
    Page<Employee> employeePage = employeeService.getEmployeeByPage(pageable);
    // then
    assertEquals(1, employeePage.getNumberOfElements());
  }

  @Test
  public void
      should_return_0_employee_when_get_employees_by_page_and_given_page_0_size_1_and_0_employee() {
    // given
    Pageable pageable = PageRequest.of(0, 1, Sort.Direction.DESC, "name");
    when(employeeRepository.findAll(pageable)).thenReturn(new PageImpl<>(new ArrayList<>()));
    // when
    Page<Employee> employeePage = employeeService.getEmployeeByPage(pageable);
    // then
    assertEquals(0, employeePage.getNumberOfElements());
  }
}
