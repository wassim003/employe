package com.employee.web.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.employee.web.dao.EmployeeDao;
import com.employee.web.exception.EmployeeNotFoundException;
import com.employee.web.exception.InvalidEmailException;
import com.employee.web.model.Employee;

@Service
public class EmployeeService {

    private final EmployeeDao employeeDao;

    public EmployeeService(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }

    public List<Employee> getAllEmployees() {
        return employeeDao.findAll();
    }

    public Employee getEmployeeById(Integer id) {
        Optional<Employee> employee = employeeDao.findById(id);
        return employee.orElseThrow(() -> new EmployeeNotFoundException("Employee not found with ID: " + id));
    }

    // Ajout d'un employé avec vérification des doublons (par email) et validation de l'email
    public Employee addEmployee(Employee employee) {
        validateEmail(employee.getEmail()); // Validation du format de l'email
        if (employeeDao.existsByEmail(employee.getEmail())) {
            throw new RuntimeException("L'employé avec cet email existe déjà");
        }
        return employeeDao.save(employee);
    }

    public Employee updateEmployee(Integer id, Employee employee) {
        if (!employeeDao.existsById(id)) {
            throw new EmployeeNotFoundException("Employee not found with ID: " + id);
        }
        employee.setId(id);
        return employeeDao.save(employee);
    }

    public String deleteEmployee(Integer id) {
        if (employeeDao.existsById(id)) {
            employeeDao.deleteById(id);
            return "Employee with ID " + id + " has been deleted.";
        } else {
            throw new EmployeeNotFoundException("Employee not found with ID: " + id);
        }
    }

    // Recherche par prénom
    public List<Employee> getEmployeesByFirstName(String firstName) {
        return employeeDao.findByFirstName(firstName);
    }

    // Recherche par email
    public Employee getEmployeeByEmail(String email) {
        return employeeDao.findByEmail(email)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with email: " + email));
    }

    // Recherche par prénom contenant une sous-chaîne
    public List<Employee> searchEmployeesByFirstName(String query) {
        return employeeDao.findByFirstNameContaining(query);
    }

    // Recherche par nom de famille contenant une sous-chaîne
    public List<Employee> searchEmployeesByLastName(String query) {
        return employeeDao.findByLastNameContaining(query);
    }

    // Recherche par nom complet (prénom + nom)
    public List<Employee> searchEmployeesByFullName(String firstName, String lastName) {
        return employeeDao.findByFirstNameAndLastName(firstName, lastName);
    }

    // Compte total d'employés
    public int countEmployees() {
        return (int) employeeDao.count();
    }

    // Liste des prénoms uniques
    public List<String> getUniqueFirstNames() {
        return employeeDao.findDistinctFirstName();
    }

    // Ajout en masse d'employés avec vérification des doublons et validation des emails
    public List<Employee> addEmployees(List<Employee> employees) {
        for (Employee employee : employees) {
            validateEmail(employee.getEmail());
            if (employeeDao.existsByEmail(employee.getEmail())) {
                throw new RuntimeException("L'employé avec cet email existe déjà: " + employee.getEmail());
            }
        }
        return employeeDao.saveAll(employees);
    }

    // Validation de l'email
    private void validateEmail(String email) {
        String EMAIL_REGEX = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";
        if (email == null || !email.matches(EMAIL_REGEX)) {
            throw new InvalidEmailException("L'adresse email " + email + " n'est pas valide.");
        }
    }
}
