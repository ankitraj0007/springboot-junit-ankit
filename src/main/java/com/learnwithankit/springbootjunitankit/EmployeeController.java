package com.learnwithankit.springbootjunitankit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping
    public Employee saveEmployee(@RequestBody Employee employee){
        Employee savedEmployee = employeeService.saveEmployee(employee);
        return savedEmployee;
    }

    @GetMapping
    public List<Employee> getEmployees(){
        List<Employee> employees = employeeService.getEmployees();
        return employees;
    }

    @GetMapping("/{id}")
    public Employee getById(@PathVariable long id){
        Employee employee = employeeService.getEmployeeById(id);
        return employee;
    }

    @PutMapping("update/{id}")
    public Employee updateEmployee(@RequestBody Employee employee, @PathVariable long id){
        return employeeService.updateEmployee(employee, id);
    }

    @DeleteMapping("delete/{id}")
    public String deleteEmployee(@PathVariable long id){
        employeeService.deleteEmployee(id);
        return "Employee deleted";
    }
}
