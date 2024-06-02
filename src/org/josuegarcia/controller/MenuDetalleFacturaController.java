/* Documentacion Nombre completo: Josue David Garcia Mendez Codigo Tecnico:IN5BV
 * Fecha Creacion: 11/4/24 Fecha Modificaciones: 22/5/24
 */
package org.josuegarcia.controller;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
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
import org.josuegarcia.bean.DetalleFactura;
import org.josuegarcia.bean.Facturas;
import org.josuegarcia.bean.Productos;
import org.josuegarcia.db.Conexion;
import org.josuegarcia.system.Principal;

public class MenuDetalleFacturaController implements Initializable {

    private enum operaciones {
        AGREGAR, ELIMINAR, EDITAR, CANCELAR, NINGUNO
    }
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;
    private ObservableList<Facturas> listaFacturas;
    private ObservableList<Productos> listaProductos;
    private ObservableList<DetalleFactura> listaDetalleFacturas;

    private Principal escenarioPrincipal;

    @FXML
    private Button btnRegresar;
    @FXML
    private Button btnRegresarMenu;
    @FXML
    private TextField txtCodDetalleFac;
    @FXML
    private TextField txtPrecioU;
    @FXML
    private TextField txtCantidad;
    @FXML
    private ComboBox cmbNumeroFactura;
    @FXML
    private ComboBox cmbCodigoProducto;
    @FXML
    private TableView tbDetalleFactura;
    @FXML
    private TableColumn colDetalleFactura;
    @FXML
    private TableColumn colPrecioUnitario;
    @FXML
    private TableColumn colCantidad;
    @FXML
    private TableColumn colNumeroFactura;
    @FXML
    private TableColumn colCodigoProducto;
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
        cmbNumeroFactura.setItems(getFacturas());
        cmbCodigoProducto.setItems(getProducto());
    }

    // Este metodo nos sirve para listar los datos agregados
    public void CargarDatos() {
        tbDetalleFactura.setItems(getDetalleFacturas());
        colDetalleFactura.setCellValueFactory(new PropertyValueFactory<DetalleFactura, Integer>("codigoDetalleFactura"));
        colPrecioUnitario.setCellValueFactory(new PropertyValueFactory<DetalleFactura, Double>("precioUnitario"));
        colCantidad.setCellValueFactory(new PropertyValueFactory<DetalleFactura, Integer>("cantidad"));
        colNumeroFactura.setCellValueFactory(new PropertyValueFactory<DetalleFactura, Integer>("numeroFactura"));
        colCodigoProducto.setCellValueFactory(new PropertyValueFactory<DetalleFactura, String>("codigoProducto"));

    }

    // Es para identificar la tupla seleccionada por el usuario en la ventana de JavaFx
    public void seleccionarElemento() {
        txtCodDetalleFac.setText(String.valueOf(((DetalleFactura) tbDetalleFactura.getSelectionModel().getSelectedItem()).getCodigoDetalleFactura()));
        txtPrecioU.setText(String.valueOf(((DetalleFactura) tbDetalleFactura.getSelectionModel().getSelectedItem()).getPrecioUnitario()));
        txtCantidad.setText(String.valueOf(((DetalleFactura) tbDetalleFactura.getSelectionModel().getSelectedItem()).getCantidad()));

        cmbNumeroFactura.getSelectionModel().select(buscarF(((DetalleFactura) tbDetalleFactura.getSelectionModel().getSelectedItem()).getNumeroFactura()));
        cmbCodigoProducto.getSelectionModel().select(buscarP(((DetalleFactura) tbDetalleFactura.getSelectionModel().getSelectedItem()).getCodigoProducto()));

    }

    // Es para buscar el elemento de la llave foranea para mostrarla en la comboBox
    public Facturas buscarF(int numeroFactura) {
        Facturas resultado = null;
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarFactura(?)}");
            procedimiento.setInt(1, numeroFactura);
            ResultSet registro = procedimiento.executeQuery();

            while (registro.next()) {
                resultado = new Facturas(
                        registro.getInt("numeroFactura"),
                        registro.getString("estado"),
                        registro.getDouble("totalFactura"),
                        registro.getString("fechaFactura"),
                        registro.getInt("codigoCliente"),
                        registro.getInt("codigoEmpleado")
                );

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultado;
    }

    // Es para buscar el elemento de la llave foranea para mostrarla en la comboBox
    public Productos buscarP(String codigoProducto) {
        Productos resultado = null;
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarProductos(?)}");
            procedimiento.setString(1, codigoProducto);
            ResultSet registro = procedimiento.executeQuery();

            while (registro.next()) {
                resultado = new Productos(
                        registro.getString("codigoProducto"),
                        registro.getString("descripcionProducto"),
                        registro.getDouble("precioUnitario"),
                        registro.getDouble("precioDocena"),
                        registro.getDouble("precioMayor"),
                        registro.getInt("existencia"),
                        registro.getInt("codigoTipoProducto"),
                        registro.getInt("codigoProveedor"));

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultado;
    }

    public ObservableList<DetalleFactura> getDetalleFacturas() {
        // Crea Arraylist llamada lista para almacenar los datos del listar
        ArrayList<DetalleFactura> lista = new ArrayList<DetalleFactura>();

        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarDetalleFactura()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new DetalleFactura(resultado.getInt("codigoDetalleFactura"),
                        resultado.getDouble("precioUnitario"),
                        resultado.getInt("cantidad"),
                        resultado.getInt("numeroFactura"),
                        resultado.getString("codigoProducto")
                ));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Almacena en nuestro ObservableList listaTipoProducto lo agregado anteriormente en lista
        return listaDetalleFacturas = FXCollections.observableArrayList(lista);
    }

    public ObservableList<Productos> getProducto() {
        // Crea Arraylist llamada lista para almacenar los datos del listar
        ArrayList<Productos> lista = new ArrayList<Productos>();

        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarProductos()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new Productos(resultado.getString("codigoProducto"),
                        resultado.getString("descripcionProducto"),
                        resultado.getDouble("precioUnitario"),
                        resultado.getDouble("precioDocena"),
                        resultado.getDouble("precioMayor"),
                        resultado.getInt("existencia"),
                        resultado.getInt("codigoTipoProducto"),
                        resultado.getInt("codigoProveedor")));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Almacena en nuestro ObservableList listaTipoProducto lo agregado anteriormente en lista
        return listaProductos = FXCollections.observableArrayList(lista);
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

    public void agregar() {
        switch (tipoDeOperaciones) {
            case NINGUNO:
                // desactivar el control que se rellenara solo
                txtPrecioU.setEditable(false);
                // activar controles 
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
                // desactivar el control que se rellenara solo
                txtPrecioU.setEditable(false);
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
        DetalleFactura registro = new DetalleFactura();
        registro.setCodigoDetalleFactura(Integer.parseInt(txtCodDetalleFac.getText()));

        registro.setCantidad(Integer.parseInt(txtCantidad.getText()));
        registro.setNumeroFactura(((Facturas) cmbNumeroFactura.getSelectionModel().getSelectedItem()).getNumeroFactura());
        registro.setCodigoProducto(((Productos) cmbCodigoProducto.getSelectionModel().getSelectedItem()).getCodigoProducto());

        try {
            // haremos conexion con la base de datos para poder registrar datos por medio del procedimiento almacenado
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call Sp_AgregarDetalleFactura(?, ?, ?, ?)}");
            procedimiento.setInt(1, registro.getCodigoDetalleFactura());

            procedimiento.setInt(2, registro.getCantidad());
            procedimiento.setInt(3, registro.getNumeroFactura());
            procedimiento.setString(4, registro.getCodigoProducto());
            procedimiento.execute();
            // agregamos a la listaProducto el objeto registro que contiene los datos que agregamos.
            listaDetalleFacturas.add(registro);

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
                if (tbDetalleFactura.getSelectionModel().getSelectedItem() != null) {
                    int respuesta = JOptionPane.showConfirmDialog(null, "Confirmar si elimina registro", "Eliminar DetalleFacturas", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

                    // si la respuesta es no, no hara nada, pero si la respuesta es positiva removera la tupla que presiono el usuario
                    if (respuesta == JOptionPane.YES_NO_OPTION) {
                        try {
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarDetalleFactura(?)}");
                            procedimiento.setInt(1, ((DetalleFactura) tbDetalleFactura.getSelectionModel().getSelectedItem()).getCodigoDetalleFactura());

                            procedimiento.execute();
                            listaDetalleFacturas.remove(tbDetalleFactura.getSelectionModel().getSelectedItem());
                            // listaProveedores.remove(tbProducto.getSelectionModel().getSelectedItem());
                            // listaTipoProducto.remove(tbProducto.getSelectionModel().getSelectedItem());
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
                if (tbDetalleFactura.getSelectionModel().getSelectedItem() != null) {
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
                    txtCodDetalleFac.setEditable(false);
                    tipoDeOperaciones = operaciones.EDITAR;

                    // desactivar el control que se rellenara solo
                    txtPrecioU.setEditable(false);

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

    // hacemos actualizar 
    public void actualizar() {
        try {
            // llamamos instancia
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EditarDetalleFactura(?, ?, ?, ?)}");
            // registrara lo que presiono el usuario 
            // almacenamos la informacion de la tabla en registro
            DetalleFactura registro = (DetalleFactura) tbDetalleFactura.getSelectionModel().getSelectedItem();
            // podremos editar unicamente los datos que no sean la Id, para no afectar nuestro orden en la db
            registro.setCodigoDetalleFactura(Integer.parseInt(txtCodDetalleFac.getText()));
            registro.setCantidad(Integer.parseInt(txtCantidad.getText()));
            registro.setNumeroFactura(((Facturas) cmbNumeroFactura.getSelectionModel().getSelectedItem()).getNumeroFactura());
            registro.setCodigoProducto(((Productos) cmbCodigoProducto.getSelectionModel().getSelectedItem()).getCodigoProducto());

            // registramos los en procedimientos, los datos para actualizar la tabla 
            procedimiento.setInt(1, registro.getCodigoDetalleFactura());
            procedimiento.setInt(2, registro.getCantidad());
            procedimiento.setInt(3, registro.getNumeroFactura());
            procedimiento.setString(4, registro.getCodigoProducto());

            procedimiento.execute();

        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    //Este metodo reporte nos sirve para poder cancelar alguna actualizacion o el metodo Agregar
    public void reporte() {
        switch (tipoDeOperaciones) {
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

    // Metodo para desactivar controller
    public void desactivarControles() {
        txtCodDetalleFac.setEditable(false);
        txtPrecioU.setEditable(false);
        txtCantidad.setEditable(false);

    }

    // metodo para activarControllers
    public void activarControles() {
        txtCodDetalleFac.setEditable(true);

        txtCantidad.setEditable(true);

    }

    // metodo para limpiar
    public void LimpiarControles() {
        txtCodDetalleFac.clear();
        txtPrecioU.clear();
        txtCantidad.clear();
        cmbNumeroFactura.getSelectionModel().getSelectedItem();
        cmbCodigoProducto.getSelectionModel().getSelectedItem();

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

        } else if (event.getSource() == btnRegresarMenu) {
            escenarioPrincipal.MenuFacturasView();

        }

    }

}
