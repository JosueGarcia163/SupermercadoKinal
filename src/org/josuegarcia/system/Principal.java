/* Documentacion Nombre completo: Josue David Garcia Mendez Codigo Tecnico:IN5BV
 * Fecha Creacion: 11/4/24 Fecha Modificaciones: 20/5/24
 */
package org.josuegarcia.system;

import java.io.IOException;
import java.io.InputStream;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.josuegarcia.controller.MenuPrincipalController;
import javafx.scene.image.Image;
import org.josuegarcia.controller.MenuCargoEmpleadoController;
import org.josuegarcia.controller.MenuClientesController;
import org.josuegarcia.controller.MenuComprasController;
import org.josuegarcia.controller.MenuDetalleCompraController;
import org.josuegarcia.controller.MenuDetalleFacturaController;
import org.josuegarcia.controller.MenuEmailProveedorController;
import org.josuegarcia.controller.MenuEmpleadosController;
import org.josuegarcia.controller.MenuFacturasController;
import org.josuegarcia.controller.MenuProductosController;
import org.josuegarcia.controller.MenuProgramadorController;
import org.josuegarcia.controller.MenuProveedorController;
import org.josuegarcia.controller.MenuTelefonoProveedorController;
import org.josuegarcia.controller.MenuTipoProductoController;

public class Principal extends Application {

    private Stage escenarioPrincipal;
    private Scene escena;
    private final String URLVIEW = "/org/josuegarcia/view/";

    // Metodo con sobre carga para mostar titulo y imagen
    @Override
    public void start(Stage escenarioPrincipal) throws Exception {
        this.escenarioPrincipal = escenarioPrincipal;
        this.escenarioPrincipal.setTitle("Kinal Express");
        menuPrincipalView();

        //Parent root = FXMLLoader.load(getClass().getResource("/org/luishernandez/view/MenuPrincipalView.fxml"));
        // Scene escena = new Scene(root);
        // escenarioPrincipal.setScene(escena);
        escenarioPrincipal.show();
    }

    // se hace el metodo inicializable cambiar escena
    public Initializable cambiarEscena(String fxmlName, int width, int heigth) throws Exception {
        Initializable resultado;
        FXMLLoader loader = new FXMLLoader();

        InputStream file = Principal.class.getResourceAsStream(URLVIEW + fxmlName);
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(Principal.class.getResource(URLVIEW + fxmlName));

        escena = new Scene((AnchorPane) loader.load(file), width, heigth);
        escenarioPrincipal.setScene(escena);
        escenarioPrincipal.sizeToScene();

        resultado = (Initializable) loader.getController();

        return resultado;
    }

