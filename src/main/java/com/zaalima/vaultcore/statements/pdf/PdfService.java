package com.zaalima.vaultcore.statements.pdf;

import java.io.ByteArrayOutputStream;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.stereotype.Service;

import com.zaalima.vaultcore.statements.StatementTransaction;


@Service
public class PdfService {
    public byte[] renderMonthlyStatement(String accountHolder, int month, int year, double opening, List<StatementTransaction> txns, double closing) {
        try (PDDocument doc = new PDDocument()) {
            PDPage page = new PDPage(PDRectangle.LETTER);
            doc.addPage(page);

            try (PDPageContentStream cs = new PDPageContentStream(doc, page)) {
                cs.beginText();
                cs.setFont(PDType1Font.HELVETICA_BOLD, 14);
                cs.newLineAtOffset(50, 700);
                cs.showText("Monthly Statement");
                cs.endText();

                cs.beginText();
                cs.setFont(PDType1Font.HELVETICA, 10);
                cs.newLineAtOffset(50, 680);
                cs.showText("Account holder: " + sanitize(accountHolder));
                cs.endText();

                cs.beginText();
                cs.setFont(PDType1Font.HELVETICA, 10);
                cs.newLineAtOffset(50, 660);
                cs.showText(String.format("Period: %02d/%d", month, year));
                cs.endText();

                cs.beginText();
                cs.setFont(PDType1Font.HELVETICA, 10);
                cs.newLineAtOffset(50, 640);
                cs.showText(String.format("Opening balance: %.2f", opening));
                cs.endText();

                float y = 620f;
                for (StatementTransaction t : txns) {
                    if (y < 80) break; // simple paging guard
                    cs.beginText();
                    cs.setFont(PDType1Font.HELVETICA, 9);
                    cs.newLineAtOffset(50, y);
                    String line = String.format("%s | %s | %s | %.2f", t.timestamp, sanitize(t.reference), t.type, t.amount);
                    cs.showText(line);
                    cs.endText();
                    y -= 14f;
                }

                cs.beginText();
                cs.setFont(PDType1Font.HELVETICA_BOLD, 10);
                cs.newLineAtOffset(50, y - 10);
                cs.showText(String.format("Closing balance: %.2f", closing));
                cs.endText();
            }

            try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
                doc.save(baos);
                return baos.toByteArray();
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to render statement PDF", e);
        }
    }

    private String sanitize(String s) {
        if (s == null) return "";
        // basic escape - PDF text content does not execute, but avoid control chars
        return s.replaceAll("[\r\n\t]", " ");
    }
}
