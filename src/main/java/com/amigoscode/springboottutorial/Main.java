package com.amigoscode.springboottutorial;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@SpringBootApplication
@RestController
@RequestMapping("/api/v1/customers")
public class Main {
	private final CustomerRepository customerRepository;

	public Main(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}

	@GetMapping("")
	public List<Customer> getCustomers() {
		return customerRepository.findAll();
	}
	
	@PostMapping("")
	public void registerNewCustomer(Customer customer) {
		customerRepository.save(customer);
	}

	@GetMapping("/greet")
	public GreetResponse greet() {
		return new GreetResponse(
				"Hello, World!",
				List.of("Java", "TypeScript", "Python"),
				new Person(
						"John",
						30,
						100.0));
	}

	record Person(String name, int age, double savings) {
	}


	record GreetResponse(String greet,
	                     List<String> favoriteProgrammingLanguages,
	                     Person person) {
		@Override
		public String toString() {
			return String.format("%s %s %s", greet, favoriteProgrammingLanguages, person);
		}
	}
}
