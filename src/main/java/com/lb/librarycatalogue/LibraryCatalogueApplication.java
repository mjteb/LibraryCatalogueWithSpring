package com.lb.librarycatalogue;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class LibraryCatalogueApplication {

	public static void main(String[] args) {
		SpringApplication.run(LibraryCatalogueApplication.class, args);
	}

}
