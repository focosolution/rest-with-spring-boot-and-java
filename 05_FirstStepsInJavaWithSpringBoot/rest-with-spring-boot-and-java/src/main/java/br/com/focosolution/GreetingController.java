package br.com.focosolution;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {
	
	private static final String template = "Hello, my %s!";
	private final AtomicLong counter = new AtomicLong();
	
	// Utilizando Query Params, exemplo de URI: http://localhost:8080/greeting?name=FocoSolution
	@RequestMapping("/greeting")  
	public Greeting greeting(
			@RequestParam(value = "name", defaultValue = "World") String param) {
		return new Greeting(counter.incrementAndGet(), String.format(template, param));
	}

}
