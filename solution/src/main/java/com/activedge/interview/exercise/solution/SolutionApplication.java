package com.activedge.interview.exercise.solution;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@EnableJpaAuditing
public class SolutionApplication {

	public static void main(String[] args) {
		SpringApplication.run(SolutionApplication.class, args);
	}

}
