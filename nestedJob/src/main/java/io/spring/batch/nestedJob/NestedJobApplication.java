package io.spring.batch.nestedJob;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableBatchProcessing
@SpringBootApplication
public class NestedJobApplication {

	public static void main(String[] args) {
		SpringApplication.run(NestedJobApplication.class, args);
	}

}
