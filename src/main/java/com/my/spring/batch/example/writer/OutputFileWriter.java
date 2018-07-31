package com.my.spring.batch.example.writer;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;

import com.my.spring.batch.example.properties.BatchProperties;

public class OutputFileWriter extends FlatFileItemWriter<Map<String, Object>> {

	private static final Logger LOGGER = LoggerFactory.getLogger(OutputFileWriter.class);

	@Autowired
	BatchProperties batchProp;

	public OutputFileWriter(String outputPath, String tableName) {
		this.setResource(new FileSystemResource(outputPath + tableName + batchProp.getFileNameExtension()));
		this.setLineAggregator(new DelimitedLineAggregator<Map<String, Object>>() {
			{
				LOGGER.info("SET FILE SEPERATOR: " + batchProp.getFileSeperator());
				setDelimiter(batchProp.getFileSeperator());
			}
		});
	}

	public void write(List<? extends Map<String, Object>> items) throws Exception {
		// example of streaming
		items.forEach((item) -> {
			item.forEach((k, v) -> {
				item.put(k, v);
			});
		});
		super.write(items);
	}
}