    // Creamos el metodo para poder hacer el cambio de escena y mostrar la vista de la escena "MenuPrincipalView.fxml"
    public void menuPrincipalView() {
        try {
            MenuPrincipalController menuPrincipalView = (MenuPrincipalController) cambiarEscena("MenuPrincipalView.fxml", 454, 459);
            menuPrincipalView.setEscenarioPrincipal(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Creamos el metodo para poder hacer el cambio de escena y mostrar la vista de la escena "MenuClientesView.fxml"
    public void menuClientesView() {
        try {
            MenuClientesController menuClienteView = (MenuClientesController) cambiarEscena("MenuClientesView.fxml", 635, 398);
            menuClienteView.setEscenarioPrincipal(this);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // Creamos el metodo para poder hacer el cambio de escena y mostrar la vista de la escena "MenuProgramador.fxml"
    public void menuProgramadorView() {
        try {
            MenuProgramadorController menuProgramadorView = (MenuProgramadorController) cambiarEscena("MenuProgramador.fxml", 600, 400);
            menuProgramadorView.setEscenarioPrincipal(this);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // Creamos el metodo para poder hacer el cambio de escena y mostrar la vista de la escena "MenuProgramador.fxml"
    public void menuProveedoresView() {
        try {
            MenuProveedorController menuProveedorView = (MenuProveedorController) cambiarEscena("MenuProveedorView.fxml", 625, 527);
            menuProveedorView.setEscenarioPrincipal(this);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // Creamos el metodo para poder hacer el cambio de escena y mostrar la vista de la escena "MenuCargoEmpleadoView.fxml"
    public void MenuCargoEmpleadoView() {
        try {
            MenuCargoEmpleadoController menuCargoEmpleadoView = (MenuCargoEmpleadoController) cambiarEscena("MenuCargoEmpleadoView.fxml", 662, 468);
            menuCargoEmpleadoView.setEscenarioPrincipal(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Creamos el metodo para poder hacer el cambio de escena y mostrar la vista de la escena "MenuTipoProductoView.fxml"
    public void MenuTipoProductoView() {
        try {
            MenuTipoProductoController menuTipoProductoView = (MenuTipoProductoController) cambiarEscena("MenuTipoProductoView.fxml", 647, 478);
            menuTipoProductoView.setEscenarioPrincipal(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // Creamos el metodo para poder hacer el cambio de escena y mostrar la vista de la escena "MenuComprasView.fxml"

    public void MenuComprasView() {
        try {
            MenuComprasController menuComprasView = (MenuComprasController) cambiarEscena("MenuComprasView.fxml", 782, 557);
            menuComprasView.setEscenarioPrincipal(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Creamos el metodo para poder hacer el cambio de escena y mostrar la vista de la escena "MenuProductosView.fxml"
    public void MenuProductosView() {
        try {
            MenuProductosController menuProductosView = (MenuProductosController) cambiarEscena("MenuProductosView.fxml", 727, 510);
            menuProductosView.setEscenarioPrincipal(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Creamos el metodo para poder hacer el cambio de escena y mostrar la vista de la escena "MenuEmailProveedorView.fxml"
    public void MenuEmailProveedorView() {
        try {
            MenuEmailProveedorController menuEmailProveedorView = (MenuEmailProveedorController) cambiarEscena("MenuEmailProveedorView.fxml", 678, 502);
            menuEmailProveedorView.setEscenarioPrincipal(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Creamos el metodo para poder hacer el cambio de escena y mostrar la vista de la escena "MenuEmailProveedorView.fxml"
    public void MenuTelefonoProveedorView() {
        try {
            MenuTelefonoProveedorController menuTelefonoProveedorView = (MenuTelefonoProveedorController) cambiarEscena("MenuTelefonoProveedorView.fxml", 642, 459);
            menuTelefonoProveedorView.setEscenarioPrincipal(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Creamos el metodo para poder hacer el cambio de escena y mostrar la vista de la escena "MenuEmpleadoView.fxml"
    public void MenuEmpleadoView() {
        try {
            MenuEmpleadosController menuEmpleadoView = (MenuEmpleadosController) cambiarEscena("MenuEmpleadoView.fxml", 635, 490);
            menuEmpleadoView.setEscenarioPrincipal(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Creamos el metodo para poder hacer el cambio de escena y mostrar la vista de la escena "MenuDetalleCompraView.fxml"
    public void MenuDetalleCompraView() {
        try {
            MenuDetalleCompraController menuDetalleCompraView = (MenuDetalleCompraController) cambiarEscena("MenuDetalleCompraView.fxml", 628, 466);
            menuDetalleCompraView.setEscenarioPrincipal(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // Creamos el metodo para poder hacer el cambio de escena y mostrar la vista de la escena "MenuDetalleCompraView.fxml"
    public void MenuFacturasView() {
        try {
            MenuFacturasController menuFacturasView = (MenuFacturasController) cambiarEscena("MenuFacturasView.fxml", 661, 495);
            menuFacturasView.setEscenarioPrincipal(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // Creamos el metodo para poder hacer el cambio de escena y mostrar la vista de la escena "MenuDetalleCompraView.fxml"
    public void MenuDetalleFacturaView() {
        try {
            MenuDetalleFacturaController menuDetalleFacturasView = (MenuDetalleFacturaController) cambiarEscena("MenuDetalleFacturaView.fxml", 653, 482);
            menuDetalleFacturasView.setEscenarioPrincipal(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    

    public static void main(String[] args) {
        launch(args);
    }
}
