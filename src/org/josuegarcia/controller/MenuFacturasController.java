/* Documentacion Nombre completo: Josue David Garcia Mendez Codigo Tecnico:IN5BV
 * Fecha Creacion: 11/4/24 Fecha Modificaciones: 21/5/24
 */
package org.josuegarcia.controller;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.swing.JOptionPane;
import org.josuegarcia.bean.Clientes;
import org.josuegarcia.bean.Empleados;
import org.josuegarcia.bean.Facturas;
import org.josuegarcia.db.Conexion;
import org.josuegarcia.reports.GenerarReportes;
import org.josuegarcia.system.Principal;

public class MenuFacturasController implements Initializable {

    private enum operaciones {
        AGREGAR, ELIMINAR, EDITAR, CANCELAR, NINGUNO
    }
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;
    private ObservableList<Facturas> listaFacturas;
    private ObservableList<Clientes> listaClientes;
    private ObservableList<Empleados> listaEmpleados;

    private Principal escenarioPrincipal;

    @FXML
    private Button btnRegresar;
    @FXML
    private Button btnIrDetalleFac;
    @FXML
    private TextField txtNumeroF;
    @FXML
    private TextField txtEstado;
    @FXML
    private TextField txtTotalF;
    @FXML
    private TextField txtFechaF;
    @FXML
    private ComboBox cmbCodigoCliente;
    @FXML
    private ComboBox cmbCodigoEmpleado;
    @FXML
    private TableView tbFacturas;
    @FXML
    private TableColumn colNumeroFactura;
    @FXML
    private TableColumn colEstado;
    @FXML
    private TableColumn colTotalFactura;
    @FXML
    private TableColumn colFechaFactura;
    @FXML
    private TableColumn colCodigoCliente;
    @FXML
    private TableColumn colCodigoEmpleado;
    @FXML
    private Button btnAgregar;
    @FXML
    private Button btnEliminar;
    @FXML
    private Button btnEditar;
    @FXML
    private Button btnReportes;
    @FXML
    private ImageView imgAgregar;
    @FXML
    private ImageView imgEliminar;
    @FXML
    private ImageView imgEditar;
    @FXML
    private ImageView imgReporte;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        CargarDatos();
        cmbCodigoCliente.setItems(getClientes());
        cmbCodigoEmpleado.setItems(getEmpleados());
    }

    // Este metodo nos sirve para listar los datos agregados
    public void CargarDatos() {
        tbFacturas.setItems(getFacturas());
        colNumeroFactura.setCellValueFactory(new PropertyValueFactory<Facturas, Integer>("numeroFactura"));
        colEstado.setCellValueFactory(new PropertyValueFactory<Facturas, String>("estado"));
        colTotalFactura.setCellValueFactory(new PropertyValueFactory<Facturas, Double>("totalFactura"));
        colFechaFactura.setCellValueFactory(new PropertyValueFactory<Facturas, String>("fechaFactura"));
        colCodigoCliente.setCellValueFactory(new PropertyValueFactory<Facturas, Integer>("codigoCliente"));
        colCodigoEmpleado.setCellValueFactory(new PropertyValueFactory<Facturas, Integer>("codigoEmpleado"));

    }

    // Es para identificar la tupla seleccionada por el usuario en la ventana de JavaFx
    public void seleccionarElemento() {
        txtNumeroF.setText(String.valueOf(((Facturas) tbFacturas.getSelectionModel().getSelectedItem()).getNumeroFactura()));
        txtEstado.setText(((Facturas) tbFacturas.getSelectionModel().getSelectedItem()).getEstado());
        txtTotalF.setText(String.valueOf(((Facturas) tbFacturas.getSelectionModel().getSelectedItem()).getTotalFactura()));
        txtFechaF.setText(String.valueOf(((Facturas) tbFacturas.getSelectionModel().getSelectedItem()).getFechaFactura()));

        cmbCodigoCliente.getSelectionModel().select(buscarCliente(((Facturas) tbFacturas.getSelectionModel().getSelectedItem()).getCodigoCliente()));
        cmbCodigoEmpleado.getSelectionModel().select(buscarEmpleados(((Facturas) tbFacturas.getSelectionModel().getSelectedItem()).getCodigoEmpleado()));

    }

    // Es para buscar el elemento de la llave foranea para mostrarla en la comboBox
    public Clientes buscarCliente(int codigoCliente) {
        Clientes resultado = null;
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarClientes(?)}");
            procedimiento.setInt(1, codigoCliente);
            ResultSet registro = procedimiento.executeQuery();

            while (registro.next()) {
                resultado = new Clientes(registro.getInt("codigoCliente"),
                        registro.getString("NITcliente"),
                        registro.getString("nombreCliente"),
                        registro.getString("apellidoCliente"),
                        registro.getString("direccionCliente"),
                        registro.getString("telefonoCliente"),
                        registro.getString("correoCliente")
                );

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultado;
    }

    // Es para buscar el elemento de la llave foranea para mostrarla en la comboBox
    public Empleados buscarEmpleados(int codigoEmpleado) {
        Empleados resultado = null;
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarEmpleados(?)}");
            procedimiento.setInt(1, codigoEmpleado);
            ResultSet registro = procedimiento.executeQuery();

            while (registro.next()) {
                resultado = new Empleados(registro.getInt("codigoEmpleado"),
                        registro.getString("nombresEmpleado"),
                        registro.getString("apellidosEmpleado"),
                        registro.getDouble("sueldo"),
                        registro.getString("direccion"),
                        registro.getString("turno"),
                        registro.getInt("codigoCargoEmpleado")
                );

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultado;
    }

    public ObservableList<Facturas> getFacturas() {
        // Crea Arraylist llamada lista para almacenar los datos del listar
        ArrayList<Facturas> lista = new ArrayList<Facturas>();

        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarFactura()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new Facturas(resultado.getInt("numeroFactura"),
                        resultado.getString("estado"),
                        resultado.getDouble("totalFactura"),
                        resultado.getString("fechaFactura"),
                        resultado.getInt("codigoCliente"),
                        resultado.getInt("codigoEmpleado")
                ));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Almacena en nuestro ObservableList listaTipoProducto lo agregado anteriormente en lista
        return listaFacturas = FXCollections.observableArrayList(lista);
    }

    public ObservableList<Clientes> getClientes() {
        // Crea Arraylist llamada lista para almacenar los datos del listar
        ArrayList<Clientes> lista = new ArrayList();

        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarClientes()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new Clientes(resultado.getInt("codigoCliente"),
                        resultado.getString("NITcliente"),
                        resultado.getString("nombreCliente"),
                        resultado.getString("apellidoCliente"),
                        resultado.getString("direccionCliente"),
                        resultado.getString("telefonoCliente"),
                        resultado.getString("correoCliente")));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Almacena en nuestro ObservableList listaTipoProducto lo agregado anteriormente en lista
        return listaClientes = FXCollections.observableArrayList(lista);
    }

    public ObservableList<Empleados> getEmpleados() {
        // Crea Arraylist llamada lista para almacenar los datos del listar
        ArrayList<Empleados> lista = new ArrayList<Empleados>();

        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarEmpleados()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new Empleados(resultado.getInt("codigoEmpleado"),
                        resultado.getString("nombresEmpleado"),
                        resultado.getString("apellidosEmpleado"),
                        resultado.getDouble("sueldo"),
                        resultado.getString("direccion"),
                        resultado.getString("turno"),
                        resultado.getInt("codigoCargoEmpleado")));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Almacena en nuestro ObservableList listaTipoProducto lo agregado anteriormente en lista
        return listaEmpleados = FXCollections.observableArrayList(lista);
    }

    public void agregar() {
        switch (tipoDeOperaciones) {
            case NINGUNO:
                txtTotalF.setEditable(false);
                activarControles();
                btnAgregar.setText("Guardar");
                btnEliminar.setText("Cancelar");
                btnEditar.setDisable(true);
                btnReportes.setDisable(true);
                imgAgregar.setImage(new Image("/org/josuegarcia/images/Guardar.png"));
                imgEliminar.setImage(new Image("/org/josuegarcia/images/Cancelar.png"));
                tipoDeOperaciones = operaciones.EDITAR;
                break;
            case EDITAR:
                guardar();
                txtTotalF.setEditable(false);
                activarControles();
                LimpiarControles();
                btnAgregar.setText("Agregar");
                btnEliminar.setText("Eliminar");
                btnEditar.setDisable(false);
                btnReportes.setDisable(false);
                imgAgregar.setImage(new Image("/org/josuegarcia/images/Agregar.png"));
                imgEliminar.setImage(new Image("/org/josuegarcia/images/Editar.png"));
                tipoDeOperaciones = operaciones.NINGUNO;
                break;

        }

    }

    public void guardar() {
        // Creamos el objeto registro donde guardaremos nuestros datos 
        Facturas registro = new Facturas();
        registro.setNumeroFactura(Integer.parseInt((txtNumeroF.getText())));
        registro.setEstado(txtEstado.getText());
        registro.setFechaFactura((txtFechaF.getText()));
        registro.setCodigoCliente(((Clientes) cmbCodigoCliente.getSelectionModel().getSelectedItem()).getCodigoCliente());
        registro.setCodigoEmpleado(((Empleados) cmbCodigoEmpleado.getSelectionModel().getSelectedItem()).getCodigoEmpleado());

        try {
            // haremos conexion con la base de datos para poder registrar datos por medio del procedimiento almacenado
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call Sp_AgregarFactura(?, ?, ?, ?, ?)}");
            procedimiento.setInt(1, registro.getNumeroFactura());
            procedimiento.setString(2, registro.getEstado());
            procedimiento.setString(3, registro.getFechaFactura());
            procedimiento.setInt(4, registro.getCodigoCliente());
            procedimiento.setInt(5, registro.getCodigoEmpleado());
            procedimiento.execute();
            // agregamos a la listaProducto el objeto registro que contiene los datos que agregamos.
            listaFacturas.add(registro);

        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    // metodo para eliminar una tupla
    public void eliminar() {
        switch (tipoDeOperaciones) {
            case EDITAR:
                activarControles();
                LimpiarControles();
                btnAgregar.setText("Agregar");
                btnEliminar.setText("Eliminar");
                btnEditar.setDisable(false);
                btnReportes.setDisable(false);
                // colocamos imagenes 
                imgAgregar.setImage(new Image("/org/josuegarcia/images/Agregar.png"));
                imgEliminar.setImage(new Image("/org/josuegarcia/images/Editar.png"));
                tipoDeOperaciones = operaciones.NINGUNO;
                break;
            default:
                // si el elemento seleccionado de la tabla es diferente a nulo mandara un JoptionPane que nos pregunte si queremos eliminar 
                if (tbFacturas.getSelectionModel().getSelectedItem() != null) {
                    int respuesta = JOptionPane.showConfirmDialog(null, "Confirmar si elimina registro", "Eliminar Factura", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

                    // si la respuesta es no, no hara nada, pero si la respuesta es positiva removera la tupla que presiono el usuario
                    if (respuesta == JOptionPane.YES_NO_OPTION) {
                        try {
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarFactura(?)}");
                            procedimiento.setInt(1, ((Facturas) tbFacturas.getSelectionModel().getSelectedItem()).getNumeroFactura());

                            procedimiento.execute();
                            listaFacturas.remove(tbFacturas.getSelectionModel().getSelectedItem());
                            LimpiarControles();

                        } catch (Exception e) {
                            e.printStackTrace();

                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Debe de seleccionar un elemento");
                }
                break;
        }

    }

    // creamos el metodo de editar
    public void editar() {
        switch (tipoDeOperaciones) {
            case NINGUNO:
                if (tbFacturas.getSelectionModel().getSelectedItem() != null) {
                    // Cambiamos el texto de los botones
                    btnEditar.setText("Actualizar");
                    btnReportes.setText("Cancelar");
                    // disable solo acepta valores boolean
                    btnAgregar.setDisable(true);
                    btnEliminar.setDisable(true);
                    // colocamos las imagenes
                    imgEditar.setImage(new Image("/org/josuegarcia/images/Editar.png"));
                    imgReporte.setImage(new Image("/org/josuegarcia/images/Cancelar.png"));
                    activarControles();
                    txtNumeroF.setEditable(false);
                    tipoDeOperaciones = operaciones.EDITAR;

                    txtTotalF.setEditable(false);
                } else {
                    JOptionPane.showMessageDialog(null, "Debe de seleccionar un elemento");
                }

                break;
            case EDITAR:
                actualizar();

                // Colocamos el nombre predeterminado
                btnEditar.setText("Editar");
                btnReportes.setText("Reportes");
                // disable solo acepta valores boolean
                // activamos agregar y eliminar
                btnAgregar.setDisable(false);
                btnEliminar.setDisable(false);
                // colocamos las imagenes
                imgEditar.setImage(new Image("/org/josuegarcia/images/Agregar.png"));
                imgReporte.setImage(new Image("/org/josuegarcia/images/Reportes.png"));
                desactivarControles();
                LimpiarControles();
                tipoDeOperaciones = operaciones.NINGUNO;
                CargarDatos();
                break;

            default:

                break;
        }
    }

    public void actualizar() {
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EditarFactura(?, ?, ?, ?, ?)}");
            // registrara lo que presiono el usuario 
            // almacenamos la informacion de la tabla en registro
            Facturas registro = (Facturas) tbFacturas.getSelectionModel().getSelectedItem();
            // podremos editar unicamente los datos que no sean la Id, para no afectar nuestro orden en la db
            registro.setCodigoEmpleado(Integer.parseInt(txtNumeroF.getText()));
            registro.setEstado((txtEstado.getText()));
            registro.setFechaFactura((txtFechaF.getText()));
            registro.setCodigoCliente(((Clientes) cmbCodigoCliente.getSelectionModel().getSelectedItem()).getCodigoCliente());
            registro.setCodigoEmpleado(((Empleados) cmbCodigoEmpleado.getSelectionModel().getSelectedItem()).getCodigoEmpleado());

            // registramos los en procedimientos, los datos para actualizar la tabla 
            procedimiento.setInt(1, registro.getNumeroFactura());
            procedimiento.setString(2, registro.getEstado());
            procedimiento.setString(3, registro.getFechaFactura());
            procedimiento.setInt(4, registro.getCodigoCliente());
            procedimiento.setInt(5, registro.getCodigoEmpleado());
            procedimiento.execute();

        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    //Este metodo reporte nos sirve para poder cancelar alguna actualizacion o el metodo Agregar
    public void reporte() {
        switch (tipoDeOperaciones) {
            case NINGUNO:
                imprimirReporte();

            case EDITAR:
                activarControles();
                LimpiarControles();
                btnEditar.setText("Editar");
                btnReportes.setText("Reporte");
                btnAgregar.setDisable(false);
                btnEliminar.setDisable(false);
                imgEditar.setImage(new Image("/org/josuegarcia/images/Editar.png"));
                imgReporte.setImage(new Image("/org/josuegarcia/images/Reportes.png"));
                tipoDeOperaciones = operaciones.NINGUNO;

                break;

        }

    }

    public void imprimirReporte() {
        if (tbFacturas.getSelectionModel().getSelectedItem() != null) {
            Map parametros = new HashMap();
            int factID = ((Facturas) tbFacturas.getSelectionModel().getSelectedItem()).getNumeroFactura();
            parametros.put("factID", factID);
            GenerarReportes.mostrarReportes("reporteFacturas.jasper", "Reporte Facturas", parametros);
        } else {
            JOptionPane.showMessageDialog(null, "Debe de seleccionar un elemento");
        }
    }

    // Metodo para desactivar controller
    public void desactivarControles() {
        txtNumeroF.setEditable(false);
        txtEstado.setEditable(false);
        txtTotalF.setEditable(false);
        txtFechaF.setEditable(false);

    }

    // metodo para activarControllers
    public void activarControles() {
        txtNumeroF.setEditable(true);
        txtEstado.setEditable(true);
        txtFechaF.setEditable(true);

    }

    // metodo para limpiar
    public void LimpiarControles() {
        txtNumeroF.clear();
        txtEstado.clear();
        txtTotalF.clear();
        txtFechaF.clear();

        cmbCodigoCliente.getSelectionModel().getSelectedItem();
        cmbCodigoEmpleado.getSelectionModel().getSelectedItem();

    }

    // creamos los metodos
    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    // Creamos el metodo FXML handleButtonAction para que pueda regresar al menu principal
    @FXML
    public void handleButtonAction(ActionEvent event) {
        if (event.getSource() == btnRegresar) {
            escenarioPrincipal.menuPrincipalView();

        } else if (event.getSource() == btnIrDetalleFac) {
            escenarioPrincipal.MenuDetalleFacturaView();

        }
    }

}
