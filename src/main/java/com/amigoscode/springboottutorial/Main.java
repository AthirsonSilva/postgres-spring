package com.amigoscode.springboottutorial;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@SpringBootApplication
@RestController
public class Main {

	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
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
