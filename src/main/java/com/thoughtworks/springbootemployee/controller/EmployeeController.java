package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.dto.EmployeeDto;
import com.thoughtworks.springbootemployee.dto.EmployeeRequestDto;
import com.thoughtworks.springbootemployee.entity.Employee;
import com.thoughtworks.springbootemployee.service.EmployeeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/{id}")
    public Employee getEmployee(@PathVariable int id) {
        return employeeService.getEmployee(id);
    }

    @GetMapping(params = "gender")
    public List<Employee> findByGender(@RequestParam String gender) {
        return employeeService.getEmployeeByGender(gender);
    }

    @GetMapping
    public Page<Employee> getEmployeeByPage(@PageableDefault(size = 1) Pageable pageable, @RequestParam(defaultValue = "false") boolean unpaged) {
        if (unpaged) {
            return employeeService.getEmployeeByPage(Pageable.unpaged());
        }
        return employeeService.getEmployeeByPage(pageable);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addEmployee(@RequestBody EmployeeRequestDto employeeRequestDto) {
         employeeService.addEmployee(employeeRequestDto);
    }

    @PutMapping("/{id}")
    public void updateEmployee(@RequestBody EmployeeDto employeeDto) {
        employeeService.updateEmployee(employeeDto);
    }
}
