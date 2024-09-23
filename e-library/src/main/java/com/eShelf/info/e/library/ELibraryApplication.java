package com.eShelf.info.e.library;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication (exclude = SecurityAutoConfiguration.class)
public class ELibraryApplication {

	public static void main(String[] args) {
		SpringApplication.run(ELibraryApplication.class, args);
	}

}
