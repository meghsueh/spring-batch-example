package com.my.spring.batch.example.reader;

import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.jdbc.core.ColumnMapRowMapper;

public class DbQueryReader extends JdbcCursorItemReader<Map<String, Object>> {

	private static final Logger LOGGER = LoggerFactory.getLogger(DbQueryReader.class);

	public DbQueryReader(String sql, DataSource ds) {
		this.setDataSource(ds);
		this.setSql(sql);
		this.setRowMapper(new ColumnMapRowMapper());
		LOGGER.info("Set DbQueryReader with Sql: " + sql);
	}
}
