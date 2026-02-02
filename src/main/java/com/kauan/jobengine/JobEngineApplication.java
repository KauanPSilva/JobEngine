package com.kauan.jobengine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class JobEngineApplication {
	public static void main(String[] args) {
		SpringApplication.run(JobEngineApplication.class, args);
	}
}
