/* Documentacion Nombre completo: Josue David Garcia Mendez Codigo Tecnico:IN5BV
 * Fecha Creacion: 11/4/24 Fecha Modificaciones: 19/5/24
 */
package org.josuegarcia.bean;


public class TelefonoProveedor {
     // declaramos variables para el crud de TelefonoProveedor
    private int codigoTelefonoProveedor;
    private String numeroPrincipal;
    private String numeroSecundario;
    private String observarciones;
    private int codigoProveedor;
 
    // constructor vacio
    public TelefonoProveedor() {
    }

    // constructor lleno
    public TelefonoProveedor(int codigoTelefonoProveedor, String numeroPrincipal, String numeroSecundario, String observarciones, int codigoProveedor) {
        this.codigoTelefonoProveedor = codigoTelefonoProveedor;
        this.numeroPrincipal = numeroPrincipal;
        this.numeroSecundario = numeroSecundario;
        this.observarciones = observarciones;
        this.codigoProveedor = codigoProveedor;
    }
    
    // metodos setters and getters
    public int getCodigoTelefonoProveedor() {
        return codigoTelefonoProveedor;
    }

    public void setCodigoTelefonoProveedor(int codigoTelefonoProveedor) {
        this.codigoTelefonoProveedor = codigoTelefonoProveedor;
    }

    public String getNumeroPrincipal() {
        return numeroPrincipal;
    }

    public void setNumeroPrincipal(String numeroPrincipal) {
        this.numeroPrincipal = numeroPrincipal;
    }

    public String getNumeroSecundario() {
        return numeroSecundario;
    }

    public void setNumeroSecundario(String numeroSecundario) {
        this.numeroSecundario = numeroSecundario;
    }

    public String getObservarciones() {
        return observarciones;
    }

    public void setObservarciones(String observarciones) {
        this.observarciones = observarciones;
    }

    public int getCodigoProveedor() {
        return codigoProveedor;
    }

    public void setCodigoProveedor(int codigoProveedor) {
        this.codigoProveedor = codigoProveedor;
    }
    
    
    
}
