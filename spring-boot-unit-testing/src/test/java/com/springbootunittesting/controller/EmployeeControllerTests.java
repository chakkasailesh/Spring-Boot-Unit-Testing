package com.springbootunittesting.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springbootunittesting.entity.Employee;
import com.springbootunittesting.exception.ResourceNotFoundException;
import com.springbootunittesting.service.EmployeeService;

@WebMvcTest
public class EmployeeControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private EmployeeService employeeService;

	@Autowired
	private ObjectMapper objectMapper;

	private Employee employee;

	@BeforeEach
	public void setup() {
		employee = Employee.builder().firstName("Scarlett").lastName("Johansson").email("scarjo@email.com").build();
	}

	@Test
	@DisplayName("Test for create employee REST API")
	public void givenEmployee_whenCreateEmployee_thenReturnCreatedEmployee() throws JsonProcessingException, Exception {
		// given - precondition or setup
		given(employeeService.saveEmployee(any(Employee.class))).willAnswer((arguments) -> {
			return arguments.getArgument(0);
		});

		// when - action or behavior that we are going to test
		ResultActions response = mockMvc.perform(post("/api/employees").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(employee)));

		// then - verify the output
		response.andDo(print()).andExpect(status().isCreated())
				.andExpect(jsonPath("$.firstName", is(employee.getFirstName())))
				.andExpect(jsonPath("$.lastName", is(employee.getLastName())))
				.andExpect(jsonPath("$.email", is(employee.getEmail())));
	}

	@Test
	@DisplayName("Test for getAllEmployees REST API")
	public void givenEmployees_whenGetAllEmployees_thenReturnAllEmployees() throws Exception {
		// given - precondition or setup
		Employee employee1 = Employee.builder().firstName("Robert").lastName("Downey Jr").email("tonystark@email.com")
				.build();
		given(employeeService.getAllEmployees()).willReturn(List.of(employee, employee1));

		// when - action or behavior that we are going to test
		ResultActions response = mockMvc.perform(get("/api/employees"));

		// then - verify the output
		response.andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.size()", is(2)));
	}

	@Test
	@DisplayName("Test for getEmployee Rest API")
	public void givenEmploeeId_whenGetEmployee_thenReturnEmployee() throws Exception {
		// given - precondition or setup
		long id = 1L;
		given(employeeService.getEmployee(id)).willReturn(Optional.of(employee));

		// when - action or behavior that we are going to test
		ResultActions response = mockMvc.perform(get("/api/employees/{id}", id));

		// then - verify the output
		response.andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$.firstName", is(employee.getFirstName())))
				.andExpect(jsonPath("$.lastName", is(employee.getLastName())))
				.andExpect(jsonPath("$.email", is(employee.getEmail())));
	}

	@Test
	@DisplayName("Test for getEmployee Rest API negative case")
	public void givenInvalidEmployeeId_whenGetEmployee_thenReturnNotFound() throws Exception {
		// given - precondition or setup
		long id = 3L;
		given(employeeService.getEmployee(id)).willReturn(Optional.empty());

		// when - action or behavior that we are going to test
		ResultActions response = mockMvc.perform(get("/api/employees/{id}", id));

		// then - verify the output
		response.andDo(print()).andExpect(status().isNotFound());
	}

	@Test
	@DisplayName("Test for updateEmployee Rest API")
	public void givenEmployeeId_whenUpdateEmployee_thenReturnUpdatedEmployee() throws Exception {
		// given - precondition or setup
		Employee employee1 = Employee.builder().firstName("Robert").lastName("Downey Jr").email("tonystark@email.com")
				.build();
		Long id = 1L;
		given(employeeService.updateEmployee(id, employee1)).willReturn(employee1);

		// when - action or behavior that we are going to test
		ResultActions response = mockMvc.perform(put("/api/employees/{id}", id).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(employee1)));

		// then - verify the output
		response.andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$.firstName", is(employee1.getFirstName())))
				.andExpect(jsonPath("$.lastName", is(employee1.getLastName())))
				.andExpect(jsonPath("$.email", is(employee1.getEmail())));
	}

	@Test()
	@DisplayName("Test for updateEmployee Rest API negative case")
	public void givenInvalidEmployeeId_whenUpdateEmployee_thenReturnNotFound() throws Exception {
		// given - precondition or setup
		Employee employee1 = Employee.builder().firstName("Robert").lastName("Downey Jr").email("tonystark@email.com")
				.build();
		Long id = 1L;
		given(employeeService.updateEmployee(id, employee1)).willThrow(ResourceNotFoundException.class);

		// when - action or behavior that we are going to test
		ResultActions response = mockMvc.perform(put("/api/employees/{id}", id).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(employee1)));

		// then - verify the output
		response.andDo(print()).andExpect(status().isNotFound());
	}

	@Test()
	@DisplayName("Test for deleteEmployee Rest API")
	public void givenEmployeeId_whenDeleteEmployee_thenReturnSuccessMessage() throws Exception {
		// given - precondition or setup
		Long id = 1L;
		willDoNothing().given(employeeService).deleteEmployee(id);

		// when - action or behavior that we are going to test
		ResultActions response = mockMvc.perform(delete("/api/employees/{id}", id));

		// then - verify the output
		response.andDo(print()).andExpect(status().isOk());
	}

}
