package com.my.spring.batch.example.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;

public class BatchCompletionListener extends JobExecutionListenerSupport {

	private static final Logger LOGGER = LoggerFactory.getLogger(BatchCompletionListener.class);
	
	@Override
	public void afterJob(JobExecution jobExecution) {
		super.beforeJob(jobExecution);
		LOGGER.info("BATCH JOB AFTER JOB STATUS: " + jobExecution.getStatus());
	}

	@Override
	public void beforeJob(JobExecution jobExecution) {
		super.beforeJob(jobExecution);
		LOGGER.info("BATCH JOB BEFORE JOB STATUS: " + jobExecution.getStatus());
	}
	

}
