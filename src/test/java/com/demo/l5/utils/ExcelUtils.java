package com.demo.l5.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.InputStream;
import java.util.*;

public class ExcelUtils {

	public static List<Map<String, String>> readExcelAsMap(String resourcePath) {
		try (
				InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(resourcePath);
				Workbook workbook = new XSSFWorkbook(is)) 
		{

			Sheet sheet = workbook.getSheetAt(0);
			Iterator<Row> rowIterator = sheet.iterator();
			
			if (!rowIterator.hasNext())
				return Collections.emptyList();

			Row headerRow = rowIterator.next();
			List<String> headers = new ArrayList<>();
			
			for (Cell c : headerRow)
				headers.add(c.getStringCellValue().trim());

			List<Map<String, String>> rows = new ArrayList<>();
			DataFormatter formatter = new DataFormatter();

			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();
				Map<String, String> map = new LinkedHashMap<>();
				
				for (int i = 0; i < headers.size(); i++) {
					Cell cell = row.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
					map.put(headers.get(i), formatter.formatCellValue(cell).trim());
				}
				
				rows.add(map);
			}
			return rows;
			
		} catch (Exception e) {
			throw new RuntimeException("Failed to read Excel: " + e.getMessage(), e);
		}
	}
}
