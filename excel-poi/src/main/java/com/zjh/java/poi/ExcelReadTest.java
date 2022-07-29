//package com.zjh.java.poi;
//
//import org.apache.poi.hssf.usermodel.HSSFCell;
//import org.apache.poi.hssf.usermodel.HSSFDateUtil;
//import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
//import org.apache.poi.hssf.usermodel.HSSFWorkbook;
//import org.apache.poi.ss.usermodel.*;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//import org.joda.time.DateTime;
//
//import java.io.FileInputStream;
//import java.util.Date;
//
//public class ExcelReadTest {
//    String path = "E:\\Java\\IdeaProjects\\JavaLearning\\excel-poi\\src\\main\\";
//
//    public void testWrite03() throws Exception{
//        FileInputStream fileInputStream = new FileInputStream(path+"test.xls");
//        Workbook workbook = new HSSFWorkbook(fileInputStream);
//        Sheet sheet = workbook.getSheetAt(0);
//        Row row = sheet.getRow(0);
//        Cell cell = row.getCell(1);
//        System.out.println(cell.getStringCellValue());
//        fileInputStream.close();
//    }
//
//    public void testWrite07() throws Exception{
//        FileInputStream fileInputStream = new FileInputStream(path+"resourcesexcel-poi-test-07.xlsx");
//        Workbook workbook = new XSSFWorkbook(fileInputStream);
//        Sheet sheet = workbook.getSheetAt(0);
//        Row row = sheet.getRow(0);
//        Cell cell = row.getCell(1);
//        System.out.println(cell.getStringCellValue());
//        fileInputStream.close();
//    }
//    public void testCellType() throws Exception{
//        FileInputStream fileInputStream = new FileInputStream(path+"明细表.xls");
//        Workbook workbook = new HSSFWorkbook(fileInputStream);
//        Sheet sheetAt = workbook.getSheetAt(0);
//        Row rowTitle = sheetAt.getRow(0);
//        if (rowTitle!=null){
//            int physicalNumberOfCells = rowTitle.getPhysicalNumberOfCells();
//            for (int cellNum = 0; cellNum < physicalNumberOfCells; cellNum++) {
//                Cell cell = rowTitle.getCell(cellNum);
//                if (cell!=null){
//                    int cellType = cell.getCellType();
//                    String stringCellValue = cell.getStringCellValue();
//                    System.out.print(stringCellValue+"|");
//                }
//            }
//            System.out.println();
//        }
//        int rowCount = sheetAt.getPhysicalNumberOfRows();
//        for (int rowNum = 1; rowNum < rowCount; rowNum++) {
//            Row row = sheetAt.getRow(rowNum);
//            if (row!=null){
//                int cellCount = rowTitle.getPhysicalNumberOfCells();
//                for (int cellNum = 0; cellNum < cellCount; cellNum++) {
//                    Cell cell = row.getCell(cellNum);
//                    if (cell!=null){
//                        int cellType = cell.getCellType();
//                        String cellValue = "";
//                        switch (cellType){
//                            case HSSFCell.CELL_TYPE_STRING:
//                                System.out.print("[String]");
//                                cellValue = cell.getStringCellValue();
//                                break;
//                            case HSSFCell.CELL_TYPE_BOOLEAN:
//                                System.out.print("[boolean]");
//                                cellValue = String.valueOf(cell.getBooleanCellValue());
//                                break;
//                            case HSSFCell.CELL_TYPE_BLANK:
//                                System.out.print("[blank]");
//                                break;
//                            case HSSFCell.CELL_TYPE_NUMERIC:
//                                System.out.print("[NUMERIC]");
//                                if (HSSFDateUtil.isCellDateFormatted(cell)){
//                                    System.out.print("[DATE]");
//                                    Date dateCellValue = cell.getDateCellValue();
//                                    cellValue = new DateTime(dateCellValue).toString("yyyy-MM-dd");
//                                }else {
//                                    System.out.print("[转换为字符串输出]");
//                                    cell.setCellType(HSSFCell.CELL_TYPE_STRING);
//                                    cellValue = cell.toString();
//                                }
//                                break;
//                            case HSSFCell.CELL_TYPE_ERROR:
//                                System.out.println("[error]");
//                                break;
//                        }
//                        System.out.println(cellValue);
//                    }
//                }
//            }
//            fileInputStream.close();
//        }
//    }
//
//    public void testFormula() throws Exception{
//        FileInputStream fileInputStream = new FileInputStream(path+"公式.xls");
//        Workbook workbook = new HSSFWorkbook(fileInputStream);
//        Sheet sheetAt = workbook.getSheetAt(0);
//        Row row = sheetAt.getRow(1);
//        Cell cell = row.getCell(0);
//        FormulaEvaluator formulaEvaluator = new HSSFFormulaEvaluator((HSSFWorkbook) workbook);
//        int cellType = cell.getCellType();
//        switch (cellType){
//            //判断cell是否有公式
//            case Cell.CELL_TYPE_FORMULA:
//                String formula = cell.getCellFormula();
//                System.out.println(formula);
//                //计算
//                CellValue evaluate = formulaEvaluator.evaluate(cell);
//                String cellValue = evaluate.formatAsString();
//                System.out.println(cellValue);
//                break;
//        }
//
//    }
//
//    public static void main(String[] args) throws Exception {
//        ExcelReadTest excelReadTest = new ExcelReadTest();
//        //excelReadTest.testWrite03();
//        //excelReadTest.testWrite07();
//        excelReadTest.testCellType();
//    }
//}
