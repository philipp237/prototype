package ru.annenkov.prototype.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

	@GetMapping
	public String printHelloWorld() {
		return "Hello, world";
	}

	@GetMapping("/{name}")
	public String printHelloName(@PathVariable String name) {
		return "Hello, " + name;
	}

}
