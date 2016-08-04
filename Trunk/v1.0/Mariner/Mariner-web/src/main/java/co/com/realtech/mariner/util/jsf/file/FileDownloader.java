/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.realtech.mariner.util.jsf.file;

import co.com.realtech.mariner.util.excel.ExcelToPdf;
import co.com.realtech.mariner.util.excel.ResultSetToExcel;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;

/**
 * Clase encargada de exportar un arreglo de datos con sus columnas a formato
 * excel
 *
 * @author fabianagudelo
 */
public class FileDownloader {

    /**
     * Crea el informe seleccionado en formato excel.
     *
     * @param colHeaders
     * @param datos
     * @param nombreHoja
     * @param nombreArchivo
     * @param contexto
     * @param descargar
     * @return 
     * @throws java.lang.Exception
     */
    public File construirExcel(List<String> colHeaders, List<Object> datos, String nombreHoja, String nombreArchivo, FacesContext contexto, boolean descargar) throws Exception {
        File tempFile = null;
        try {
            ResultSetToExcel rste = new ResultSetToExcel(nombreHoja);
            tempFile = File.createTempFile("reporteGeneral", ".xlsx");
            OutputStream os = new FileOutputStream(tempFile);
            rste.generate(os, datos, colHeaders);
            if(descargar){
                InputStream is = new FileInputStream(tempFile);
                byte[] bytes;
                bytes = IOUtils.toByteArray(is);
                descargarArchivo(contexto, bytes, nombreArchivo, "xlsx");
            }
        } catch (Exception e) {
            throw e;
        }
        return tempFile;
    }
    
    /**
     * Descarga un archivo del excel como PDF
     * @param archivoExcel
     * @param contexto
     * @param columnas
     * @return
     * @throws Exception 
     */
    public File descargarPdfDeExcel(File archivoExcel,FacesContext contexto, int columnas) throws Exception{
        File tempFile = null;
        try {
            tempFile = File.createTempFile("reporteGeneral", ".pdf");
            ExcelToPdf.convert(archivoExcel, tempFile, columnas);
            InputStream is = new FileInputStream(tempFile);
            byte[] bytes;
            bytes = IOUtils.toByteArray(is);
            descargarArchivo(contexto, bytes, "reporteGeneral","pdf");
        } catch (Exception e) {
            throw e;
        }
        return tempFile;
    }

    /**
     * Descargar excel a través del FacesContext.
     *
     * @param context
     * @param bytes
     * @param nombreArchivo
     */
    private static void descargarArchivo(FacesContext context, byte[] bytes, String nombreArchivo, String extension) {
        ExternalContext externalContext = context.getExternalContext();
        HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();
        try {
            try (ServletOutputStream servletOutputStream = response.getOutputStream()) {
                response.addHeader("Content-Type", "application/" + extension);
                response.addHeader("Content-Disposition", "attachment; filename=" + nombreArchivo + "." + extension);
                response.setContentLength(bytes.length);
                response.setContentType("application/" + extension);
                servletOutputStream.write(bytes);
                servletOutputStream.flush();
                context.responseComplete();
            }
        } catch (Exception e) {
            System.out.println("Error enviando reporte al cliente, error causado por " + e);
        }
    }
    
    /**
     * Descargar archivo a través del FacesContext.
     *
     * @param context
     * @param bytes
     * @param nombreArchivo
     */
    public void descargarArchivoFacesContext(FacesContext context, byte[] bytes, String nombreArchivo) {
        ExternalContext externalContext = context.getExternalContext();
        HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();
        try {
            if (bytes == null) {
                System.out.println("Bytes nulos en respuesta de PDF");
            } else {
                try (ServletOutputStream servletOutputStream = response.getOutputStream()) {
                    response.addHeader("Content-Type", "application/pdf");
                    response.addHeader("Content-Disposition", "attachment; filename=" + nombreArchivo + ".pdf");
                    response.setContentLength(bytes.length);
                    response.setContentType("application/pdf");
                    servletOutputStream.write(bytes);
                    servletOutputStream.flush();
                    context.responseComplete();
                }
            }
        } catch (Exception e) {
            System.out.println("Error enviando archivo PDF, error causado por " + e);
        }
    }

}
