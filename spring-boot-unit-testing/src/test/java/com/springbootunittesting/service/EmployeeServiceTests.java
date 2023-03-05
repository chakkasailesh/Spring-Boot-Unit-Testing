package com.springbootunittesting.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.springbootunittesting.entity.Employee;
import com.springbootunittesting.exception.ResourceExistsException;
import com.springbootunittesting.repository.EmployeeRepository;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTests {
	@Mock
	private EmployeeRepository employeeRepository;
	@InjectMocks
	private EmployeeServiceImpl employeeService;
	private Employee employee;

	@BeforeEach
	public void setup() {
		employee = Employee.builder().id(1L).firstName("Scarlett").lastName("Johansson").email("scarjo@email.com")
				.build();
	}

	@Test
	@DisplayName("Test for savEmployee method")
	public void givenEmployee_whenSaveEmployee_thenReturnEmployee() {
		// given - precondition or setup
		given(employeeRepository.save(employee)).willReturn(employee);
		given(employeeRepository.findByEmail(employee.getEmail())).willReturn(Optional.empty());

		// when - action or behavior that we are going to test
		Employee savedEmployee = employeeService.saveEmployee(employee);

		// then - verify the output
		assertThat(savedEmployee).isNotNull();
	}

	@Test
	@DisplayName("Test for savEmployee method exception")
	public void givenExistingEmployee_whenSaveEmployee_thenThrowsException() {
		// given - precondition or setup
		given(employeeRepository.findByEmail(employee.getEmail())).willReturn(Optional.of(employee));

		// when - action or behavior that we are going to test
		org.junit.jupiter.api.Assertions.assertThrows(ResourceExistsException.class,
				() -> employeeService.saveEmployee(employee));

		// then - verify the output
		verify(employeeRepository, never()).save(any(Employee.class));
	}

	@Test
	@DisplayName("Test for getAllEmployees method")
	public void givenEmployees_whenGetAllEmployees_thenReturnEmployees() {
		// given - precondition or setup
		Employee employee1 = Employee.builder().id(2L).firstName("Robert").lastName("Downey Jr")
				.email("tonystark@email.com").build();
		given(employeeRepository.findAll()).willReturn(List.of(employee, employee1));

		// when - action or behavior that we are going to test
		List<Employee> employees = employeeService.getAllEmployees();

		// then - verify the output
		assertThat(employees).isNotNull();
		assertThat(employees.size()).isEqualTo(2);
	}

	@Test
	@DisplayName("Test for getEmployeeById method")
	public void givenEmployeeId_whenGetEmployeeById_thenReturnEmployee() {
		// given - precondition or setup
		Long id = 1L;
		given(employeeRepository.findById(id)).willReturn(Optional.of(employee));

		// when - action or behavior that we are going to test
		Employee returnedEmployee = employeeService.getEmployee(id).get();

		// then - verify the output
		assertThat(returnedEmployee).isNotNull();
	}

	@Test
	@DisplayName("Test for updateEmployee method")
	public void givenEmployee_whenUpdateEmployee_thenReturnUpdatedEmployee() {
		// given - precondition or setup
		Employee employee1 = Employee.builder().id(1L).firstName("Robert").lastName("Downey Jr")
				.email("tonystark@email.com").build();
		Long id = 1L;
		given(employeeRepository.findById(id)).willReturn(Optional.of(employee));
		given(employeeRepository.save(employee1)).willReturn(employee1);

		// when - action or behavior that we are going to test
		Employee returnedEmployee = employeeService.updateEmployee(id, employee1);

		// then - verify the output
		assertThat(returnedEmployee).isEqualTo(employee1);
	}

	@Test
	@DisplayName("Test for deleteEmployee method")
	public void givenEmployeeId_whenDeleteEmployee_thenReturnSuccessMessage() {
		// given - precondition or setup
		Long id = 1L;
		willDoNothing().given(employeeRepository).deleteById(id);
		// when - action or behavior that we are going to test
		employeeService.deleteEmployee(id);

		// then - verify the output
		verify(employeeRepository, times(1)).deleteById(id);
	}
}
