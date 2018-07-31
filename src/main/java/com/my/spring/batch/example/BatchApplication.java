package com.my.spring.batch.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
@EnableAutoConfiguration(exclude = { DataSourceAutoConfiguration.class,
		DataSourceTransactionManagerAutoConfiguration.class })
@EnableBatchProcessing
public class BatchApplication {

	private static final Logger LOGGER = LoggerFactory.getLogger(BatchApplication.class);

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(BatchApplication.class);
		app.setWebEnvironment(false);
		ConfigurableApplicationContext ctx = app.run(args);
		JobLauncher jobLauncher = ctx.getBean(JobLauncher.class);
		Job batchJob = ctx.getBean("batchJob", Job.class);

		try {
			LOGGER.info("BatchApplication starting ...");

			JobParameters jobParms = new JobParametersBuilder().addLong("time", System.currentTimeMillis())
					.addString("sql", "select * from tableA").addString("table", "tableA").toJobParameters();

			LOGGER.info("Batch job starts.");
			JobExecution execution = jobLauncher.run(batchJob, jobParms);
			LOGGER.info("Batch job exists status: " + execution.getExitStatus());

			System.exit(0);
		} catch (Exception e) {
			LOGGER.error("BatchApplication failed to run.", e);
		}
	}
}
