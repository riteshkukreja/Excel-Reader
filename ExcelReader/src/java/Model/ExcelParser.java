/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 *
 * @author Ritesh
 */
public class ExcelParser {
    
    private static String[] columns = {"id", "username", "first name", "last name", "address", "age", "passing year"};
    
    private static Object getCellValue(Cell cell) {
        switch (cell.getCellType()) {
        case Cell.CELL_TYPE_STRING:
            return cell.getStringCellValue();

        case Cell.CELL_TYPE_BOOLEAN:
            return cell.getBooleanCellValue();

        case Cell.CELL_TYPE_NUMERIC:
            return cell.getNumericCellValue();
        }

        return null;
    }
    
    private static Boolean matchCellValue(Cell cell, String title) {
        return title.equals((String) getCellValue(cell));
    }
    
    private static void validateColumns(Row headers) throws Exception {
        Iterator<Cell> cellIterator = headers.cellIterator();
        int cellCount = 0;

        while (cellIterator.hasNext()) {
            Cell nextCell = cellIterator.next();
            int columnIndex = nextCell.getColumnIndex();
            cellCount++;
            
            if(columnIndex >= columns.length || !matchCellValue(nextCell, columns[columnIndex]))
                throw new Exception("Invalid Columns name");
        }

        if(cellCount != 7)
            throw new Exception("Excel file must contain all the predefined columns.");

    }
    
    public static List<User> readUsersFromExcelFile(File excelFile) throws Exception {        
        List<User> listUsers = new ArrayList<>();
        try (FileInputStream inputStream = new FileInputStream(excelFile); Workbook workbook = new HSSFWorkbook(inputStream)) {
            Sheet firstSheet = workbook.getSheetAt(0);
            Iterator<Row> iterator = firstSheet.iterator();
            
            // validate headers
            if(iterator.hasNext()) {
                Row nextRow = iterator.next();
                validateColumns(nextRow);
            } else {
                throw new Exception("Empty sheet");
            }
            
            while (iterator.hasNext()) {
                Row nextRow = iterator.next();
                Iterator<Cell> cellIterator = nextRow.cellIterator();
                User aUser = new User();
                int cellCount = 0;
                
                while (cellIterator.hasNext()) {
                    Cell nextCell = cellIterator.next();
                    int columnIndex = nextCell.getColumnIndex();
                    cellCount++;
                    
                    try {                    
                        switch (columnIndex) {
                            case 0:
                                aUser.setId((double) getCellValue(nextCell));
                                break;
                            case 1:
                                aUser.setUsername((String) getCellValue(nextCell));
                                break;
                            case 2:
                                aUser.setFirst_name((String) getCellValue(nextCell));
                                break;
                            case 3:
                                aUser.setLast_name((String) getCellValue(nextCell));
                                break;
                            case 4:
                                aUser.setAddress((String) getCellValue(nextCell));
                                break;
                            case 5:
                                aUser.setAge((double) getCellValue(nextCell));
                                break;
                            case 6:
                                aUser.setPassing_year((double) getCellValue(nextCell));
                                break;
                        }
                    } catch(Exception e) {
                        throw new Exception("Data type doesn't match at row " + nextRow.getRowNum() + " for column " + columns[columnIndex]);
                    }
                }
                    
                if(cellCount != 7)
                    throw new Exception("Column value cannot be empty");
                listUsers.add(aUser);
            }

        }

        return listUsers;
    }
}
