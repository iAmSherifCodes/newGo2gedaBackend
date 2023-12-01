package com.go2geda.Go2GedaApp;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication @OpenAPIDefinition
public class Go2GedaAppApplication {
	public static void main(String[] args) {
		SpringApplication.run(Go2GedaAppApplication.class, args);
	}
}
