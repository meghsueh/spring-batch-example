package com.my.spring.batch.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BatchCommandLineRunner {

	private static final Logger LOGGER = LoggerFactory.getLogger(BatchCommandLineRunner.class);

	@Autowired
	JobLauncher jobLauncher;

	@Autowired
	Job batchJob;

	public void run(String... args) {
		LOGGER.info("BatchCommandLineRunner run ...");

		try {
			JobParameters jobParms = new JobParametersBuilder().addLong("time", System.currentTimeMillis())
					.addString("sql", "select * from tableA").addString("table", "tableA").toJobParameters();
			JobExecution execution = jobLauncher.run(batchJob, jobParms);
			LOGGER.info("Batch job exists status: " + execution.getExitStatus());
		} catch (Exception e) {
			LOGGER.error("Failed to run batch job.", e);
		}

	}
}
