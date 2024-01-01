package com.geekydroid.savestmentbackend.utils;

import com.geekydroid.savestmentbackend.domain.expenditure.CategoryRespnose;
import com.geekydroid.savestmentbackend.domain.investment.EquityItem;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class InvestmentExcelGenerator {

    int serialNumber = 1;

    public File createExcel(List<CategoryRespnose> investmentTypes, List<EquityItem> equityItems) throws IOException {

        try (HSSFWorkbook workbook = createWorkBook(investmentTypes, equityItems)) {
            File exportFile = new File("equityData.xls");
            FileOutputStream fos = new FileOutputStream(exportFile);
            workbook.write(fos);
            return exportFile;
        }
    }

    private HSSFWorkbook createWorkBook(List<CategoryRespnose> investmentTypes, List<EquityItem> equityItems) {
        HSSFWorkbook workbook = new HSSFWorkbook();
        Map<String, List<EquityItem>> equityItemMap = groupInvestmentsByTypes(investmentTypes, equityItems);
        List<String> sheetHeaders = List.of("S.No","Symbol","Trade Date","Trade Type","Units","Price","Amount Invested");
        for (Map.Entry<String, List<EquityItem>> entry : equityItemMap.entrySet()) {
            List<EquityItem> groupedEquityItems = entry.getValue();
            if (!groupedEquityItems.isEmpty()) {
                serialNumber = 1;
                HSSFSheet sheet = workbook.createSheet(entry.getKey());
                createSheetHeader(sheet,sheetHeaders);
                for (int i = 0;i<groupedEquityItems.size();i++) {
                    addDataInRow(i+1,sheet,groupedEquityItems.get(i));
                }
            }
        }
        return workbook;
    }

    private void addDataInRow(int rowIndex, HSSFSheet sheet, EquityItem equityItem) {
        HSSFRow row = sheet.createRow(rowIndex);
        int columnIndex = 0;
        createCell(row,columnIndex++,String.valueOf(serialNumber++));
        createCell(row,columnIndex++,equityItem.getSymbol());
        createCell(row,columnIndex++,equityItem.getTradeDate().toString());
        createCell(row,columnIndex++,equityItem.getTradeType());
        createCell(row,columnIndex++,equityItem.getQuantity().toString());
        createCell(row,columnIndex++,equityItem.getPrice().toString());
        createCell(row,columnIndex,equityItem.getAmountInvested().toString());

    }

    private void createCell(HSSFRow rowIndex,int columnIndex,String value) {
        HSSFCell cell = rowIndex.createCell(columnIndex);
        cell.setCellValue(value);
    }

    private void createSheetHeader(HSSFSheet sheet, List<String> sheetHeaders) {
        HSSFRow row = sheet.createRow(0);
        for (int i = 0;i<sheetHeaders.size();i++) {
            int columnWidth = 15*500;
            sheet.setColumnWidth(i,columnWidth);
            HSSFCell cell = row.createCell(i);
            cell.setCellValue(sheetHeaders.get(i));
        }
    }

    private Map<String, List<EquityItem>> groupInvestmentsByTypes(List<CategoryRespnose> investmentTypes, List<EquityItem> equityItems) {

        Map<String, List<EquityItem>> equityItemMap = new HashMap<>();

        for (CategoryRespnose investmentType : investmentTypes) {
            List<EquityItem> filteredEquityItems = equityItems.stream().filter(
                    equityItem -> Objects.equals(equityItem.getInvestmentTypeId(), investmentType.getId())).toList();
            equityItemMap.put(investmentType.getName(), filteredEquityItems);
        }

        return equityItemMap;

    }

}
