package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.entity.Company;
import com.thoughtworks.springbootemployee.entity.Employee;
import com.thoughtworks.springbootemployee.service.CompanyService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping
    public Page<Company> getCompanyByPage(@PageableDefault(size = 1) Pageable pageable, @RequestParam(defaultValue = "false") boolean unpaged) {
        if (unpaged) {
            return companyService.getCompaniesByPage(Pageable.unpaged());
        }
        return companyService.getCompaniesByPage(pageable);
    }

    @GetMapping("/{id}/employees")
    public List<Employee> getEmployeesByCompanyId(@PathVariable int id) {
        return companyService.getEmployeeUnderCompany(id);
    }

    @GetMapping("/{id}")
    public Company getCompany(@PathVariable int id) {
        return companyService.getCompany(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addCompany(@RequestBody Company company) {
        companyService.addCompany(company);
    }

    @PutMapping("/{id}")
    public void updateCompany(@PathVariable int id, @RequestBody Company company) {
        company.setCompanyId(id);
        companyService.updateCompany(company);
    }

    @DeleteMapping("/{id}")
    public void deleteCompany(@PathVariable int id) {
        companyService.deleteCompany(id);
    }
}
