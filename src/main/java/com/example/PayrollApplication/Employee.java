package com.example.PayrollApplication;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Employee{
	private @Id @GeneratedValue Long id;
	private String firstName, lastName, jobRole;
	private Long salary;
	
	public Employee() {}
	
	public Employee(String firstName, String lastName, String jobRole, Long salary)
	{
		this.setFirstName(firstName);
		this.setLastName(lastName);
		this.setJobRole(jobRole);
		this.setSalary(salary);
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getJobRole() {
		return jobRole;
	}

	public void setJobRole(String jobRole) {
		this.jobRole = jobRole;
	}

	public Long getSalary() {
		return salary;
	}

	public void setSalary(Long salary) {
		this.salary = salary;
	}
	
	// Provides JSON output of the object.
	@Override
	public String toString() 
	{
		return "Employee{" + "id=" + id 
					+ ", firstName='" + firstName.trim() 
					+ "\', lastName='" + lastName.trim()
					+ "\', jobRole='" + jobRole.trim()
					+ "\', salary='" + salary 
				+ "}";
	}
	
	public void update(Employee newEmployee)
	{
		if(this.getFirstName().trim() != newEmployee.getFirstName().trim())
			this.setFirstName(newEmployee.getFirstName());
		
		if(this.getLastName().trim() != newEmployee.getLastName().trim())
			this.setLastName(newEmployee.getLastName());
		
		if(this.getJobRole().trim() != newEmployee.getJobRole().trim())
			this.setJobRole(newEmployee.getJobRole());
		
		if(this.getSalary() != newEmployee.getSalary())
			this.setSalary(newEmployee.getSalary());
	}

}