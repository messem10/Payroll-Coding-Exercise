package com.example.PayrollApplication;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
class EmployeeRepositoryTest extends BaseAPITest {

	private final EmployeeRepository repository = mock(EmployeeRepository.class);
	
	@Override
	@BeforeEach
	public void initialize(){
		super.initialize();
	}

	
	@Test
	void contextLoads() throws Exception {
		assertTrue(repository != null);
	}

	@Test
	void canGetAllEmployees() throws Exception {
		String uri = "/employees";
	    MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
    		.accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
	      
	    assertStatus(200, mvcResult);
	    
	    String content = mvcResult.getResponse().getContentAsString();
	    Employee[] employeeArray = super.fromJsonToArray(content, "employees", Employee[].class);
	    assertTrue(employeeArray.length > 0);
	}
	
	@Test
	void canGetEmployeeById() throws Exception {
		String uri = "/employees/1";
	    MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
    		.accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
	      
	    assertStatus(200, mvcResult);
	    
	    String content = mvcResult.getResponse().getContentAsString();
	    Employee employee = super.fromJson(content, Employee.class);
	    assertTrue(employee != null);
	}
	
	@Test
	void canAddEmployee() throws Exception{
		String uri = "/employees";
		int initialCount = getNumEmployees();
		
		Employee newEmployee = new Employee("John", "Smith", "Manager", (long) 123456);
		
	    MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
    		.contentType(MediaType.APPLICATION_JSON_VALUE).content(super.toJson(newEmployee))).andReturn();
	      
	    assertStatus(201, mvcResult);
	    assertTrue(initialCount < getNumEmployees());
	}
	
	@Test 
	void canUpdateEmployee() throws Exception{
		String uri = "/employees/1";
		Employee newEmployee = new Employee("Another", "Person", "JobRoleHere", (long) 1234);
	    MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri)
    		.contentType(MediaType.APPLICATION_JSON_VALUE).content(super.toJson(newEmployee))).andReturn();
	      
	    assertStatus(200, mvcResult);
	    
	    // Check to see if information updated
	    mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
	    		.accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
		      
	    assertStatus(200, mvcResult);
	    
	    String content = mvcResult.getResponse().getContentAsString();
	    Employee employee = super.fromJson(content, Employee.class);
	    assertEquals(employee.getSalary(), newEmployee.getSalary());
	}
	
	@Test
	void canRemoveEmployee() throws Exception {
		// Add a temporary employee to test removal on.
		canAddEmployee();
	    
	    int initialCount = getNumEmployees();
	    String uri = "/employees/" + initialCount;
	    
	    MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri)
	    		.accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
	    
	    assertStatus(200, mvcResult);
	    assertTrue(initialCount > getNumEmployees());
	}
	
	int getNumEmployees() throws Exception
	{
		String uri = "/employees";
	    MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
    		.accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

	    String content = mvcResult.getResponse().getContentAsString();
	    return super.fromJsonToArray(content, "employees", Employee[].class).length;
	}
	
}
