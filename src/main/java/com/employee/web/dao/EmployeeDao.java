package com.employee.web.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.employee.web.model.Employee;

@Repository
public interface EmployeeDao extends JpaRepository<Employee, Integer> {

    // Find employees by first name
    List<Employee> findByFirstName(String firstName);

    // Find employee by email
    Optional<Employee> findByEmail(String email);

    // Find employees by first name containing a substring (case-insensitive)
    List<Employee> findByFirstNameContainingIgnoreCase(String query);

    // Find employees by last name containing a substring (case-insensitive)
    List<Employee> findByLastNameContainingIgnoreCase(String query);

    // Find employees by both first name and last name
    List<Employee> findByFirstNameAndLastName(String firstName, String lastName);

    // Find distinct first names (for unique first names) using a custom query
    @Query("SELECT DISTINCT e.firstName FROM Employee e")
    List<String> findDistinctFirstName();

    // Additional methods for searching by first name and last name
    List<Employee> findByLastNameContaining(String query);
    List<Employee> findByFirstNameContaining(String query);
    boolean existsByEmail(String email);


}
