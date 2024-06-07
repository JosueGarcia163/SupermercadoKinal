/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.josuegarcia.reports;

import java.io.InputStream;
import java.util.Map;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.josuegarcia.db.Conexion;


public class GenerarReportes {
    public static void mostrarReportes(String nombreReporte, String titulo, Map parametro){
        InputStream reporte = GenerarReportes.class.getResourceAsStream(nombreReporte);
     try{
     JasperReport reporteMaestro = (JasperReport)JRLoader.loadObject(reporte);
     JasperPrint reporteImpreso = JasperFillManager.fillReport(reporteMaestro, parametro, Conexion.getInstance().getConexion());
     JasperViewer visor = new JasperViewer(reporteImpreso, false);
     visor.setTitle(titulo);
     visor.setVisible(true);
     
     
     
     
     }catch(Exception e){e.printStackTrace();}   
        
    }
    
    /*
    Interface Map
    HashMap es uno de los objetos  que implementa un conjunto de key-valu.
    Tiene un constructor sin parametros new HashMap() y su finalidad suele
    referirse para agrupar informacion en un unico objeto.
    
    tiene cierta similitud con la conexion de objetos ArrayList, el hashing hace referencia 
    a una tecnica de organizacion de archivos hashing(abierto-cerrado) en la que 
    se almacena el registro de una direccion que es generada por una funcion se aplica a la llave
    del registro de una direccion que es generada por una funcion se aplica a la llave del registro
    dentro de memoria fisica.
    
    En java el Hasmap posee un espacio de memoria y cuando se guarda un objeto en dicho
    espacio se determina su direccion, aplicando una funcion a la llave que le indiquemos
    cada objeto se identifica mediante algun identificador apropiado.
    */
    
    
}
