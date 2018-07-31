package com.my.spring.batch.example.config;

import java.util.Map;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.my.spring.batch.example.listener.BatchCompletionListener;
import com.my.spring.batch.example.processor.DbRowProcessor;
import com.my.spring.batch.example.properties.BatchProperties;
import com.my.spring.batch.example.reader.DbQueryReader;
import com.my.spring.batch.example.writer.OutputFileWriter;

@Configuration
public class BatchConfiguration {

	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;

	@Autowired
	private BatchProperties batchProp;

	@Bean
	@StepScope
	public JdbcCursorItemReader<Map<String, Object>> getReader(@Value("#{jobParameters['sq']}") String sql) {
		return new DbQueryReader(sql, null);
	}

	@Bean
	@StepScope
	public FlatFileItemWriter<Map<String, Object>> getWriter(@Value("#{jobParameters['table']}") String tableName) {
		return new OutputFileWriter(batchProp.getFileOutputPath(), tableName);
	}

	@Bean
	@StepScope
	public ItemProcessor<Map<String, Object>, Map<String, Object>> getProcessor() {
		return new DbRowProcessor();
	}

	// auto inject to this config
	private static final String SQL = null;
	private static final String TABLE = null;

	@Bean
	public Step dbToFile() {
		return (stepBuilderFactory.get("dbToFile")).<Map<String, Object>, Map<String, Object>> chunk(10)
				.reader(getReader(SQL)).processor(getProcessor()).writer(getWriter(TABLE)).allowStartIfComplete(true)
				.build();
	}

	@Bean
	public Job batchJob() {
		return jobBuilderFactory.get("batchJob").incrementer(new RunIdIncrementer())
				.listener(new BatchCompletionListener()).flow(dbToFile()).end().build();
	}
}
