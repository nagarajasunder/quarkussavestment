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
            Document document = new Document();
            PdfWriter.getInstance(document, fos);
            document.open();

            Font titleFont = FontFactory.getFont(FontFactory.COURIER,16, BaseColor.BLACK);
            Chunk titleChunk = new Chunk("Savestment Investment Report",titleFont);
            document.add(titleChunk);
            addDateRangeToDocument(document,startLocalDate,endLocalDate);
            Paragraph blankLine = new Paragraph("              ");
            document.add(blankLine);
            document.add(blankLine);
            PdfPTable table = new PdfPTable(8);
            addTableHeader(table);
            Double totalBuyAmount = 0d;
            Double totalSellAmount = 0d;
            for (int i = 0; i < equityItems.size(); i++) {
                EquityItem equityItem = equityItems.get(i);
                addRows(table, String.valueOf(i + 1));
                addRows(table, equityItem.getTradeDate().toString());
                addRows(table, equityItem.getInvestmentType());
                addRows(table, equityItem.getSymbol());
                addRows(table, equityItem.getPrice().toString());
                addRows(table, equityItem.getQuantity().toString());
                addRows(table, equityItem.getAmountInvested().toString());
                addRows(table, equityItem.getTradeType());
                if (equityItem.getTradeType().equalsIgnoreCase("BUY")) {
                    totalBuyAmount+=equityItem.getAmountInvested();
                }
                else {
                    totalSellAmount+=equityItem.getAmountInvested();
                }
            }
            document.add(table);
            Font font = FontFactory.getFont(FontFactory.COURIER,12, BaseColor.BLACK);
            document.add(new Paragraph("Total Buy Amount - "+getAmountFormatted(totalBuyAmount),font));
            document.add(new Paragraph("Total Sell Amount - "+getAmountFormatted(totalSellAmount),font));
            document.close();
            return exportFile;
        } catch (Exception e) {
            return null;
        }
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

    private void addTableHeader(PdfPTable table) {
        Stream.of("S.No", "Trade Date", "Category", "Symbol", "Price","Units","Trade Type","Amount(in Rupees)").forEach(columnTitle -> {
            PdfPCell header = new PdfPCell();
            header.setBackgroundColor(BaseColor.LIGHT_GRAY);
            header.setBorderWidth(2);
            header.setPhrase(new Phrase(columnTitle));
            table.addCell(header);
        });
    }
}
