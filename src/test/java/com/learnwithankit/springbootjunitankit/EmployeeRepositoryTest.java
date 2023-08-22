package com.learnwithankit.springbootjunitankit;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest // only scan repo and entity
public class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    public void saveEmployeeTest(){
        Employee employee = new Employee();
        employee.setName("ankit");
        employee.setEmail("a@a.com");

        employeeRepository.save(employee);

        Assertions.assertThat(employee.getId()).isGreaterThan(0);
    }


}
