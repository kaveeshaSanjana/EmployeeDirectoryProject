package edu.icet.service.impl;

import edu.icet.dto.Employee;
import edu.icet.entity.EmployeeEntity;
import edu.icet.repository.EmployeeDao;
import edu.icet.service.EmployeeService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeDao employeeDao;
    private final ModelMapper modelMapper;

    @Override
    public List<Employee> getAllEmployees() {
        return employeeDao.findAll().stream()
                .map(employeeEntity -> modelMapper.map(employeeEntity, Employee.class))
                .collect(Collectors.toList());
    }

    @Override
    public Employee createEmployee(Employee employee) {
        if (employeeDao.findByEmail(employee.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already exists: " + employee.getEmail());
        }
        EmployeeEntity savedEntity = employeeDao.save(modelMapper.map(employee, EmployeeEntity.class));
        return modelMapper.map(savedEntity, Employee.class);
    }

    @Override
    public Employee getEmployeeById(Long id) {
        EmployeeEntity employeeEntity = employeeDao.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found "));
        return modelMapper.map(employeeEntity, Employee.class);
    }

    @Override
    public Employee getEmployeeByEmail(String email) {
        EmployeeEntity employeeEntity = employeeDao.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found "));
        return modelMapper.map(employeeEntity, Employee.class);
    }

    @Override
    public Employee updateEmployee(Long id, Employee updatedEmployee) {
        Optional<EmployeeEntity> byIdActive = employeeDao.findByIdActive(id);
        if(byIdActive.isPresent()){
            updatedEmployee.setCreatedAt(byIdActive.get().getCreatedAt());
            EmployeeEntity savedEntity = employeeDao.save(modelMapper.map(updatedEmployee,EmployeeEntity.class));
            return modelMapper.map(savedEntity, Employee.class);
        }else{
            throw new EntityNotFoundException("Employee not found ");
        }
    }

    @Override
    public void deleteEmployee(Long id) {
        EmployeeEntity employeeEntity = employeeDao.findByIdActive(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found or already inactive with id: " + id));

        employeeEntity.setIsActive(false);
        employeeDao.save(employeeEntity);
    }

    @Override
    public void hardDeleteEmployee(Long id) {
        if (!employeeDao.existsById(id)) {
            throw new EntityNotFoundException("Employee not found with id: " + id);
        }
        employeeDao.deleteById(id);
    }
}
