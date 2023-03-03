package com.springbootunittesting.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.springbootunittesting.entity.Employee;

@DataJpaTest
public class EmployeeRepositoryTests {
	@Autowired
	private EmployeeRepository employeeRepository;

	private Employee employee;

	@BeforeEach
	public void setup() {
		employee = Employee.builder().firstName("Scarlett").lastName("Johansson").email("scarjo@email.com").build();
	}

	@Test
	@DisplayName("Test for save employee operation")
	public void givenEmployeeObject_whenSave_thenReturnSavedEmployee() {
		// given - precondition or setup
//		Employee employee = Employee.builder().firstName("John").lastName("Doe").email("johndoe@email.com").build();

		// when - action or behavior that we are going to test
		Employee savedEmployee = employeeRepository.save(employee);

		// then - verify the output
		assertThat(savedEmployee).isNotNull();
		assertThat(savedEmployee.getId()).isGreaterThan(0);
	}

	@Test
	@DisplayName("Test for get all employees operation")
	public void givenEmployees_whenFindAll_thenReturnAllEmployees() {
		// given - precondition or setup
//		Employee employee1 = Employee.builder().firstName("Scarlett").lastName("Johansson").email("scarjo@email.com")
//				.build();
		Employee employee2 = Employee.builder().firstName("And").lastName("De Armas").email("anadearmas@email.com")
				.build();
		employeeRepository.save(employee);
		employeeRepository.save(employee2);

		// when - action or behavior that we are going to test
		List<Employee> employees = employeeRepository.findAll();

		// then - verify the output
		assertThat(employees).isNotNull();
		assertThat(employees.size()).isEqualTo(2);
	}

	@Test
	@DisplayName("Test for get employee by id operation")
	public void givenEmployeeId_whenFindById_thenReturnEmployee() {
		// given - precondition or setup
//		Employee employee = Employee.builder().firstName("Scarlett").lastName("Johansson").email("scarjo@email.com")
//				.build();
		employeeRepository.save(employee);
		Long id = employee.getId();
		// when - action or behavior that we are going to test
		Employee returnedEmployee = employeeRepository.findById(id).get();

		// then - verify the output
		assertThat(returnedEmployee).isNotNull();
		assertThat(returnedEmployee.getId()).isEqualTo(id);
	}

	@Test
	@DisplayName("Test for get employee by email operation")
	public void givenEmployeeEmail_whenFindByEmail_thenReturnEmployee() {
		// given - precondition or setup
//		Employee employee = Employee.builder().firstName("Scarlett").lastName("Johansson").email("scarjo@email.com")
//				.build();
		employeeRepository.save(employee);
		String email = "scarjo@email.com";
		// when - action or behavior that we are going to test
		Employee returnedEmployee = employeeRepository.findByEmail(email).get();

		// then - verify the output
		assertThat(returnedEmployee).isNotNull();
		assertThat(returnedEmployee.getEmail()).isEqualTo(email);
	}

	@Test
	@DisplayName("Test for update employee operation")
	public void givenEmployee_whenUpdateEmployee_thenReturnUpdatedEmployee() {
		// given - precondition or setup
//		Employee employee = Employee.builder().firstName("Scarlett").lastName("Johansson").email("scarjo@email.com")
//				.build();
		employeeRepository.save(employee);
		// when - action or behavior that we are going to test
		Employee savedEmployee = employeeRepository.findById(employee.getId()).get();
		savedEmployee.setFirstName("Dwayne");
		savedEmployee.setLastName("Johnson");
		Employee returnedEmployee = employeeRepository.save(savedEmployee);

		// then - verify the output
		assertThat(returnedEmployee).isNotNull();
		assertThat(returnedEmployee.getFirstName()).isEqualTo("Dwayne");
		assertThat(returnedEmployee.getLastName()).isEqualTo("Johnson");
	}

	@Test
	@DisplayName("Test for delete employee by id operation")
	public void givenEmployee_whenDelete_thenRemoveEmployee() {
		// given - precondition or setup
//		Employee employee = Employee.builder().firstName("Scarlett").lastName("Johansson").email("scarjo@email.com")
//				.build();
		employeeRepository.save(employee);
		Long id = employee.getId();
		// when - action or behavior that we are going to test
		employeeRepository.deleteById(id);
		Optional<Employee> optionalEmployee = employeeRepository.findById(id);

		// then - verify the output
		assertThat(optionalEmployee).isEmpty();
	}

	@Test
	@DisplayName("Test for get employee by firstName and lastName operation")
	public void givenEmployeeFirstAndLastName_whenFindByFirstAndLastName_thenReturnEmployee() {
		// given - precondition or setup
//		Employee employee = Employee.builder().firstName("Scarlett").lastName("Johansson").email("scarjo@email.com")
//				.build();
		employeeRepository.save(employee);
		String firstName = "Scarlett";
		String lastName = "Johansson";
		// when - action or behavior that we are going to test
		Employee returnedEmployee = employeeRepository.findEmployee(firstName, lastName);

		// then - verify the output
		assertThat(returnedEmployee).isNotNull();
		assertThat(returnedEmployee.getFirstName()).isEqualTo(firstName);
		assertThat(returnedEmployee.getLastName()).isEqualTo(lastName);
	}

	@Test
	@DisplayName("Test for get employee by firstName and email operation")
	public void givenEmployeeFirstNameAndEmployee_whenFindByFirstNameAndEmail_thenReturnEmployee() {
		// given - precondition or setup
//		Employee employee = Employee.builder().firstName("Scarlett").lastName("Johansson").email("scarjo@email.com")
//				.build();
		employeeRepository.save(employee);
		String firstName = "Scarlett";
		String email = "scarjo@email.com";
		// when - action or behavior that we are going to test
		Employee returnedEmployee = employeeRepository.findEmployeeByFirstNameAndEmail(firstName, email);

		// then - verify the output
		assertThat(returnedEmployee).isNotNull();
		assertThat(returnedEmployee.getFirstName()).isEqualTo(firstName);
		assertThat(returnedEmployee.getEmail()).isEqualTo(email);
	}
}
