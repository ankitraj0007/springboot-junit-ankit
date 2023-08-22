package com.learnwithankit.springbootjunitankit;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TestH2Repository extends JpaRepository<Employee,Long> {
}
