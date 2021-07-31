package com.example.PayrollApplication;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
public class EmployeeController {
	private final EmployeeRepository repository;
	
	EmployeeController(EmployeeRepository repository)
	{
		this.repository = repository;
	}
	
	@GetMapping("/employees")
	CollectionModel<EntityModel<Employee>> getAllEmployees(){
		List<EntityModel<Employee>> employeeList = repository.findAll().stream()
				.map(employee -> singleEmployeeEntityModel(employee, employee.getId()))
				.collect(Collectors.toList());
		
		return CollectionModel.of(employeeList, linkTo(methodOn(EmployeeController.class).getAllEmployees()).withSelfRel());
	}
		
	@PostMapping("/employees")
	ResponseEntity<Employee> addNewEmployee(@RequestBody Employee newEmployee)
	{
		repository.save(newEmployee);
		return new ResponseEntity<Employee>(newEmployee, HttpStatus.CREATED);
	}
	
	@GetMapping("/employees/{id}")
	EntityModel<Employee> getById(@PathVariable Long id)
	{
		Employee employee = repository.findById(id).orElseThrow(()-> new EmployeeNotFoundException(id));
		
		return singleEmployeeEntityModel(employee, id);
	}
	
	@PutMapping("/employees/{id}")
	Employee updateEmployee(@RequestBody Employee newEmployee, @PathVariable Long id)
	{
		return repository.findById(id).map(employee -> {
			employee.update(newEmployee);
			return repository.save(employee);
		}).orElseGet(() -> {
			newEmployee.setId(id);
			return repository.save(newEmployee);
		});
	}
	
	@DeleteMapping("/employees/{id}")
	void deleteAtId(@PathVariable Long id)
	{
		repository.deleteById(id);
	}
	
	EntityModel<Employee> singleEmployeeEntityModel(Employee employee, Long id)
	{
		return EntityModel.of(employee,
				linkTo(methodOn(EmployeeController.class).getById(id)).withSelfRel(),
				linkTo(methodOn(EmployeeController.class).getAllEmployees()).withRel("employees"));
	}
}

class EmployeeNotFoundException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	EmployeeNotFoundException(Long id)
	{
		super("Employee #" + id + " was not found.");
	}
}