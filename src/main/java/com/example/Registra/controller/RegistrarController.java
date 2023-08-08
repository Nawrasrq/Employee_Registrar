package com.example.Registra.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.Registra.model.Employee;
import com.example.Registra.repo.EmployeeRepo;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class RegistrarController {
	
	@Autowired
	EmployeeRepo employeeRepo;
	
	@GetMapping("/employees")
	public ResponseEntity<List<Employee>> getAllEmployees(@RequestParam(required = false) String fullName){
		try {
			List<Employee> employees = new ArrayList<Employee>();
			
			if(fullName == null) {
				employeeRepo.findAll().forEach(employees::add);
			}
			else {
				employeeRepo.findByName(fullName).forEach(employees::add);
			}
			
			if(employees.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(HttpStatus.OK);
		}
		
		catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@GetMapping("/employees/{id}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable("id") long id){
		Employee employee = employeeRepo.findById(id);
		
		if(employee != null) {
			return new ResponseEntity<>(employee, HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
	}
	
	@GetMapping("/employees/salary")
	public ResponseEntity<List<Employee>> findBySalary(@RequestParam(required = true) int salary){
		try {
			List<Employee> employees = new ArrayList<Employee>();
			
			employeeRepo.findBySalary(salary).forEach(employees::add);
			if(employees.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			
			return new ResponseEntity<>(HttpStatus.OK);
			
		}
		catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("/employees/{id}")
	public ResponseEntity<String> updateEmployee(@PathVariable("id") long id, @RequestBody Employee employee){
		Employee _employee = employeeRepo.findById(id);
		
		if(_employee != null) {
			_employee.setId(id);
			_employee.setFullName(employee.getFullName());
			_employee.setPosition(employee.getPosition());
			_employee.setSalary(employee.getSalary());
			
			employeeRepo.update(_employee);
			return new ResponseEntity<>("Employee was updated successfully.", HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>("Cannot find employee with id =" + id, HttpStatus.NOT_FOUND);
		}
		
	}
	
	@DeleteMapping("/employees/{id}")
	public ResponseEntity<String> deleteEmployee(@PathVariable("id") long id){
		try {
			int result = employeeRepo.deleteById(id);
			
			if(result == 0) {
				return new ResponseEntity<>("Cannot find employee with id = " + id, HttpStatus.OK);
			}
			return new ResponseEntity<>("Employee was deleted successfully", HttpStatus.OK);
		}
		
		catch(Exception e) {
			return new ResponseEntity<>("Cannot delete employee", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@DeleteMapping("/employees")
	public ResponseEntity<String> deleteAllEmployees(){
		try {
			int numRows = employeeRepo.deleteAll();
			return new ResponseEntity<>("Deleted " + numRows + "Employee(s) successfully", HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity<>("Cannot delete employees", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	
	}
	
	public static void main(String args[]) {
	
	}
}
