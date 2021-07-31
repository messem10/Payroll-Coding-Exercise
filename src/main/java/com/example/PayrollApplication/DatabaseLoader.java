package com.example.PayrollApplication;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class DatabaseLoader{
	
	// Adds some initial data to the database.
	@Bean
	CommandLineRunner initializeDatabase(EmployeeRepository repository)
	{
		return args -> {
			repository.save(new Employee("First", "Last", "Programmer", (long) 9001));
			repository.save(new Employee("John", "Smith", "Manager", (long) 123456));
		};
	}
}