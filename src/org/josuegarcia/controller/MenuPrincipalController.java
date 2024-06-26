/* Documentacion Nombre completo: Josue David Garcia Mendez Codigo Tecnico:IN5BV
 * Fecha Creacion: 11/4/24 Fecha Modificaciones: 22/5/24
 */
package org.josuegarcia.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import org.josuegarcia.system.Principal;

public class MenuPrincipalController implements Initializable {

    private Principal escenarioPrincipal;

    @FXML
    private MenuItem btnMenuClientes;

    @FXML
    private MenuItem btnProgramador;

    @FXML
    private MenuItem btnMenuProveedores;

    @FXML
    private MenuItem btnMenuCargoEmpleado;

    @FXML
    private MenuItem btnMenuTipoProducto;

    @FXML
    private MenuItem btnMenuCompras;

    @FXML
    private MenuItem btnProductos;

    @FXML
    private MenuItem btnEmailProveedor;

    @FXML
    private MenuItem btnTelefonoProveedor;

    @FXML
    private MenuItem btnEmpleados;
    
    @FXML
    private MenuItem btnDetalleCompra;

    @FXML
    private MenuItem btnFacturas;
    
    @FXML
    private MenuItem btnDetalleFactura;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    // Creamos el metodo buttonHandleEvent para que podamos avanzar a los menus correspondientes
    @FXML
    public void buttonHandleEvent(ActionEvent event) throws IOException {
        if (event.getSource() == btnMenuClientes) {
            escenarioPrincipal.menuClientesView();

        } else if (event.getSource() == btnProgramador) {
            escenarioPrincipal.menuProgramadorView();
        } else if (event.getSource() == btnMenuProveedores) {
            escenarioPrincipal.menuProveedoresView();
        } else if (event.getSource() == btnMenuCargoEmpleado) {
            escenarioPrincipal.MenuCargoEmpleadoView();
        } else if (event.getSource() == btnMenuTipoProducto) {
            escenarioPrincipal.MenuTipoProductoView();
        } else if (event.getSource() == btnMenuCompras) {
            escenarioPrincipal.MenuComprasView();
        } else if (event.getSource() == btnProductos) {
            escenarioPrincipal.MenuProductosView();
        } else if (event.getSource() == btnEmailProveedor) {
            escenarioPrincipal.MenuEmailProveedorView();
        } else if (event.getSource() == btnEmpleados) {
            escenarioPrincipal.MenuEmpleadoView();
        }else if (event.getSource() == btnDetalleCompra) {
            escenarioPrincipal.MenuDetalleCompraView();
        }else if (event.getSource() == btnFacturas) {
            escenarioPrincipal.MenuFacturasView();
        }else if (event.getSource() == btnDetalleFactura) {
            escenarioPrincipal.MenuDetalleFacturaView();
        }else if (event.getSource() == btnTelefonoProveedor) {
            escenarioPrincipal.MenuTelefonoProveedorView();
        }
        

    }

}
