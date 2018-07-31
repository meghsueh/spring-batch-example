package com.my.spring.batch.example;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

@SpringBootApplication
@EnableAutoConfiguration(exclude = { DataSourceAutoConfiguration.class,
		DataSourceTransactionManagerAutoConfiguration.class })
@EnableBatchProcessing
public class BatchWebApplication {

	private static final Logger LOGGER = LoggerFactory.getLogger(BatchWebApplication.class);

	public static void main(String[] args) {
		Environment env = SpringApplication.run(BatchWebApplication.class, args).getEnvironment();
		LOGGER.info(
				"Starting Batch web application for environment: " + Arrays.toString(env.getActiveProfiles()) + ".");
	}

	@Bean
	CommandLineRunner runner() {
		return args -> {
			System.out.println("CommandLineRunner running the BatchWebApplication class ...");
		};
	}
}
