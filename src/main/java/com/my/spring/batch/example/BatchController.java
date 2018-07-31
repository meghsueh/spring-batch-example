package com.my.spring.batch.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/run")
public class BatchController {
	private static final Logger LOGGER = LoggerFactory.getLogger(BatchController.class);

	@Autowired
	JobLauncher jobLauncher;

	@Autowired
	Job batchJob;

	@RequestMapping(path = "/batchJob", method = RequestMethod.GET, headers = "Accept=application/json", produces = "application/json")
	public String startJob(@RequestParam(value = "table", required = false) String table) {

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

		return "Batch job has been invoked.";
	}
}
