package com.example.Registra.model;

public class Employee {
	private long id;
	private String fullName;
	private String position;
	private int salary;

	public Employee() {}
  
	public Employee(long id, String fullName, String position, int salary) {
	    this.id = id;
	    this.fullName = fullName;
	    this.position = position;
	    this.salary = salary;
	}

	public Employee(String fullName, String position, int salary) {
	    this.fullName = fullName;
	    this.position = position;
	    this.salary = salary;
	}
  
	public void setId(long id) {
		this.id = id;
	}
  
	public long getId() {
		return id;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public int getSalary() {
		return salary;
	}

	public void setSalary(int salary) {
		this.salary = salary;
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", fullName=" + fullName + ", position=" + position + ", salary=" + salary + "]";
	}
}
