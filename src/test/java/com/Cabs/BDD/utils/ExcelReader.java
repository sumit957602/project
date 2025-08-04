package com.Cabs.BDD.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ExcelReader {
    private String filePath;
    private Workbook workbook;

    public ExcelReader(String filePath) {
        this.filePath = filePath;
        try {
            FileInputStream file = new FileInputStream(filePath);
            workbook = new XSSFWorkbook(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Map<String, String> getTestData(String sheetName, int rowNumber) {
        Map<String, String> data = new HashMap<>();
        Sheet sheet = workbook.getSheet(sheetName);

        if (sheet == null) {
            throw new RuntimeException("Sheet " + sheetName + " not found");
        }

        Row headerRow = sheet.getRow(0);
        Row dataRow = sheet.getRow(rowNumber);

        if (headerRow == null || dataRow == null) {
            throw new RuntimeException("Header row or data row not found");
        }

        for (int i = 0; i < headerRow.getLastCellNum(); i++) {
            Cell headerCell = headerRow.getCell(i);
            Cell dataCell = dataRow.getCell(i);

            if (headerCell != null && dataCell != null) {
                String header = getCellValueAsString(headerCell);
                String value = getCellValueAsString(dataCell);
                data.put(header, value);
            }
        }
        return data;
    }

    private String getCellValueAsString(Cell cell) {
        if (cell == null)
            return "";

        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue().toString();
                } else {
                    return String.valueOf((long) cell.getNumericCellValue());
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            default:
                return "";
        }
    }

    public void close() {
        try {
            if (workbook != null) {
                workbook.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
