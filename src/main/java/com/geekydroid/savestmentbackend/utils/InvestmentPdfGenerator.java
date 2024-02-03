package com.geekydroid.savestmentbackend.utils;

import com.geekydroid.savestmentbackend.domain.investment.EquityItem;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

public class InvestmentPdfGenerator {

    public File createPdf(LocalDate startLocalDate, LocalDate endLocalDate, List<EquityItem> equityItems) {

        File exportFile = new File("InvestmentData.pdf");
        try (FileOutputStream fos = new FileOutputStream(exportFile)) {
            Document document = new Document(PageSize.A3);
            PdfWriter.getInstance(document, fos);
            document.open();
            Font pdfFont = FontFactory.getFont(FontFactory.COURIER,10, BaseColor.BLACK);
            Font titleFont = FontFactory.getFont(FontFactory.COURIER,16, BaseColor.BLACK);
            Chunk titleChunk = new Chunk("Savestment Investment Report",titleFont);
            document.add(titleChunk);
            addDateRangeToDocument(document,startLocalDate,endLocalDate);
            Paragraph blankLine = new Paragraph("              ");
            document.add(blankLine);
            document.add(blankLine);
            PdfPTable table = new PdfPTable(8);
            setColumnWidths(table);
            table.setWidthPercentage(100);
            addTableHeader(table,pdfFont);
            Double totalBuyAmount = 0d;
            Double totalSellAmount = 0d;
            for (int i = 0; i < equityItems.size(); i++) {
                EquityItem equityItem = equityItems.get(i);
                addRows(table, String.valueOf(i + 1),pdfFont);
                addRows(table, equityItem.getTradeDate().toString(),pdfFont);
                addRows(table, equityItem.getInvestmentType(),pdfFont);
                addRows(table, equityItem.getSymbol(),pdfFont);
                addRows(table, equityItem.getPrice().toString(),pdfFont);
                addRows(table, equityItem.getQuantity().toString(),pdfFont);
                addRows(table, equityItem.getTradeType(),pdfFont);
                addRows(table, equityItem.getAmountInvested().toString(),pdfFont);
                if (equityItem.getTradeType().equalsIgnoreCase("BUY")) {
                    totalBuyAmount+=equityItem.getAmountInvested();
                }
                else {
                    totalSellAmount+=equityItem.getAmountInvested();
                }
            }
            addRows(table,"",pdfFont);
            addRows(table,"",pdfFont);
            addRows(table,"",pdfFont);
            addRows(table,"",pdfFont);
            addRows(table,"",pdfFont);
            addRows(table,"",pdfFont);
            addRows(table,"Total Buy Amount",pdfFont);
            addRows(table,getAmountFormatted(totalBuyAmount),pdfFont);

            addRows(table,"",pdfFont);
            addRows(table,"",pdfFont);
            addRows(table,"",pdfFont);
            addRows(table,"",pdfFont);
            addRows(table,"",pdfFont);
            addRows(table,"",pdfFont);
            addRows(table,"Total Sell Amount",pdfFont);
            addRows(table,getAmountFormatted(totalSellAmount),pdfFont);
            document.add(table);
            document.close();
            return exportFile;
        } catch (Exception e) {
            return null;
        }
    }

    private void setColumnWidths(PdfPTable table) throws DocumentException {
        float totalWidth = 800f;
        table.setTotalWidth(totalWidth);
        float[] columnWidths = new float[]{totalWidth*0.05f,totalWidth*0.15f,totalWidth*0.2f,totalWidth*0.2f,totalWidth*0.1f,totalWidth*0.1f,totalWidth*0.1f,totalWidth*0.1f};
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

    private void addRows(PdfPTable table, String data,Font font) {
        table.addCell(new Phrase(data,font));
    }

    private void addTableHeader(PdfPTable table,Font font) {
        Stream.of("S.No", "Date", "Category", "Symbol", "Price(in Rupees)","Units","Trade Type","Amount(in Rupees)").forEach(columnTitle -> {
            PdfPCell header = new PdfPCell();
            header.setBackgroundColor(BaseColor.LIGHT_GRAY);
            header.setBorderWidth(1);
            header.setPhrase(new Phrase(columnTitle,font));
            table.addCell(header);
        });
    }
}
