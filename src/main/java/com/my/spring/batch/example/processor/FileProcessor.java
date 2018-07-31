package com.my.spring.batch.example.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

public class FileProcessor implements ItemProcessor<String, String> {

	private static final Logger LOGGER = LoggerFactory.getLogger(FileProcessor.class);

	@Override
	public String process(String file) throws Exception {
		LOGGER.info("FileProcessor processing file: " + file);
		return file;
	}

}
