/* Documentacion Nombre completo: Josue David Garcia Mendez Codigo Tecnico:IN5BV
 * Fecha Creacion: 11/4/24 Fecha Modificaciones: 9/5/24
 */
package org.josuegarcia.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    private Connection conexion;
    private static Conexion instancia;

    // Se crea el metodo conexion
    public Conexion() {
        try {
            // se crea el try con la conexion mysql
            // declaramos el driver que tiene el jdbc
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            // creamos la instancia con los parametros de nuestra base de datos
             // conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/DBKinalExpress?useSSL=false", "2023324_IN5BV", "abc123!!");

             conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/DBKinalExpress?useSSL=false", "root", "Admin");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();

        } catch (InstantiationException e) {
            e.printStackTrace();

        } catch (IllegalAccessException e) {
            e.printStackTrace();

        } catch (SQLException e) {
            e.printStackTrace();

        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    public static Conexion getInstance() {
        if (instancia == null) {
            instancia = new Conexion();

        }

        return instancia;
    }

    public Connection getConexion() {
        return conexion;
    }

    public void setConexion(Connection conexion) {
        this.conexion = conexion;
    }

}
