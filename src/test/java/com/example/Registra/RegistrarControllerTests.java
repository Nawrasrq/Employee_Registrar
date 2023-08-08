package com.example.Registra;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.example.Registra.controller.RegistrarController;
import com.example.Registra.model.Employee;
import com.example.Registra.repo.EmployeeRepo;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(RegistrarController.class)
public class RegistrarControllerTests {
	@MockBean
	private EmployeeRepo employeeRepo;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Test
	void createTutorial() throws Exception{
		Employee employee = new Employee(1, "SB @WebMvcTest", "position", 60000);
		mockMvc.perform(post("/tests/employees").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(employee)))
				.andExpect(status().isCreated())
				.andDo(print());
	}
	
	@Test
	void returnEmployee() throws Exception {
	long id = 1L;
	Employee employee = new Employee(id, "SB @WebMvcTest", "position", 60000);
	
	when(employeeRepo.findById(id)).thenReturn(employee);
	mockMvc.perform(get("tests/employees/{id}", id)).andExpect(status().isOk())
	.andExpect(jsonPath("$.id").value(id))
	.andExpect(jsonPath("$.fullName").value(employee.getFullName()))
	.andExpect(jsonPath("$.position").value(employee.getPosition()))
	.andExpect(jsonPath("$.salary").value(employee.getSalary()))
	.andDo(print());
	}
	
	@Test
	void returnNotFoundEmployee() throws Exception{
		long id = 1L;
		
		when(employeeRepo.findById(id)).thenReturn(null);
		mockMvc.perform(get("/tests/employee/{id}", id))
			.andExpect(status().isNotFound())
			.andDo(print());
	}

	@Test
	void returnListOfEmployees() throws Exception{
		List<Employee> employees = new ArrayList<>(Arrays.asList(
			new Employee(1, "@WebMvcTest 1", "position 1", 60000),
			new Employee(2, "@WebMvcTest 2", "position 2", 50000),
			new Employee(3, "@WebMvcTest 3", "position 3", 40000)));
		
		when(employeeRepo.findAll()).thenReturn(employees);
		mockMvc.perform(get("/tests/employees"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.size").value(employees.size()))
			.andDo(print());
	}
	
	@Test
	void returnListOfEmployeesWithFilter() throws Exception{
		List<Employee> employees = new ArrayList<>(
				Arrays.asList(new Employee(1, "@WebMvcTest 1", "position 1", 60000),
						new Employee(3, "@WebMvcTest 3", "position 3", 40000)));
		String fullName = "@WebMvcTest";
		MultiValueMap<String, String> paramsMap = new LinkedMultiValueMap<>();
		paramsMap.add("fullName", fullName);
		
		when(employeeRepo.findByName(fullName)).thenReturn(employees);
		mockMvc.perform(get("/tests/employees").params(paramsMap))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.size()").value(employees.size()))
			.andDo(print());
		
		employees = Collections.emptyList();
		
		when(employeeRepo.findByName(fullName)).thenReturn(employees);
	    mockMvc.perform(get("/tests/employees").params(paramsMap))
	        .andExpect(status().isNoContent())
	        .andDo(print());
	}

	  @Test
	  void updateEmployee() throws Exception {
	    long id = 1L;

	    Employee employee = new Employee(id, "Spring Boot @WebMvcTest", "Description", 60000);
	    Employee updatedEmployee = new Employee(id, "Updated", "Updated", 40000);

	    when(employeeRepo.findById(id)).thenReturn(employee);
	    //when(employeeRepo.update(any(Employee.class))).thenReturn(updatedEmployee);

	    mockMvc.perform(put("/tests/employees/{id}", id).contentType(MediaType.APPLICATION_JSON)
	        .content(objectMapper.writeValueAsString(updatedEmployee)))
	        .andExpect(status().isOk())
	        .andExpect(jsonPath("$.fullName").value(updatedEmployee.getFullName()))
	        .andExpect(jsonPath("$.position").value(updatedEmployee.getPosition()))
	        .andExpect(jsonPath("$.salary").value(updatedEmployee.getSalary()))
	        .andDo(print());
	  }
	  
	  @Test
	  void returnNotFoundUpdateEmployee() throws Exception {
	    long id = 1L;

	    Employee updatedEmployee = new Employee(id, "Updated", "Updated", 40000);

	    when(employeeRepo.findById(id)).thenReturn(null);
	    //when(employeeRepo.update(any(Employee.class))).thenReturn(updatedEmployee);

	    mockMvc.perform(put("/tests/employees/{id}", id).contentType(MediaType.APPLICATION_JSON)
	        .content(objectMapper.writeValueAsString(updatedEmployee)))
	        .andExpect(status().isNotFound())
	        .andDo(print());
	  }
	  
	  @Test
	  void shouldDeleteTutorial() throws Exception {
	    long id = 1L;

	    doNothing().when(employeeRepo).deleteById(id);
	    mockMvc.perform(delete("/tests/employees/{id}", id))
	         .andExpect(status().isNoContent())
	         .andDo(print());
	  }
	  
	  @Test
	  void shouldDeleteAllTutorials() throws Exception {
	    doNothing().when(employeeRepo).deleteAll();
	    mockMvc.perform(delete("/tests/employees"))
	         .andExpect(status().isNoContent())
	         .andDo(print());
	  }
}
