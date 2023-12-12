package com.test.service;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;


@Service
public class CalculatorService {

	public int calculateSum(int num1, int num2) {
        return num1 + num2;
    }


    public InputStream generateExcel(int num1, int num2) {
        try (
                ByteArrayOutputStream excelOutputStream = new ByteArrayOutputStream();
                Workbook workbook = new XSSFWorkbook()
        ) {
            // Creating an Excel sheet
            Sheet sheet = workbook.createSheet("Calculation Result");

            // Adding numbers to the Excel sheet
            Row headerRow = sheet.createRow(0);

            Cell headerCell1 = headerRow.createCell(0);
            headerCell1.setCellValue("Number 1");

            Cell headerCell2 = headerRow.createCell(1);
            headerCell2.setCellValue("Number 2");

            Cell headerCell3 = headerRow.createCell(2);
            headerCell3.setCellValue("Result");

            // Creating a new data row
            Row dataRow = sheet.createRow(1);

            // Replace these values with your actual numbers
            dataRow.createCell(0).setCellValue(num1);
            dataRow.createCell(1).setCellValue(num2);

            // Calculate the sum
            int result = calculateSum(num1, num2);
            dataRow.createCell(2).setCellValue(result);

            workbook.write(excelOutputStream);

            return new ByteArrayInputStream(excelOutputStream.toByteArray());

        } catch (IOException e) {
            e.printStackTrace();
            // Handle exceptions appropriately
            return null;
        }
    }
	
}
