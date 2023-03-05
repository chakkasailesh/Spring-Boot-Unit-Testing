package com.springbootunittesting.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

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

}
