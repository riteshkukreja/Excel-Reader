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
public class ExcelParser implements Parser {
    
    private String[] columns = {"id", "username", "first name", "last name", "address", "age", "passing year"};
    
    private Object getCellValue(Cell cell) {
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
    
    private Boolean matchCellValue(Cell cell, String title) {
        return title.equals((String) getCellValue(cell));
    }
    
    private void validateColumns(Row headers) throws Exception {
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
    
    private List<User> readUsersFromExcelFile(File excelFile) throws Exception {        
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
                                validateLength((String) getCellValue(nextCell));
                                aUser.setUsername((String) getCellValue(nextCell));
                                break;
                            case 2:
                                validateLength((String) getCellValue(nextCell));
                                aUser.setFirst_name((String) getCellValue(nextCell));
                                break;
                            case 3:
                                validateLength((String) getCellValue(nextCell));
                                aUser.setLast_name((String) getCellValue(nextCell));
                                break;
                            case 4:
                                validateLength((String) getCellValue(nextCell));
                                aUser.setAddress((String) getCellValue(nextCell));
                                break;
                            case 5:
                                validateAge((double) getCellValue(nextCell));
                                aUser.setAge((double) getCellValue(nextCell));
                                break;
                            case 6:
                                validateYear((double) getCellValue(nextCell));
                                aUser.setPassing_year((double) getCellValue(nextCell));
                                break;
                        }
                    } catch(Exception e) {
                        throw new Exception(e + " at row " + nextRow.getRowNum() + " for column " + columns[columnIndex]);
                    }
                }
                    
                if(cellCount != 7)
                    throw new Exception("Column value cannot be empty");
                listUsers.add(aUser);
            }

        }

        return listUsers;
    }

    public List<User> parse(File excelFile) throws Exception {
        return readUsersFromExcelFile(excelFile);
    }

    private void validateAge(double age) throws Exception {
        if(age < 18)
            throw new Exception("Age should be greater than 18 years");
    }
    
    private void validateYear(double year) throws Exception {
        if(year < 2014 || year > 2017)
            throw new Exception("Year should be between 2014 and 2017");
    }
    
    private void validateLength(String data) throws Exception {
        if(data.length() == 0)
            throw new Exception("Please proovide some data");
    }
}
