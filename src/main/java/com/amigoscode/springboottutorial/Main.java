package com.amigoscode.springboottutorial;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	public Map<String, Object> registerNewCustomer(@RequestBody NewCustomerRequest request) {
		Customer customer = new Customer();

		customer.setName(request.name());
		customer.setEmail(request.email());
		customer.setAge(request.age());

		customerRepository.save(customer);

		// returns a JSON response
		HashMap<String, Object> map = new HashMap<>();
		map.put("Customer", customer);
		map.put("Message", "New customer successfully created!");

		return map;
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

	record NewCustomerRequest(
			String name,
			String email,
			Integer age
	) {
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
