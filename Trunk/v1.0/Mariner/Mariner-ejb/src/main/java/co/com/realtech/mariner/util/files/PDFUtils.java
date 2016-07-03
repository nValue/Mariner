/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.realtech.mariner.util.files;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * Clase encargada de agregar texto a algunos PDFs
 * @author fabianagudelo
 */
public class PDFUtils {
    
    public static File agregarTexto(byte[] bytes, String text) {
        File temp = null;
        try {
            temp = File.createTempFile("archivo", ".pdf");
            OutputStream oos = new FileOutputStream(temp);
            Document document = new Document();
            PdfWriter writer = PdfWriter.getInstance(document, oos);
            document.open();
            PdfContentByte cb = writer.getDirectContent();
            ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
            //InputStream targetStream = new FileInputStream(initialFile);
            // Load existing PDF
            PdfReader reader = new PdfReader(bis);
            PdfImportedPage page = writer.getImportedPage(reader, 1);
            // Copy first page of existing PDF into output PDF
            document.setPageSize(reader.getPageSize(1));
            document.newPage();
            cb.addTemplate(page, 0, 0);
            ColumnText ct = new ColumnText(cb);
            Phrase myText = new Phrase(text);
            Font fuente = new Font();
            fuente.setSize(6);
            myText.setFont(fuente);
            ct.setSimpleColumn(myText, 0, -1, document.right(), document.top(), -10, Element.ALIGN_RIGHT);
            ct.go();
            document.close();
        } catch (Exception e) {
            System.out.println("e = " + e);
        }
        return temp;
    }

}