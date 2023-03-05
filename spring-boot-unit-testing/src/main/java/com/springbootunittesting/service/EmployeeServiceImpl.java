package com.springbootunittesting.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springbootunittesting.entity.Employee;
import com.springbootunittesting.exception.ResourceExistsException;
import com.springbootunittesting.exception.ResourceNotFoundException;
import com.springbootunittesting.repository.EmployeeRepository;

@Service
public class EmployeeServiceImpl implements EmployeeService {
	@Autowired
	private EmployeeRepository employeeRepository;

	public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
		this.employeeRepository = employeeRepository;
	}

	@Override
	public Employee saveEmployee(Employee employee) {
		Optional<Employee> savedEmployee = employeeRepository.findByEmail(employee.getEmail());
		if (savedEmployee.isPresent()) {
			throw new ResourceExistsException("Employee already exists with given email: " + employee.getEmail());
		}
		return employeeRepository.save(employee);
	}

	@Override
	public List<Employee> getAllEmployees() {
		return employeeRepository.findAll();
	}

	@Override
	public Optional<Employee> getEmployee(Long id) {
		return employeeRepository.findById(id);
	}

	@Override
	public Employee updateEmployee(Long id, Employee employee) {
		Optional<Employee> employeeOptional = employeeRepository.findById(id);
		Employee savedEmployee = null;
		if (employeeOptional.isPresent()) {
			savedEmployee = employeeOptional.get();
			savedEmployee.setFirstName(employee.getFirstName());
			savedEmployee.setLastName(employee.getLastName());
			savedEmployee.setEmail(employee.getEmail());
		} else {
			throw new ResourceNotFoundException("Employee does not exist with id: " + id);
		}
		return employeeRepository.save(savedEmployee);
	}

	@Override
	public void deleteEmployee(Long id) {
		employeeRepository.deleteById(id);
	}
}
