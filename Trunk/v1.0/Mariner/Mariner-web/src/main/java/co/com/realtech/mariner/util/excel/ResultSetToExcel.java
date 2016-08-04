package co.com.realtech.mariner.util.excel;

import org.apache.poi.hssf.usermodel.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.util.HSSFCellUtil;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellUtil;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

public class ResultSetToExcel {

    private SXSSFWorkbook workbook;
    private Sheet sheet;
    private Font contentFont;
    private Font headerFont;
    private DataFormat format;
    private CellStyle headerStyle;
    private CellStyle contentStyle;
    private FormatType[] formatTypes;

    public ResultSetToExcel(FormatType[] formatTypes, String sheetName) {
        workbook = new SXSSFWorkbook();
        
        //Color col = new Color(90, 156, 209);
        //XSSFColor xColHead = new XSSFColor(col);
        
        sheet = workbook.createSheet(sheetName);
        format = workbook.createDataFormat();
        
        headerStyle = workbook.createCellStyle();
        headerFont = workbook.createFont();
        contentStyle = workbook.createCellStyle();
        contentFont = workbook.createFont();
        headerStyle.setFillForegroundColor(IndexedColors.SEA_GREEN.getIndex());
        //headerStyle.setFillForegroundColor(xColHead.getIndexed());
        headerStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
        headerFont.setColor(HSSFColor.WHITE.index);
        headerFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        headerFont.setFontHeightInPoints((short) 10);
        contentFont.setFontHeightInPoints((short) 8);

        headerStyle.setFont(headerFont);
        headerStyle.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
        headerStyle.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
        headerStyle.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
        headerStyle.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
        headerStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);

