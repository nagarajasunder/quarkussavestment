package com.geekydroid.savestmentbackend.utils;

import com.geekydroid.savestmentbackend.domain.expenditure.ExpenditureItem;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

public class ExpenditurePdfGenerator {

    public File createPdf(LocalDate startLocalDate, LocalDate endLocalDate, List<ExpenditureItem> expenditureItems) {

        File exportFile = new File("ExpenditureData.pdf");
        try (FileOutputStream fos = new FileOutputStream(exportFile)) {
            Document document = new Document(PageSize.A3);
            PdfWriter.getInstance(document, fos);
            document.open();
            Font headerFont = FontFactory.getFont(FontFactory.COURIER,14,BaseColor.BLACK);
            Font titleFont = FontFactory.getFont(FontFactory.COURIER,16,BaseColor.BLACK);
            Chunk titleChunk = new Chunk("Savestment Expenditure Report",titleFont);
            document.add(titleChunk);
            addDateRangeToDocument(document,startLocalDate,endLocalDate);
            Paragraph blankLine = new Paragraph("              ");
            document.add(blankLine);
            document.add(blankLine);
            PdfPTable table = new PdfPTable(6);
            setColumnWidths(table);
            table.setWidthPercentage(100);
            addTableHeader(table,headerFont);
            Double totalIncome = 0d;
            Double totalExpense = 0d;
            for (int i = 0; i < expenditureItems.size(); i++) {
                ExpenditureItem expenditureItem = expenditureItems.get(i);
                addRows(table, String.valueOf(i + 1));
                addRows(table, expenditureItem.getExpenditureDate().toString());
                addRows(table,expenditureItem.getExpenditureType());
                addRows(table, expenditureItem.getExpenditureCategory());
                addRows(table, expenditureItem.getExpenditureDescription());
                addRows(table, expenditureItem.getExpenditureAmount().toString());
                if (expenditureItem.getExpenditureType().equalsIgnoreCase("INCOME")) {
                    totalIncome+=expenditureItem.getExpenditureAmount();
                }
                else {
                    totalExpense+=expenditureItem.getExpenditureAmount();
                }
            }
            addRows(table,"");
            addRows(table,"");
            addRows(table,"");
            addRows(table,"");
            addRows(table,"Total Income");
            addRows(table,getAmountFormatted(totalIncome));

            addRows(table,"");
            addRows(table,"");
            addRows(table,"");
            addRows(table,"");
            addRows(table,"Total Expense");
            addRows(table,getAmountFormatted(totalExpense));

            addRows(table,"");
            addRows(table,"");
            addRows(table,"");
            addRows(table,"");
            addRows(table,"Balance");
            addRows(table,getAmountFormatted(totalIncome-totalExpense));

            document.add(table);

            document.close();
            return exportFile;
        } catch (Exception e) {
            return null;
        }
    }

    private void setColumnWidths(PdfPTable table) throws DocumentException {
        float totalWidth = 500f;
        table.setTotalWidth(totalWidth);
        float[] columnWidths = new float[]{totalWidth*0.1f,totalWidth*0.15f,totalWidth*0.15f,totalWidth*0.15f,totalWidth*0.3f,totalWidth*0.15f};
        table.setWidths(columnWidths);
    }

    private void addDateRangeToDocument(Document document, LocalDate startLocalDate, LocalDate endLocalDate) throws DocumentException {
        Font dateFont = FontFactory.getFont(FontFactory.COURIER);
        Paragraph startDate = new Paragraph("From "+startLocalDate.toString(),dateFont);
        Paragraph endDate = new Paragraph("To "+endLocalDate.toString(),dateFont);
        document.add(startDate);
        document.add(endDate);
    }

    private String getAmountFormatted(Double num) {
        return Constants.RUPEE_SYMBOL + " " +String.format("%.2f",num);
    }

    private void addRows(PdfPTable table, String data) {
        table.addCell(data);
    }

    private void addTableHeader(PdfPTable table,Font font) {
        Stream.of("S.No", "Date","Expenditure Type", "Category", "Notes", "Amount(in Rupees)").forEach(columnTitle -> {
            PdfPCell header = new PdfPCell();
            header.setBackgroundColor(BaseColor.LIGHT_GRAY);
            header.setBorderWidth(1);
            header.setPhrase(new Phrase(columnTitle,font));
            table.addCell(header);
        });
    }
}
