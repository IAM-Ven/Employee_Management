package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.entity.Company;
import com.thoughtworks.springbootemployee.entity.Employee;
import com.thoughtworks.springbootemployee.exception.CompanyNotFoundException;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.util.*;

import static java.util.Optional.of;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CompanyServiceTest {

  @Mock CompanyRepository companyRepository;
  @InjectMocks CompanyService companyService;

  @Test
  public void
      should_be_return_2_employees_when_get_employees_under_company_given_2_employees_and_1_company() {
    // given
    Company company = new Company();
    company.setCompanyId(1);
    company.setEmployees(new ArrayList<>());
    Employee employee1 = new Employee();
    Employee employee2 = new Employee();
    employee1.setCompany(company);
    employee2.setCompany(company);
    company.getEmployees().add(employee1);
    company.getEmployees().add(employee2);
    when(companyRepository.findById(1)).thenReturn(of(company));
    // when
    List<Employee> employees = companyService.getEmployeeUnderCompany(company.getCompanyId());
    // then
    assertEquals(2, employees.size());
  }

  @Test
  public void should_be_return_0_employees_when_get_employees_under_company_given_1_company() {
    // given
    Company company = new Company();
    company.setCompanyId(1);
    company.setEmployees(new ArrayList<>());
    when(companyRepository.findById(1)).thenReturn(of(company));
    // when
    List<Employee> employees = companyService.getEmployeeUnderCompany(company.getCompanyId());
    // then
    assertEquals(0, employees.size());
  }

  @Test
  public void
      should_be_return_exception_employees_when_get_employees_under_company_given_no_company() {
    // given
    when(companyRepository.findById(1)).thenReturn(Optional.empty());
    // when
    CompanyNotFoundException companyNotFoundException =
        assertThrows(
            CompanyNotFoundException.class,
            () -> companyService.getEmployeeUnderCompany(1),
            "companyNotFound");
    // then
    assertEquals("CompanyNotFoundException", companyNotFoundException.getMessage());
  }

  @Test
  public void should_return_1_company_when_add_company_given_1_company() {
    // given
    Company company = new Company();
    when(companyRepository.save(any(Company.class))).thenReturn(company);
    // when
    Company addCompany = companyService.addCompany(company);
    // then
    assertNotNull(addCompany);
  }

  @Test
  public void should_return_1_company_when_update_company_given_1_company() {
    // given
    Company company = new Company();
    when(companyRepository.save(any(Company.class))).thenReturn(company);
    // when
    Company updateCompany = companyService.updateCompany(company);
    // then
    assertNotNull(updateCompany);
  }

  @Test
  public void should_invoke_delete_company_once_when_delete_company_given_company_id_1() {
    // given
    int companyId = 1;
    // when
    companyService.deleteCompany(companyId);
    // then
    verify(companyRepository, times(1)).deleteById(anyInt());
  }

  @Test
  public void should_throw_exception_when_get_company_given_company_id_1() {
    // given
    int companyId = 1;
    when(companyRepository.findById(companyId)).thenReturn(Optional.empty());

    // when
    CompanyNotFoundException companyNotFoundException =
        assertThrows(CompanyNotFoundException.class, () -> companyService.getCompany(1));

    // then
    assertEquals("CompanyNotFoundException", companyNotFoundException.getMessage());
  }

  @Test
  public void should_get_1_company_when_get_company_given_company_id_1() {
    // given
    int companyId = 1;
    Company company = new Company();
    when(companyRepository.findById(companyId)).thenReturn(of(company));

    // when
    Company getCompany = companyService.getCompany(companyId);

    // then
    assertNotNull(getCompany);
  }

  @Test
  void should_get_1_company_when_get_company_by_page_given_page_0_size_1_and_1_company() {
    // given
    Company company = new Company();
    company.setName("oocl");
    Pageable pageable = PageRequest.of(0, 1, Sort.Direction.DESC, "name");

    when(companyRepository.findAll(pageable)).thenReturn(new PageImpl<>(Collections.singletonList(company)));
    // when
    Page<Company> companyPage = companyService.getCompaniesByPage(pageable);
    // then
    assertEquals(1, companyPage.getNumberOfElements());
  }
}
