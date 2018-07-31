package com.my.spring.batch.example.processor;

import java.util.Map;

import org.springframework.batch.item.ItemProcessor;

public class DbRowProcessor implements ItemProcessor<Map<String, Object>, Map<String, Object>> {

	@Override
	public Map<String, Object> process(Map<String, Object> map) throws Exception {
		return map;
	}

}
