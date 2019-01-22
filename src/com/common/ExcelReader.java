package com.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


/**
 * @Author xcd
 * @Aata 2019年1月11日
 * @Description
 */
public class ExcelReader {

    public List<ProgramImportData> readRandomExcel(File file, String sheetName) {
        List<ProgramImportData> dataList = new ArrayList<ProgramImportData>();
        
        try {
            InputStream inputStream = new FileInputStream(file);  
            String fileName = file.getName();  
            Workbook wb = WorkbookFactory.create(inputStream);
            
            
            Sheet sheet = null;
            
            if (sheetName == null || sheetName.isEmpty()) {
            	sheet = wb.getSheetAt(0);
            }
            else {
            	sheet = wb.getSheet(sheetName);
            }
            
            if (sheet == null) {
            	return null;
            }
              
            DataFormatter formatter = new DataFormatter();
            int firstRowIndex = sheet.getFirstRowNum();  
            int lastRowIndex = sheet.getLastRowNum();  
            for(int rIndex = firstRowIndex; rIndex <= lastRowIndex; rIndex ++){  
                Row row = sheet.getRow(rIndex);  
                if(row != null){  
                    int firstCellIndex = row.getFirstCellNum();  
                    int lastCellIndex = row.getLastCellNum();  
                    for(int cIndex = firstCellIndex; cIndex < lastCellIndex; cIndex ++){  
                        Cell cell = row.getCell(cIndex);  
                        String value = "";  
                        if(cell != null){  
                        	value = formatter.formatCellValue(cell);
                        	if (!value.isEmpty() && value.length() > 0) {
                                dataList.add(new ProgramImportData(rIndex, cIndex, value));
                                //System.out.print(value+"\t");                        		                        		
                        	}
                        }  
                    }  
                    //System.out.println();  
                }  
            }              
        }
        catch (Throwable e) {
            System.out.println("error");
        }
        
        return dataList;
    }    
    
    public HashMap<Integer, HashMap<Integer, String>> readExcelByRow(File file, String sheetName) {
    	
    	HashMap<Integer, HashMap<Integer, String>> dataMap = new HashMap<Integer, HashMap<Integer, String>>();
        try {
            //File file = new File("/Users/administrator/excel/sports.xls");
            InputStream inputStream = new FileInputStream(file);  
            String fileName = file.getName();  
            Workbook wb = WorkbookFactory.create(inputStream);

            Sheet sheet = null;
            
            if (sheetName == null || sheetName.isEmpty()) {
            	sheet = wb.getSheetAt(0);
            }
            else {
            	sheet = wb.getSheet(sheetName);
            }
              
            if (sheet == null) {
            	return null;
            }
            
            HashMap<Integer, String> cellMap = null;
            int firstRowIndex = sheet.getFirstRowNum();
            int lastRowIndex = sheet.getLastRowNum();  
            for(int rIndex = firstRowIndex; rIndex <= lastRowIndex; rIndex ++){  
                Row row = sheet.getRow(rIndex);  
                if(row != null){  
                    int firstCellIndex = row.getFirstCellNum();  
                    int lastCellIndex = row.getLastCellNum();
                    cellMap = new HashMap<Integer, String>();
                    for(int cIndex = firstCellIndex; cIndex < lastCellIndex; cIndex ++){  
                        Cell cell = row.getCell(cIndex);  
                        String value = "";  
                        if(cell != null){  
                    		value = cell.toString();
                        }  
                        
                        cellMap.put(cIndex, value);
                    }
                    
                    dataMap.put(rIndex, cellMap);
//                    System.out.println();  
                }  
            }              
        }
        catch (Throwable e) {
            System.out.println("error");
        }
        
        return dataMap;    	
    }
    
    public boolean saveData(String filename, String sheetName, Object[][] data) {
    	boolean bRet = false;
    	try {
        	XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet(sheetName);	
    		
            int rowCount = 0;
            for (Object[] record : data) {
            	Row row = sheet.createRow(rowCount++);
            	
        		int columnCount = 0;
            	for (Object field : record) {
            		Cell cell = row.createCell(columnCount++);
            		if (field instanceof String) {
            			cell.setCellValue((String)field);            			
            		}
            		else if (field instanceof Integer) {
            			cell.setCellValue(((Integer)field).toString());
            		}
            	}
            }
            	
            try (FileOutputStream fos = new FileOutputStream(filename)) {
            	workbook.write(fos);
                bRet = true;
                fos.flush();
                fos.close();
            }            
    	}
    	catch (Throwable e) {
    		e.printStackTrace();
    	}
    	
    	return bRet;
    }

}
