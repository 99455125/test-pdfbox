package org.example;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello World!");
        // 查找pdf文件中的文本
        findTextInPdf("技术人才培养", "D://pdfTest/aa.pdf");

        // 增加页码
        addPageNumbers("D://pdfTest/aa.pdf", "D://pdfTest/aa_out.pdf");
    }

    private static void addPageNumbers(String filePath, String outputFilePath) {
        try (PDDocument document = PDDocument.load(new File(filePath))) {
            int pageCount = document.getNumberOfPages();

            for (int i = 0; i < pageCount; i++) {
                PDPage page = document.getPage(i);
                try (PDPageContentStream contentStream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true, true)) {
                    contentStream.beginText();
                    // Load the SimSun font
                    PDType0Font simSunFont = PDType0Font.load(document, new File("D://pdfTest/simsun.ttf"));

                    // Set the font to SimSun
                    contentStream.setFont(simSunFont, 12);

                    contentStream.newLineAtOffset(20, 20); // Adjust the position as needed
                    contentStream.showText("Page " + (i + 1) + " of " + pageCount);
                    contentStream.endText();
                }
            }

            document.save(outputFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void findTextInPdf(String text, String path) {
        try (PDDocument document = PDDocument.load(new File(path))) {
            PDFTextStripper pdfStripper = new PDFTextStripper();
            String documentText = pdfStripper.getText(document);

            if (documentText.contains(text)) {
                System.out.println("Text found!");
            } else {
                System.out.println("Text not found.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}