package com.controllers;

import checadorhorarios.Controller;
import java.io.DataOutputStream;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

public class ExportarReporteController extends Controller {
    private File archivo;
    private List<JTable> tabla;
    private List<String> nomArchivo;
    
    public ExportarReporteController(File archivo, List<JTable> tabla, List<String> nomArchivo) {
        this.archivo = archivo;
        this.tabla = tabla;
        this.nomArchivo = nomArchivo;
        
        if( nomArchivo.size() != tabla.size() ) {
            JOptionPane.showMessageDialog(null, "Lo sentimos, inconsistencia de datos.");
        }
    }
    
    //Método para exportar
    public boolean exportarReporte() {
        try {
            DataOutputStream salida = new DataOutputStream(new FileOutputStream(archivo));
            WritableWorkbook libroTrabajo = Workbook.createWorkbook(salida);
            
            for( int index=0 ; index<tabla.size() ; index++ ) {
                JTable tablaLocal = tabla.get(index);
                WritableSheet hoja = libroTrabajo.createSheet(nomArchivo.get(index), 0);
                
                /*for( int i=0 ; i<tablaLocal.getColumnCount() ; i++ ) {
                    
                    for( int j=0 ; j<tablaLocal.getRowCount() ; j++ ) {
                        Object objeto = tablaLocal.getValueAt(j,i);
                        hoja.addCell(new Label(i, j, String.valueOf(objeto)));
                    }
                    
                }*/
                
                for (int i = 0; i < tablaLocal.getColumnCount(); i++) {
                      for (int j = 0; j < tablaLocal.getRowCount(); j++) {
                            Object titulo = tablaLocal.getColumnName(i);
                            hoja.addCell(new Label(i+1, j+1, String.valueOf(titulo)));
                       }
                }
                for (int i = 0; i < tablaLocal.getColumnCount(); i++) {
                      for (int j = 0; j < tablaLocal.getRowCount(); j++) {
                            Object object = tablaLocal.getValueAt(j, i);
                            hoja.addCell(new Label(i+1, j+2, String.valueOf(object)));
                      }
                }
                
            }
            
            libroTrabajo.write();
            libroTrabajo.close();
            return true;
        } catch (FileNotFoundException e) {
            System.out.println("Error: " + e);
            return false;
        } catch (IOException | WriteException e) {
            System.out.println("Error: " + e);
            return false;
        }
    }
}
