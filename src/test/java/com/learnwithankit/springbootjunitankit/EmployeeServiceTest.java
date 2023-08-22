package com.learnwithankit.springbootjunitankit;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class EmployeeServiceTest {

    @Autowired
    private EmployeeService employeeService;

    @MockBean //to mock the data. (not using test db)
    private EmployeeRepository employeeRepository;

    @Test
    public void saveEmployee(){
        Employee employee = new Employee(1L,"ankit","a@a.com");
        when(employeeRepository.save(employee)).thenReturn(employee);

        assertEquals(employee, employeeService.saveEmployee(employee));
    }

    @Test
    public void getEmployeesTest(){
        //mocking the data. not hitting the db
        when(employeeRepository.findAll()).thenReturn(Stream.of(
                new Employee(1L,"ankit","a@a.com"),
                new Employee(2,"ram","r@r.com")).collect(Collectors.toList()));

        assertEquals(2, employeeService.getEmployees().size());
    }

    @Test
    public void getEmployeeByIdTest(){
        long id=1L;
        Employee employee = new Employee(1L,"ankit","a@a.com");
        Optional<Employee> optionalEmployee = Optional.of(employee);
        when(employeeRepository.findById(id)).thenReturn((Optional<Employee>) optionalEmployee);

        assertEquals(1L,employeeService.getEmployeeById(id).getId());
    }

    @Test
    public void deleteEmployeeTest(){
        Employee employee = new Employee(1L,"ankit","a@a.com");
        Optional<Employee> optionalEmployee = Optional.of(employee);
        when(employeeRepository.findById(employee.getId())).thenReturn((Optional<Employee>) optionalEmployee);

        employeeService.deleteEmployee(employee.getId());
        verify(employeeRepository,times(1)).delete(employee);
    }
}
