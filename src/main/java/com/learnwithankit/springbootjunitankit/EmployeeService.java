package com.learnwithankit.springbootjunitankit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public Employee saveEmployee(Employee employee){
        Employee savedEmployee = employeeRepository.save(employee);
        return savedEmployee;
    }

    public List<Employee> getEmployees(){
        return employeeRepository.findAll();
    }

    public Employee getEmployeeById(long id){
        return employeeRepository.findById(id).get();
    }

    public void deleteEmployee(long id){
        Employee employee = employeeRepository.findById(id).orElse(null);
        employeeRepository.delete(employee);
    }

    public Employee updateEmployee(Employee employee, long id){
        Employee existingEmployee = employeeRepository.findById(id).orElse(null);
        existingEmployee.setName(employee.getName());
        existingEmployee.setEmail(employee.getEmail());
        return employeeRepository.save(existingEmployee);
    }
}
