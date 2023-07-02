package com.db.loanstorage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class LoanStorageApplication {

	public static void main(String[] args) {

		SpringApplication.run(LoanStorageApplication.class, args);
	}

}
