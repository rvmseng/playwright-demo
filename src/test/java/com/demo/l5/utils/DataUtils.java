package com.demo.l5.utils;

import com.demo.l5.data.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import java.util.List;

public class DataUtils {
	private static final ObjectMapper mapper = new ObjectMapper();

	public static List<User> readUsersFromJson(String resourcePath) {
		
		try (InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(resourcePath)) {
			
			if (is == null) {
				throw new RuntimeException("Resource not found: " + resourcePath);
			}
			
			return mapper.readValue(is, new TypeReference<List<User>>() {});
			
		} catch (Exception e) {
			throw new RuntimeException("Failed to read JSON: " + e.getMessage(), e);
		}
	}
}