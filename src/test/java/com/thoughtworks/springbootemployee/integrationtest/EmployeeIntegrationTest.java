package com.thoughtworks.springbootemployee.integrationtest;

import com.thoughtworks.springbootemployee.entity.Company;
import com.thoughtworks.springbootemployee.entity.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeIntegrationTest {

  @Autowired MockMvc mockMvc;

  @Autowired EmployeeRepository employeeRepository;

  @Autowired CompanyRepository companyRepository;

  @AfterEach
  void clean() {
    employeeRepository.deleteAll();
    companyRepository.deleteAll();
  }

  @Test
  void should_return_1_employee_when_get_employee_given_1_employee() throws Exception {
    Company company = new Company();
    company.setName("cargosmart");
    company = companyRepository.save(company);

    Employee employee = new Employee();
    employee.setName("lester");
    employee.setAge(1);
    employee.setGender("male");
    employee.setCompany(company);

    employee = employeeRepository.save(employee);

    mockMvc
        .perform(get("/employees/" + employee.getId()))
        .andExpect(status().isOk())
        .andExpect(jsonPath("name").value("lester"));
  }

  @Test
  public void should_return_1_employee_when_add_employee_given_1_employee() throws Exception {
    Company company = new Company();
    company.setName("cargosmart");
    int companyId = companyRepository.save(company).getCompanyId();

    String employeeContent =
        String.format(
            "{\"name\":\"kiki\",\"gender\":\"male\",\"age\":88,\"companyId\":%d}", companyId);
    mockMvc
        .perform(
            post("/employees").contentType(MediaType.APPLICATION_JSON).content(employeeContent))
        .andExpect(status().isCreated());

    List<Employee> employees = employeeRepository.findAll();
    assertEquals(1, employees.size());
  }

  @Test
  void should_return_1_employee_when_get_employee_by_page_given_page_1_size_1() throws Exception {
    Company company = new Company();
    company.setName("cargosmart");
    company = companyRepository.save(company);

    Employee employee = new Employee();
    employee.setName("lester");
    employee.setAge(1);
    employee.setGender("male");
    employee.setCompany(company);
    employeeRepository.save(employee);

    Employee employee1 = new Employee();
    employee1.setName("kiki");
    employee1.setAge(2);
    employee1.setGender("female");
    employee1.setCompany(company);
    employeeRepository.save(employee1);

    mockMvc
        .perform(get("/employees?page=1&size=1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("numberOfElements").value(1));
  }

  @Test
  public void should_return_0_employee_when_delete_employee_given_1_employee() throws Exception {
    Company company = new Company();
    company.setName("cargosmart");
    companyRepository.save(company);

    Employee employee = new Employee();
    employee.setName("lester");
    employee.setAge(1);
    employee.setGender("male");
    employee.setCompany(company);
    employee = employeeRepository.save(employee);

    mockMvc.perform(delete("/employees/" + employee.getId())).andExpect(status().isOk());

    List<Employee> employees = employeeRepository.findAll();
    assertEquals(0, employees.size());
  }

  @Test
  public void should_return_employee_when_update_employee_given_1_employee() throws Exception {
    Company company = new Company();
    company.setName("cargosmart");
    int companyId = companyRepository.save(company).getCompanyId();

    Employee employee = new Employee();
    employee.setName("lester");
    employee.setAge(1);
    employee.setGender("male");
    employee.setCompany(company);
    employee = employeeRepository.save(employee);

    String employeeContent =
        String.format(
            "{\"name\":\"kiki\",\"gender\":\"male\",\"age\":88,\"companyId\":%d}", companyId);

    mockMvc
        .perform(
            put("/employees/" + employee.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(employeeContent))
        .andExpect(status().isOk());

    List<Employee> employees = employeeRepository.findAll();
    assertEquals("kiki", employees.get(0).getName());
    assertEquals(88, employees.get(0).getAge());
  }
}
