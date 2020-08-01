package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.entity.Company;
import com.thoughtworks.springbootemployee.entity.Employee;
import com.thoughtworks.springbootemployee.exception.CompanyNotFoundException;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {

  @Autowired CompanyRepository companyRepository;

  public Page<Company> getCompaniesByPage(Pageable pageable) {
    return companyRepository.findAll(pageable);
  }

  public List<Employee> getEmployeeUnderCompany(int companyId) {
    return companyRepository
        .findById(companyId)
        .orElseThrow(CompanyNotFoundException::new)
        .getEmployees();
  }

  public Company getCompany(int id) {
    return companyRepository.findById(id).orElseThrow(CompanyNotFoundException::new);
  }

  public Company addCompany(Company company) {
    return companyRepository.save(company);
  }

  public Company updateCompany(Company company) {
    return companyRepository.save(company);
  }

  public void deleteCompany(int id) {
    companyRepository.deleteById(id);
  }
}
