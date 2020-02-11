package com.ntxl.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class MapperUtils {

	
	@Autowired
	private ObjectMapper objectMapper;
	
	
	@SuppressWarnings("unchecked")
	public <S,D> D convertValues(S source,D destination){
		destination = (D) objectMapper.convertValue(source, destination.getClass());
		return destination;
	}
	
	public <S> String convertToString(S source){
		String string = null;
		 try {
			string= new String(objectMapper.writeValueAsBytes(source));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return string;
	}
	
}
