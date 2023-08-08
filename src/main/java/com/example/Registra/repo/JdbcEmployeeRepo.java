package com.example.Registra.repo;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.Registra.model.Employee;

@Repository
public class JdbcEmployeeRepo implements EmployeeRepo{
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public int add(Employee employee){
		return jdbcTemplate.update("INSERT INTO employees (id, fullName, position, salary VALUES(?,?,?,?)",
				new Object[] {employee.getFullName(), employee.getPosition(), employee.getSalary()});
	}
	
	@Override
	public int update(Employee employee){
		return jdbcTemplate.update("UPDATE employees SET title=?, fullName=?, position=?, salary=? WHERE id=?",
				new Object[] {employee.getFullName(), employee.getPosition(), employee.getSalary(), employee.getId()});
	}
	
	@Override
	public int deleteAll(){
		return jdbcTemplate.update("DELETE from employees");
	}
	
	@Override
	public int deleteById(Long id){
		return jdbcTemplate.update("DELETE from employees WHERE id=?",
				id);
	}
	
	@Override
	public Employee findById(Long id) {
		try {
			Employee employee = jdbcTemplate.queryForObject("SELECT * FROM employees where id=?",
					BeanPropertyRowMapper.newInstance(Employee.class), id);
			return employee;
		} catch(IncorrectResultSizeDataAccessException e) {
			return null;
		}
	}
	
	@Override
	public List<Employee> findAll(){
	    return jdbcTemplate.query("SELECT * from employees", 
	    		BeanPropertyRowMapper.newInstance(Employee.class));
	}
	
	@Override
	public List<Employee> findByName(String name){
		return jdbcTemplate.query("SELECT * from employees WHERE fullName LIKE '%?%'",
		        BeanPropertyRowMapper.newInstance(Employee.class), name);
	}
	
	@Override
	public List<Employee> findBySalary(int salary){
		return jdbcTemplate.query("SELECT * from employees WHERE salary=?",
		        BeanPropertyRowMapper.newInstance(Employee.class), salary);
	}
}
