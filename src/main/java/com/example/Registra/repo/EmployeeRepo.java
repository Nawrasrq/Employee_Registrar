package com.example.Registra.repo;
import java.util.List;
import com.example.Registra.model.Employee;

public interface EmployeeRepo {
	int add(Employee employee);
	int update(Employee employee);
	int deleteAll();
	int deleteById(Long id);

	Employee findById(Long id);
	List<Employee> findAll();
	List<Employee> findByName(String name);
	List<Employee> findBySalary(int salary);
	
}
