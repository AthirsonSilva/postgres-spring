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

	public Customer writeCustomer(Customer customer, NewCustomerRequest request) {
		customer.setName(request.name());
		customer.setEmail(request.email());
		customer.setAge(request.age());

		customerRepository.save(customer);

		return customer;
	}

	@GetMapping("")
	public Map<String, List<Customer>> getCustomers() {
		HashMap<String, List<Customer>> map = new HashMap<>();
		map.put("Customers", customerRepository.findAll());
		return map;
	}

	@PostMapping("")
	public Map<String, Object> registerNewCustomer(@RequestBody NewCustomerRequest request) {
		Customer customer = new Customer();

		// returns a JSON response
		HashMap<String, Object> map = new HashMap<>();
		map.put("Customer", writeCustomer(customer, request));
		map.put("Message", "New customer successfully created!");

		return map;
	}

	@DeleteMapping("/{id}")
	public Map<String, Object> deleteCustomer(@PathVariable("id") Integer id) {
		customerRepository.deleteById(id);

		HashMap<String, Object> map = new HashMap<>();
		map.put("Message", "Customer successfully deleted!");

		return map;
	}

	@GetMapping("/greet")
	public GreetResponse greet() {
		return new GreetResponse(
				"Hello, World! Welcome to my API!",
				List.of("Java", "TypeScript", "Python"),
				new Person(
						"Athirson Silva",
						20,
						"Software Engineer"));
	}

	@PatchMapping("/{id}")
	public Map<String, Object> updateCustomer(
			@PathVariable("id") Integer id,
			@RequestBody NewCustomerRequest request
	) {
		Customer customer = customerRepository.findById(id).orElseThrow(
				() -> new IllegalArgumentException("Customer with id " + id + " does not exist!"));


		HashMap<String, Object> map = new HashMap<>();
		map.put("Customer", writeCustomer(customer, request));
		map.put("Message", "Customer successfully updated!");

		return map;
	}

	record NewCustomerRequest(
			String name,
			String email,
			Integer age
	) {
	}

	record Person(String name, int age, String role) {

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
