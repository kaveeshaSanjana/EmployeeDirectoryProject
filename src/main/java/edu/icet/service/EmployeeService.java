package edu.icet.service;

import edu.icet.dto.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {
    List<Employee> getAllEmployees();
    Employee createEmployee(Employee employee);
    Employee getEmployeeById(Long id);
    Employee updateEmployee(Long id, Employee updatedEmployee);
    void deleteEmployee(Long id);
    void hardDeleteEmployee(Long id);
    Employee getEmployeeByEmail(String email);
}
