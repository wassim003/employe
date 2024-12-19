package com.employee.web.controller;

import com.employee.web.model.Employee;
import com.employee.web.service.EmployeeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Operation(summary = "View a list of all employees")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved list of employees"),
        @ApiResponse(responseCode = "204", description = "No content, no employees found")
    })
    @GetMapping
    public ResponseEntity<List<Employee>> listEmployees() {
        List<Employee> employees = employeeService.getAllEmployees();
        if (employees.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(employees);
    }

    @Operation(summary = "Get an employee by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved employee"),
        @ApiResponse(responseCode = "404", description = "Employee not found with the provided ID")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(
        @Parameter(description = "ID of the employee to be retrieved", required = true) @PathVariable Integer id) {
        Employee employee = employeeService.getEmployeeById(id);
        return ResponseEntity.ok(employee);
    }

    @Operation(summary = "Add a new employee")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Successfully added a new employee"),
        @ApiResponse(responseCode = "400", description = "Invalid input or email format")
    })
    @PostMapping
    public ResponseEntity<Employee> addEmployee(
        @Parameter(description = "Employee object to be added", required = true) @RequestBody Employee employee) {
        Employee newEmployee = employeeService.addEmployee(employee);
        return ResponseEntity.status(HttpStatus.CREATED).body(newEmployee);
    }

    @Operation(summary = "Update an existing employee")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully updated employee"),
        @ApiResponse(responseCode = "400", description = "Invalid input or email format"),
        @ApiResponse(responseCode = "404", description = "Employee not found with the provided ID")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(
        @Parameter(description = "ID of the employee to be updated", required = true) @PathVariable Integer id,
        @Parameter(description = "Updated employee object", required = true) @RequestBody Employee employee) {
        Employee updatedEmployee = employeeService.updateEmployee(id, employee);
        return ResponseEntity.ok(updatedEmployee);
    }

    @Operation(summary = "Delete an employee by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully deleted employee"),
        @ApiResponse(responseCode = "404", description = "Employee not found with the provided ID")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployee(
        @Parameter(description = "ID of the employee to be deleted", required = true) @PathVariable Integer id) {
        String result = employeeService.deleteEmployee(id);
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "Search employee by email")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved employee"),
        @ApiResponse(responseCode = "404", description = "Employee not found with the provided email")
    })
    @GetMapping("/search/email/{email}")
    public ResponseEntity<Employee> getEmployeeByEmail(@PathVariable String email) {
        Employee employee = employeeService.getEmployeeByEmail(email);
        return ResponseEntity.ok(employee);
    }

    @Operation(summary = "Search employees by first name containing a substring")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved list of employees"),
        @ApiResponse(responseCode = "204", description = "No content, no employees found")
    })
    @GetMapping("/search/firstNameContaining/{query}")
    public ResponseEntity<List<Employee>> searchEmployeesByFirstName(@PathVariable String query) {
        List<Employee> employees = employeeService.searchEmployeesByFirstName(query);
        if (employees.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(employees);
    }

    // Other endpoints (similar improvement for exception handling)

    @Operation(summary = "Get the total count of employees")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved employee count")
    })
    @GetMapping("/stats/count")
    public ResponseEntity<Integer> countEmployees() {
        int count = employeeService.countEmployees();
        return ResponseEntity.ok(count);
    }
}
