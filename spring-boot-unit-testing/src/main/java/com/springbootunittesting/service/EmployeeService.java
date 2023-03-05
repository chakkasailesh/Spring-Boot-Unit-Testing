package com.springbootunittesting.service;

import java.util.List;
import java.util.Optional;

import com.springbootunittesting.entity.Employee;

public interface EmployeeService {
	Employee saveEmployee(Employee employee);

	List<Employee> getAllEmployees();

	Optional<Employee> getEmployee(Long id);

	Employee updateEmployee(Long id, Employee employee);

	void deleteEmployee(Long id);
}
