package com.reliaquest.api.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class EmployeeController implements IEmployeeController {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);
    private static final String EMPLOYEE_API_URL = "http://localhost:8112/api/v1/employee";

    private final RestTemplate restTemplate;

    public EmployeeController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public List<Employee> getAllEmployees() {
        try {
            logger.info("Fetching all employees");
            String url = EMPLOYEE_API_URL;
            EmployeeResponse response = restTemplate.getForObject(url, EmployeeResponse.class);
            if (response != null && "Successfully processed request.".equals(response.getStatus())) {
                return response.getData();
            }
            logger.warn("Failed to retrieve employees: " + response);
            return List.of();
        } catch (Exception e) {
            logger.error("Error occurred while fetching employees", e);
            throw new RuntimeException("Failed to retrieve employees");
        }
    }

    @Override
    public List<Employee> getEmployeesByNameSearch(String nameFragment) {
        try {
            logger.info("Searching for employees with name fragment: {}", nameFragment);
            String url = EMPLOYEE_API_URL + "?search=" + nameFragment;
            EmployeeResponse response = restTemplate.getForObject(url, EmployeeResponse.class);
            if (response != null && "Successfully processed request.".equals(response.getStatus())) {
                 return response.getData();
            }
            logger.warn("Failed to find employees with name fragment: " + nameFragment);
            return List.of();
        } catch (Exception e) {
            logger.error("Error occurred while searching for employees by name", e);
            throw new RuntimeException("Failed to search employees by name");
        }
    }

    @Override
    public Employee getEmployeeById(String id) {
        try {
            logger.info("Fetching employee with ID: {}", id);
            String url = EMPLOYEE_API_URL + "/" + id;
            EmployeeResponse response = restTemplate.getForObject(url, EmployeeResponse.class);
            if (response != null && response.getData() != null) {
                return response.getData();
            }
            logger.warn("Employee with ID {} not found", id);
            return null;
        } catch (Exception e) {
            logger.error("Error occurred while fetching employee by ID", e);
            throw new RuntimeException("Failed to retrieve employee by ID");
        }
    }

    @Override
    public int getHighestSalaryOfEmployees() {
        try {
            logger.info("Fetching highest salary of employees");
            List<Employee> employees = getAllEmployees();
            return employees.stream()
                        .mapToInt(Employee::getEmployeeSalary)
                        .max()
                        .orElseThrow(() -> new RuntimeException("No employees found"));
        } catch (Exception e) {
            logger.error("Error occurred while fetching the highest salary", e);
            throw new RuntimeException("Failed to retrieve the highest salary");
        }
    }

    @Override
    public List<String> getTop10HighestEarningEmployeeNames() {
        try {
            logger.info("Fetching top 10 highest earning employee names");
            List<Employee> employees = getAllEmployees();
            return employees.stream()
                        .sorted((e1, e2) -> Integer.compare(e2.getEmployeeSalary(), e1.getEmployeeSalary()))
                        .limit(10)
                        .map(Employee::getEmployeeName)
                        .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Error occurred while fetching top 10 highest earning employee names", e);
            throw new RuntimeException("Failed to retrieve top 10 highest earning employees");
        }
    }

    @Override
    public Employee createEmployee(Employee employee) {
        try {
            logger.info("Creating new employee: {}", employee.getEmployeeName());
            String url = EMPLOYEE_API_URL;
            EmployeeResponse response = restTemplate.postForObject(url, employee, EmployeeResponse.class);
            if (response != null && "Successfully processed request.".equals(response.getStatus())) {
                return response.getData();
            }
            logger.warn("Failed to create employee: {}", employee.getEmployeeName());
            return null;
        } catch (Exception e) {
            logger.error("Error occurred while creating employee", e);
            throw new RuntimeException("Failed to create employee");
        }
    }

    @Override
    public String deleteEmployeeById(String id) {
        try {
            logger.info("Deleting employee with ID: {}", id);
            String url = EMPLOYEE_API_URL + "/" + id;
            EmployeeResponse response = restTemplate.exchange(url, HttpMethod.DELETE, null, EmployeeResponse.class);
            if (response != null && "Successfully processed request.".equals(response.getStatus())) {
            return response.getData().getEmployeeName();
            }
            logger.warn("Failed to delete employee with ID: {}", id);
            return null;
        } catch (Exception e) {
            logger.error("Error occurred while deleting employee", e);
            throw new RuntimeException("Failed to delete employee");
        }
    }


}
