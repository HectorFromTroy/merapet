package com.nazipov.merapet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class MerapetApplication {

	public static void main(String[] args) {
		SpringApplication.run(MerapetApplication.class, args);
	}

}
