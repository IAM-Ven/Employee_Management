package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.dto.EmployeeDto;
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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {
    @Mock
    EmployeeRepository employeeRepository;
    @Mock
    CompanyRepository companyRepository;
    @InjectMocks
    EmployeeService employeeService;

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
        EmployeeNotFoundException employeeNotFoundException
                = assertThrows(EmployeeNotFoundException.class, () -> employeeService.getEmployee(1), "EmployeeNotFoundException");

        // then
        assertEquals("EmployeeNotFoundException", employeeNotFoundException.getMessage());
    }

    @Test
    public void should_return_1_employee_when_add_employee_given_1_employee_dto() {
        //given
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setId(1);
        employeeDto.setName("LLL");
        employeeDto.setAge(11);
        employeeDto.setGender("male");
        employeeDto.setCompanyId(1);
        when(companyRepository.findById(anyInt())).thenReturn(Optional.of(new Company()));
        //when
        employeeService.addEmployee(employeeDto);
        //then
        verify(employeeRepository, times(1)).save(any(Employee.class));
    }

    @Test
    public void should_throw_exception_when_add_employee_given_1_employee_dto_and_company_is_not_exist() {
        //given
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setId(1);
        employeeDto.setName("LLL");
        employeeDto.setAge(11);
        employeeDto.setGender("male");
        employeeDto.setCompanyId(1);
        when(companyRepository.findById(anyInt())).thenReturn(Optional.empty());
        //when
        CompanyNotFoundException companyNotFoundException
                = assertThrows(CompanyNotFoundException.class, () -> employeeService.addEmployee(employeeDto), "CompanyNotFoundException");
        //then
        assertEquals("CompanyNotFoundException", companyNotFoundException.getMessage());
    }
}