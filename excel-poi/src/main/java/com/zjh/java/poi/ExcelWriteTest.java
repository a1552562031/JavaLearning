package com.zjh.java.poi;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.joda.time.DateTime;
import java.io.FileOutputStream;

public class ExcelWriteTest {
    String path = "E:\\Java\\IdeaProjects\\JavaLearning\\excel-poi\\src\\main\\resources";
    public void testWrite03() throws Exception {
        Workbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet("test_sheet");
        Row row = sheet.createRow(0);
        String date = new DateTime().toString("yyyy-MM-dd HH:mm:ss");
        Cell cell1 = row.createCell(0);
        cell1.setCellValue(date);
        Cell cell2 = row.createCell(1);
        cell2.setCellValue("test-cell");

        Row row1 = sheet.createRow(1);
        row1.createCell(0).setCellValue("123456789");

        FileOutputStream stream = new FileOutputStream(path+"excel-poi-test-03.xls");
        workbook.write(stream);
        stream.close();
        System.out.println("excel-poi新建完成");
    }
    public void testWrite07() throws Exception {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("test_sheet-test");
        Row row = sheet.createRow(0);
        String date = new DateTime().toString("yyyy-MM-dd HH:mm:ss");
        Cell cell1 = row.createCell(0);
        cell1.setCellValue(date);
        Cell cell2 = row.createCell(1);
        cell2.setCellValue("test-cell");

        Row row1 = sheet.createRow(1);
        row1.createCell(0).setCellValue("123456789");

        FileOutputStream stream = new FileOutputStream(path+"excel-poi-test-07.xlsx");
        workbook.write(stream);
        stream.close();
        System.out.println("excel-poi新建完成");
    }
    public void testWrite03BigData() throws Exception {
        long begin = System.currentTimeMillis();
        Workbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet();
        for (int i = 0; i < 65536; i++) {
            Row row = sheet.createRow(i);
            for (int j = 0; j < 10; j++) {
                Cell cell = row.createCell(j);
                cell.setCellValue(j);
            }
        }
        System.out.println("over");
        FileOutputStream fileOutputStream = new FileOutputStream(path+"testWriteBigData03.xls");
        workbook.write(fileOutputStream);
        fileOutputStream.close();
        long end = System.currentTimeMillis();
        System.out.println((double) end-begin/1000);
    }
    public void testWrite07BigData() throws Exception {
        long begin = System.currentTimeMillis();
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet();
        for (int i = 0; i < 1048576; i++) {
            Row row = sheet.createRow(i);
            for (int j = 0; j < 10; j++) {
                Cell cell = row.createCell(j);
                cell.setCellValue(j);
            }
        }
        System.out.println("over");
        FileOutputStream fileOutputStream = new FileOutputStream(path+"testWriteBigData07.xlsx");
        workbook.write(fileOutputStream);
        fileOutputStream.close();
        long end = System.currentTimeMillis();
        System.out.println((double) end-begin/1000);
    }
    public void testWrite07BigDataSuper() throws Exception {
        long begin = System.currentTimeMillis();
        Workbook workbook = new SXSSFWorkbook();
        Sheet sheet = workbook.createSheet();
        for (int i = 0; i < 1048576; i++) {
            Row row = sheet.createRow(i);
            for (int j = 0; j < 10; j++) {
                Cell cell = row.createCell(j);
                cell.setCellValue(j);
            }
        }
        System.out.println("over");
        FileOutputStream fileOutputStream = new FileOutputStream(path+"testWriteBigData07Super.xlsx");
        workbook.write(fileOutputStream);
        fileOutputStream.close();
        ((SXSSFWorkbook)workbook).dispose();
        long end = System.currentTimeMillis();
        System.out.println((double) end-begin/1000);
    }
    public static void main(String[] args) throws Exception {
        ExcelWriteTest excelWriteTest = new ExcelWriteTest();
        excelWriteTest.testWrite03();
        excelWriteTest.testWrite07();
    }


}
