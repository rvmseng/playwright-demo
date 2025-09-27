package com.demo.l5.utils;

import com.opencsv.CSVReader;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class CSVUtils {

	public static List<Map<String, String>> readCsvAsMap(String resourcePath) {

		try 	(
				InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(resourcePath);
				InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8);
				CSVReader reader = new CSVReader(isr)
				) 
		{

			String[] header = reader.readNext(); // first line is header
			if (header == null)
				return Collections.emptyList();

			List<Map<String, String>> rows = new ArrayList<>();
			String[] line;
			
			while ((line = reader.readNext()) != null) {
				Map<String, String> map = new LinkedHashMap<>();
			
				for (int i = 0; i < header.length && i < line.length; i++) {
					map.put(header[i].trim(), line[i].trim());
				}
				
				rows.add(map);
			}
			
			return rows;
			
		} catch (Exception e) {
			throw new RuntimeException("Failed to read CSV: " + e.getMessage(), e);
		}
	}
}