        contentStyle.setFont(contentFont);
        Short borderColor = HSSFColor.GREY_40_PERCENT.index;
        contentStyle.setBottomBorderColor(borderColor);
        contentStyle.setTopBorderColor(borderColor);
        contentStyle.setLeftBorderColor(borderColor);
        contentStyle.setRightBorderColor(borderColor);
        contentStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        contentStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        contentStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        contentStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);

        this.formatTypes = formatTypes;
    }

    public ResultSetToExcel(String sheetName) {
        this(null, sheetName);
    }

    private FormatType getFormatType(Class _class) {
        if (_class == Integer.class || _class == Long.class) {
            return FormatType.INTEGER;
        } else if (_class == Float.class || _class == Double.class) {
            return FormatType.FLOAT;
        } else if (_class == Timestamp.class || _class == java.sql.Date.class) {
            return FormatType.DATE;
        } else {
            return FormatType.TEXT;
        }
    }

    public void generate(final OutputStream outputStream, List<Object> informacion, List<String> colHeaders) throws Exception {
        try {
            final int numeroFilas = informacion.size();
            //ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            /**
             * if (formatTypes != null && formatTypes.length !=
             * resultSetMetaData.getColumnCount()) { throw new
             * IllegalStateException("Number of types is not identical to number
             * of resultset columns. " + "Number of types: " +
             * formatTypes.length + ". Number of columns: " +
             * resultSetMetaData.getColumnCount()); }*
             */
            int currentRow = 0;
            Row row = sheet.createRow(currentRow);
            Object[] valor1 = (Object[]) informacion.get(0);
            System.out.println("colHeaders.size() = " + colHeaders.size());
            System.out.println("valor1 = " + valor1.length);
            //Crea los headers de las columnas
            System.out.println("Numero de filas .. " + numeroFilas);
            boolean isAutoDecideFormatTypes;
            if (isAutoDecideFormatTypes = (formatTypes == null)) {
                formatTypes = new FormatType[colHeaders.size()];
            }
            Cell cell;
            for (int i = 0; i < valor1.length; i++) {
                String title = colHeaders.get(i);
                //row.setRowStyle(headerStyle);
                title = title.toUpperCase();
                cell = row.createCell(i);
                cell.setCellValue(title);
                cell.setCellStyle(headerStyle);
                //writeCell(row, i, title, FormatType.TEXT, boldFont);
                if (isAutoDecideFormatTypes) {
                    Class _class;
                    if (valor1[i] == null) {
                        _class = String.class;
                    } else {
                        _class = valor1[i].getClass();
                    }
                    formatTypes[i] = getFormatType(_class);
                }
            }
            currentRow++;
            
            //Crea el contenido del excel
            Cell cellContent;
            ArrayList<Class> clases = new ArrayList<>();
            //Object columnas = informacion.get(0);
            for (Object objeto : informacion) {
                //row.setRowStyle(contentStyle);
                row = sheet.createRow(currentRow++);
                Object[] cols = (Object[]) objeto;
                for (int i = 0; i < valor1.length; i++) {
                    Object value = cols[i];
                    if (value == null) {
                        value = "";
                    }
                    cellContent = row.createCell(i);
                    CharSequence cs = value.toString();
                    try {
                        if (StringUtils.isNumeric(cs)) {
                            if (objeto.equals(informacion.get(0))) {
                                System.out.println("Numero -> " + cs);
                                clases.add(Integer.class);
                            }
                            cellContent.setCellValue(Integer.parseInt(value.toString()));
                        } else {
                            if (objeto.equals(informacion.get(0))) {
                                System.out.println("String -> " + cs);
                                clases.add(String.class);
                            }
                            cellContent.setCellValue(value.toString());
                        }
                    } catch (NumberFormatException e) {
                        cellContent.setCellValue(value.toString());
                    }
                    cellContent.setCellStyle(contentStyle);
                    //writeCell(row, i, value, formatTypes[i]);
                }
            }
            Cell cellEmpty;
            row = sheet.createRow(currentRow++);
            cellEmpty = row.createCell(colHeaders.size());
            cellEmpty.setCellValue("");
            
            Cell cellResumeTitle;
            Cell cellResume;
            int z = 0;
            for (Class clase : clases) {
                if(clase.equals(Integer.class) && colHeaders.get(z).contains("$")){
                    row = sheet.createRow(currentRow++);
                    cellResumeTitle = row.createCell(colHeaders.size()-2);
                    cellResumeTitle.setCellValue(colHeaders.get(z));
                    cellResumeTitle.setCellStyle(headerStyle);
                    cellResume = row.createCell(colHeaders.size()-1);
                    
                    String strFormula = "SUM("+toAlphabetic(z)+"2:"+toAlphabetic(z) +(numeroFilas + 1) + ")";
                    System.out.println("strFormula = " + strFormula);
                    cellResume.setCellType(HSSFCell.CELL_TYPE_FORMULA);
                    cellResume.setCellFormula(strFormula);
                    cellResume.setCellStyle(contentStyle);
                }
                z++;
            }
            // Autosize columns
            for (int i = 0; i < colHeaders.size(); i++) {
                sheet.autoSizeColumn((short) i);
            }
            workbook.write(outputStream);
            outputStream.close();
        } finally {
            //outputStream.close();
        }
    }
    
    public String toAlphabetic(int i) {
        if (i < 0) {
            return "-" + toAlphabetic(-i - 1);
        }
        int quot = i / 26;
        int rem = i % 26;
        char letter = (char) ((int) 'A' + rem);
        if (quot == 0) {
            return "" + letter;
        } else {
            return toAlphabetic(quot - 1) + letter;
        }
    }

    public void generate(File file, List<Object> informacion, List<String> colHeaders) throws Exception {
        generate(new FileOutputStream(file), informacion, colHeaders);
    }

    private void writeCell(HSSFRow row, int col, Object value, FormatType formatType) {
        writeCell(row, col, value, formatType, null, null);
    }

    private void writeCell(HSSFRow row, int col, Object value, FormatType formatType, HSSFFont font) {
        writeCell(row, col, value, formatType, null, font);
    }

    private void writeCell(HSSFRow row, int col, Object value, FormatType formatType,
            Short bgColor, HSSFFont font) {
        HSSFCell cell = HSSFCellUtil.createCell(row, col, null);
        if (value == null) {
            return;
        }

        if (font != null) {
            CellStyle style = workbook.createCellStyle();
            style.setFont(font);
            cell.setCellStyle(style);
        }

        switch (formatType) {
            case TEXT:
                cell.setCellValue(value.toString());
                break;
            case INTEGER:
                cell.setCellValue(((Number) value).intValue());
                CellUtil.setCellStyleProperty(cell, workbook, CellUtil.DATA_FORMAT,
                        HSSFDataFormat.getBuiltinFormat(("#,##0")));
                break;
            case FLOAT:
                cell.setCellValue(((Number) value).doubleValue());
                CellUtil.setCellStyleProperty(cell, workbook, CellUtil.DATA_FORMAT,
                        HSSFDataFormat.getBuiltinFormat(("#,##0.00")));
                break;
            case DATE:
                cell.setCellValue((Timestamp) value);
                CellUtil.setCellStyleProperty(cell, workbook, CellUtil.DATA_FORMAT,
                        HSSFDataFormat.getBuiltinFormat(("m/d/yy")));
                break;
            case MONEY:
                cell.setCellValue(((Number) value).intValue());
                CellUtil.setCellStyleProperty(cell, workbook, CellUtil.DATA_FORMAT,
                        format.getFormat("($#,##0.00);($#,##0.00)"));
                break;
            case PERCENTAGE:
                cell.setCellValue(((Number) value).doubleValue());
                CellUtil.setCellStyleProperty(cell, workbook, CellUtil.DATA_FORMAT,
                        HSSFDataFormat.getBuiltinFormat("0.00%"));
        }

        if (bgColor != null) {
            CellUtil.setCellStyleProperty(cell, workbook, CellUtil.FILL_FOREGROUND_COLOR, bgColor);
            CellUtil.setCellStyleProperty(cell, workbook, CellUtil.FILL_PATTERN, HSSFCellStyle.SOLID_FOREGROUND);
        }
    }

    public enum FormatType {

        TEXT,
        INTEGER,
        FLOAT,
        DATE,
        MONEY,
        PERCENTAGE
    }
}
