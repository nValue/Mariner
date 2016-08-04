/**
 * @author Fabian Agudelo Copyright(c) 2016 @Realtech, All Rights Reserved. This
 * software is the proprietary information of @Realtech Company.
 */
package co.com.realtech.mariner.util.excel;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Iterator;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelToPdf {

    public static void convert(File archivoExcel, File archivoPdf, int numColumnas) throws Exception {
        Font fuentePDF = FontFactory.getFont("/fonts/Slabo27px-Regular.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 0.8f, Font.BOLD, BaseColor.BLACK);
        BaseFont fuenteBasePDF = fuentePDF.getBaseFont();

        //First we read the Excel file in binary format into FileInputStream
        FileInputStream input_document = new FileInputStream(archivoExcel);
        // Read workbook into HSSFWorkbook
        XSSFWorkbook my_xls_workbook = new XSSFWorkbook(input_document);
        // Read worksheet into HSSFSheet
        XSSFSheet my_worksheet = my_xls_workbook.getSheetAt(0);
        // To iterate over the rows
        Iterator<Row> rowIterator = my_worksheet.iterator();
        //We will create output PDF document objects at this point
        Document iText_xls_2_pdf = new Document();
        Rectangle rc = new Rectangle(1000, 700);
        iText_xls_2_pdf.setPageSize(rc);
        iText_xls_2_pdf.setMargins(10, 10, 10, 10);
        PdfWriter.getInstance(iText_xls_2_pdf, new FileOutputStream(archivoPdf));
        iText_xls_2_pdf.open();
        //we have two columns in the Excel sheet, so we create a PDF table with two columns
        //Note: There are ways to make this dynamic in nature, if you want to.
        PdfPTable my_table = new PdfPTable(numColumnas);
        //We will use the object below to dynamically add new data to the table
        my_table.setTotalWidth(950);
        my_table.setLockedWidth(true);

        PdfPCell table_cell = new PdfPCell();

        PdfPCell celdaCabecera = new PdfPCell();
        celdaCabecera.setBackgroundColor(new BaseColor(57, 132, 197));

        PdfPCell celdaContenido = new PdfPCell();
        celdaContenido.setBackgroundColor(BaseColor.WHITE);

        Font fuenteEncabezado = new Font(fuenteBasePDF);
        fuenteEncabezado.setSize(4);
        fuenteEncabezado.setColor(BaseColor.WHITE);

        Font fuenteContenido = new Font(fuenteBasePDF);
        fuenteContenido.setSize(6);

        BaseColor colorActual;

        //Loop through rows.
        int i = 0;
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            Iterator<Cell> cellIterator = row.cellIterator();
            if (i == 0) {
                fuentePDF = fuenteEncabezado;
                colorActual = new BaseColor(57, 132, 197);
            } else {
                if ((i % 2) == 0) {
                    colorActual = new BaseColor(249, 249, 249);
                } else {
                    colorActual = BaseColor.WHITE;
                }
                fuentePDF = fuenteContenido;
            }
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                switch (cell.getCellType()) {
                    case Cell.CELL_TYPE_STRING:
                        //Push the data from Excel to PDF Cell
                        table_cell = new PdfPCell(new Phrase(cell.getRichStringCellValue().getString(), fuentePDF));
                        table_cell.setBackgroundColor(colorActual);
                        table_cell.setBorderWidth(0.5f);
                        table_cell.setBorderColor(new BaseColor(245, 245, 245));
                        //feel free to move the code below to suit to your needs
                        my_table.addCell(table_cell);
                        break;
                    case Cell.CELL_TYPE_NUMERIC:
                        table_cell = new PdfPCell(new Phrase(String.valueOf((int) cell.getNumericCellValue()), fuentePDF));
                        table_cell.setBackgroundColor(colorActual);
                        table_cell.setBorderWidth(0.5f);
                        table_cell.setBorderColor(new BaseColor(245, 245, 245));
                        my_table.addCell(table_cell);
                    //  case Cell.CELL_TYPE_ERROR:
                    //table_cell = new PdfPCell(new Phrase("!ERROR!",fuente));
                    //my_table.addCell(table_cell);   
                }
            }
            i++;
        }
        //float[] columnWidths = new float[] {10f, 20f, 30f, 10f};
        //my_table.setWidths(columnWidths);
        //Finally add the table to PDF document
        iText_xls_2_pdf.add(my_table);
        iText_xls_2_pdf.close();
        //we created our pdf file..
        input_document.close(); //close xls
    }

}
