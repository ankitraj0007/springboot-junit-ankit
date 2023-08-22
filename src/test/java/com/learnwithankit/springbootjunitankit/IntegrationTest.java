package com.learnwithankit.springbootjunitankit;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

//integration test of apis using random port and test db
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IntegrationTest {

    @Autowired
    private TestH2Repository testH2Repository;

    @LocalServerPort
    private int port;

    private String baseUrl = "http://localhost";

    private static RestTemplate restTemplate;

    @BeforeEach
    public void setUp() {
        baseUrl = baseUrl.concat(":").concat(port + "").concat("/employee");
    };

    @BeforeAll
    public static void init(){
        restTemplate = new RestTemplate();
    }

    @Test
    @Sql(statements = "delete from employees where id = 1", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testSaveEmployee(){
        Employee employee = new Employee(1,"ankittest","rajtest");
        Employee result = restTemplate.postForObject(baseUrl, employee, Employee.class);
        assertEquals("ankittest", result.getName());
    }

    @Test
    @Sql(statements = "insert into employees (id, name, email) values(2,'appy','bhale')", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "delete from employees where id = 2", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testGetEmployees(){
        List<Employee> employees = restTemplate.getForObject(baseUrl, List.class);
        assertEquals(1, employees.size());
        assertEquals(1, testH2Repository.findAll().size());
    }

    @Test
    @Sql(statements = "insert into employees (id, name, email) values(3,'rudra','raj')", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "delete from employees where id = 3", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testGetEmployeeById(){
        Employee employee = restTemplate.getForObject(baseUrl+"/{id}",Employee.class,3);
        assertAll(
                () -> assertNotNull(employee),
                () -> assertEquals(3, employee.getId())
        );
    }

    @Test
    @Sql(statements = "insert into employees (id, name, email) values(3,'rudra','raj')", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "delete from employees where id = 3", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testUpdateEmployee(){
        Employee employee = new Employee(3,"rudra updated","raj");
        restTemplate.put(baseUrl+"/update/{id}",employee,3);

        Employee employee1 = testH2Repository.findById(3l).get();
        assertEquals("rudra updated", employee1.getName());
    }

    @Test
    @Sql(statements = "insert into employees (id, name, email) values(3,'rudra','raj')", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void testDeleteEmployee(){
        assertEquals(1,testH2Repository.findAll().size());

        Employee employee = new Employee(3,"rudra updated","raj");
        restTemplate.delete(baseUrl+"/delete/{id}",employee.getId());

        assertEquals(0,testH2Repository.findAll().size());
    }
}
