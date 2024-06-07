/* Documentacion Nombre completo: Josue David Garcia Mendez Codigo Tecnico:IN5BV
 * Fecha Creacion: 11/4/24 Fecha Modificaciones: 9/5/24
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.swing.JOptionPane;
import org.josuegarcia.bean.Clientes;
import org.josuegarcia.db.Conexion;
import org.josuegarcia.reports.GenerarReportes;
import org.josuegarcia.system.Principal;

public class MenuClientesController implements Initializable {
    // Declaramos las variables para el escenario y para el button regresar

    private ObservableList<Clientes> listaClientes;

    private enum operaciones {
        AGREGAR, ELIMINAR, EDITAR, CANCELAR, NINGUNO
    }
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;

    private Principal escenarioPrincipal;
    @FXML
    private Button btnRegresar;
    @FXML
    private TextField txtDireccionC;
    @FXML
    private TextField txtCorreoC;
    @FXML
    private TextField txtCodigoC;
    @FXML
    private TextField txtNit;
    @FXML
    private TextField txtNombreC;
    @FXML
    private TextField txtApellidoC;
    @FXML
    private TextField txtTelefonoC;
    @FXML
    private TableView tbClientes;
    @FXML
    private TableColumn colCodigoC;
    @FXML
    private TableColumn colNombreC;
    @FXML
    private TableColumn colApellidoC;
    @FXML
    private TableColumn colDireccionC;
    @FXML
    private TableColumn colNit;
    @FXML
    private TableColumn colTelefonoC;
    @FXML
    private TableColumn colCorreoC;
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
    public void initialize(URL url, ResourceBundle rb) {
        CargarDatos();
    }

    // Este metodo nos sirve para listar los datos agregados
    public void CargarDatos() {
        tbClientes.setItems(getClientes());
        colCodigoC.setCellValueFactory(new PropertyValueFactory<Clientes, Integer>("codigoCliente"));
        colNit.setCellValueFactory(new PropertyValueFactory<Clientes, String>("NITcliente"));
        colNombreC.setCellValueFactory(new PropertyValueFactory<Clientes, String>("nombreCliente"));
        colApellidoC.setCellValueFactory(new PropertyValueFactory<Clientes, String>("apellidoCliente"));
        colDireccionC.setCellValueFactory(new PropertyValueFactory<Clientes, String>("direccionCliente"));
        colTelefonoC.setCellValueFactory(new PropertyValueFactory<Clientes, String>("telefonoCliente"));
        colCorreoC.setCellValueFactory(new PropertyValueFactory<Clientes, String>("correoCliente"));

    }

     // Es para identificar la tupla seleccionada por el usuario en la ventana de JavaFx
    public void seleccionarElemento() {
        txtCodigoC.setText(String.valueOf(((Clientes) tbClientes.getSelectionModel().getSelectedItem()).getCodigoCliente()));
        txtNit.setText(((Clientes) tbClientes.getSelectionModel().getSelectedItem()).getNITcliente());
        txtNombreC.setText(((Clientes) tbClientes.getSelectionModel().getSelectedItem()).getNombreCliente());
        txtApellidoC.setText(((Clientes) tbClientes.getSelectionModel().getSelectedItem()).getApellidoCliente());
        txtDireccionC.setText(((Clientes) tbClientes.getSelectionModel().getSelectedItem()).getDireccionCliente());
        txtTelefonoC.setText(((Clientes) tbClientes.getSelectionModel().getSelectedItem()).getTelefonoCliente());
        txtCorreoC.setText(((Clientes) tbClientes.getSelectionModel().getSelectedItem()).getCorreoCliente());

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

    public void agregar() {
        switch (tipoDeOperaciones) {
            case NINGUNO:
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
        Clientes registro = new Clientes();
        registro.setCodigoCliente(Integer.parseInt(txtCodigoC.getText()));
        registro.setNITcliente(txtNit.getText());
        registro.setNombreCliente(txtNombreC.getText());
        registro.setApellidoCliente(txtApellidoC.getText());
        registro.setDireccionCliente(txtDireccionC.getText());
        registro.setTelefonoCliente(txtTelefonoC.getText());
        registro.setCorreoCliente(txtCorreoC.getText());
        try {
            // haremos conexion con la base de datos para poder registrar datos por medio del procedimiento almacenado
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call Sp_AgregarClientes(?,?,?,?,?,?,?)}");
            procedimiento.setInt(1, registro.getCodigoCliente());
            procedimiento.setString(2, registro.getNITcliente());
            procedimiento.setString(3, registro.getNombreCliente());
            procedimiento.setString(4, registro.getApellidoCliente());
            procedimiento.setString(5, registro.getDireccionCliente());
            procedimiento.setString(6, registro.getTelefonoCliente());
            procedimiento.setString(7, registro.getCorreoCliente());
            procedimiento.execute();
             // agregamos a la listaTipoProducto el objeto registro que contiene los datos que agregamos.
            listaClientes.add(registro);

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
                if (tbClientes.getSelectionModel().getSelectedItem() != null) {
                    int respuesta = JOptionPane.showConfirmDialog(null, "Confirmar si elimina registro", "Eliminar Clientes", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

                    // si la respuesta es no, no hara nada, pero si la respuesta es positiva removera la tupla que presiono el usuario
                    if (respuesta == JOptionPane.YES_NO_OPTION) {
                        try {
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarClientes(?)}");
                            procedimiento.setInt(1, ((Clientes) tbClientes.getSelectionModel().getSelectedItem()).getCodigoCliente());
                            procedimiento.execute();
                            listaClientes.remove(tbClientes.getSelectionModel().getSelectedItem());
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
                if (tbClientes.getSelectionModel().getSelectedItem() != null) {
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
                    txtCodigoC.setEditable(false);
                    tipoDeOperaciones = operaciones.EDITAR;

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
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EditarClientes(?, ?, ?, ?, ?, ?, ?)}");
            // registrara lo que presiono el usuario 
            Clientes registro = (Clientes) tbClientes.getSelectionModel().getSelectedItem();
            // podremos editar unicamente los datos que no sean la Id, para no afectar nuestro orden en la db
            registro.setNITcliente(txtNit.getText());
            registro.setNombreCliente(txtNombreC.getText());
            registro.setApellidoCliente(txtApellidoC.getText());
            registro.setDireccionCliente(txtDireccionC.getText());
            registro.setTelefonoCliente(txtTelefonoC.getText());
            registro.setCorreoCliente(txtCorreoC.getText());
            procedimiento.setInt(1, registro.getCodigoCliente());
            procedimiento.setString(2, registro.getNITcliente());
            procedimiento.setString(3, registro.getNombreCliente());
            procedimiento.setString(4, registro.getApellidoCliente());
            procedimiento.setString(5, registro.getDireccionCliente());
            procedimiento.setString(6, registro.getTelefonoCliente());
            procedimiento.setString(7, registro.getCorreoCliente());
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
                
                break;
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
    public void imprimirReporte(){
    Map parametros = new HashMap();
    parametros.put("codigoCliente" , null);
    GenerarReportes.mostrarReportes("reporteClientes.jasper", "Reporte Cliente", parametros);
    
    }
    
    

    // Metodo para desactivar controller
    public void desactivarControles() {
        txtCodigoC.setEditable(false);
        txtNit.setEditable(false);
        txtNombreC.setEditable(false);
        txtApellidoC.setEditable(false);
        txtDireccionC.setEditable(false);
        txtTelefonoC.setEditable(false);
        txtCorreoC.setEditable(false);

    }

    // metodo para activarControllers
    public void activarControles() {
        txtCodigoC.setEditable(true);
        txtNit.setEditable(true);
        txtNombreC.setEditable(true);
        txtApellidoC.setEditable(true);
        txtDireccionC.setEditable(true);
        txtTelefonoC.setEditable(true);
        txtCorreoC.setEditable(true);

    }

    // metodo para limpiar
    public void LimpiarControles() {
        txtCodigoC.clear();
        txtNit.clear();
        txtNombreC.clear();
        txtApellidoC.clear();
        txtDireccionC.clear();
        txtTelefonoC.clear();
        txtCorreoC.clear();

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

        }

    }

}
