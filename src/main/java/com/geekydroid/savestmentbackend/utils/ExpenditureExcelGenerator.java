package com.geekydroid.savestmentbackend.utils;

import com.geekydroid.savestmentbackend.domain.expenditure.ExpenditureItem;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class ExpenditureExcelGenerator {

    private int serialNumber = 1;

    public File createExcel(List<ExpenditureItem> expenditureItems) throws IOException {

        try (HSSFWorkbook workbook = createWorkBook(expenditureItems)) {
            File exportFile = new File("ExpenditureData.xls");
            FileOutputStream fos = new FileOutputStream(exportFile);
            workbook.write(fos);
            return exportFile;
        }

    }

    private HSSFWorkbook createWorkBook(List<ExpenditureItem> expenditureItems) {
        serialNumber = 1;
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Expenditure");
        List<String> sheetHeaders = List.of("S.No", "Expenditure Date", "Expenditure Type", "Expenditure Category", "Amount");
        createSheetHeaders(sheet, sheetHeaders);
        for (int i = 0;i<expenditureItems.size();i++) {
            addDataInRow(i+1,sheet,expenditureItems.get(i));
        }
        return workbook;
    }

    private void addDataInRow(int rowIndex, HSSFSheet sheet, ExpenditureItem expenditureItem) {
        HSSFRow row = sheet.createRow(rowIndex);
        int columnIndex = 0;
        createCell(row,columnIndex++,String.valueOf(serialNumber++));
        createCell(row,columnIndex++,expenditureItem.getExpenditureDate().toString());
        createCell(row,columnIndex++,expenditureItem.getExpenditureType());
        createCell(row,columnIndex++,expenditureItem.getExpenditureCategory());
        createCell(row,columnIndex,expenditureItem.getExpenditureAmount().toString());
    }

    private void createCell(HSSFRow row, int columnIndex, String data) {
        HSSFCell cell = row.createCell(columnIndex);
        cell.setCellValue(data);
    }

    private void createSheetHeaders(HSSFSheet sheet, List<String> sheetHeaders) {
        HSSFRow row = sheet.createRow(0);
        for (int i = 0; i < sheetHeaders.size(); i++) {
            int columnWidth = 15 * 500;
            sheet.setColumnWidth(i, columnWidth);
            HSSFCell cell = row.createCell(i);
            cell.setCellValue(sheetHeaders.get(i));
        }
    }
}
