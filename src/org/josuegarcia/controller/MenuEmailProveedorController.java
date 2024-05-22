/* Documentacion Nombre completo: Josue David Garcia Mendez Codigo Tecnico:IN5BV
 * Fecha Creacion: 11/4/24 Fecha Modificaciones: 19/5/24
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
import org.josuegarcia.bean.EmailProveedor;
import org.josuegarcia.bean.Proveedores;
import org.josuegarcia.db.Conexion;
import org.josuegarcia.system.Principal;

public class MenuEmailProveedorController implements Initializable {

    private enum operaciones {
        AGREGAR, ELIMINAR, EDITAR, CANCELAR, NINGUNO
    }
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;
    private ObservableList<EmailProveedor> listaEmailProveedor;
    private ObservableList<Proveedores> listaProveedores;

    private Principal escenarioPrincipal;

    @FXML
    private Button btnRegresar;
    @FXML
    private Button btnRegresarP;
    @FXML
    private TextField txtCodEmailPro;
    @FXML
    private TextField txtEmailPro;
    @FXML
    private TextField txtDescripcion;
    @FXML
    private ComboBox cmbCodigoPro;
    @FXML
    private TableView tbEmailProveedor;
    @FXML
    private TableColumn colCodigoEmail;
    @FXML
    private TableColumn colEmail;
    @FXML
    private TableColumn colDescipcion;
    @FXML
    private TableColumn colCodigoPro;
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
        cmbCodigoPro.setItems(getProveedores());
    }

    // Este metodo nos sirve para listar los datos agregados
    public void CargarDatos() {
        tbEmailProveedor.setItems(getEmailProveedor());
        colCodigoEmail.setCellValueFactory(new PropertyValueFactory<EmailProveedor, Integer>("codigoEmailProveedor"));
        colEmail.setCellValueFactory(new PropertyValueFactory<EmailProveedor, String>("emailProveedor"));
        colDescipcion.setCellValueFactory(new PropertyValueFactory<EmailProveedor, String>("descripcion"));
        colCodigoPro.setCellValueFactory(new PropertyValueFactory<EmailProveedor, Integer>("codigoProveedor"));

    }

    // Es para identificar la tupla seleccionada por el usuario en la ventana de JavaFx
    public void seleccionarElemento() {
        txtCodEmailPro.setText(String.valueOf(((EmailProveedor) tbEmailProveedor.getSelectionModel().getSelectedItem()).getCodigoEmailProveedor()));
        txtEmailPro.setText(((EmailProveedor) tbEmailProveedor.getSelectionModel().getSelectedItem()).getEmailProveedor());
        txtDescripcion.setText(((EmailProveedor) tbEmailProveedor.getSelectionModel().getSelectedItem()).getDescripcion());
        cmbCodigoPro.getSelectionModel().select(buscarProveedores(((EmailProveedor) tbEmailProveedor.getSelectionModel().getSelectedItem()).getCodigoProveedor()));

    }

    // Busca en proveedores para mostrar la llave foranea en la comboBox
    public Proveedores buscarProveedores(int codigoProveedores) {
        Proveedores resultado = null;
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarProveedores(?)}");
            procedimiento.setInt(1, codigoProveedores);
            ResultSet registro = procedimiento.executeQuery();

            while (registro.next()) {
                resultado = new Proveedores(registro.getInt("codigoProveedor"),
                        registro.getString("NITproveedor"),
                        registro.getString("nombreProveedor"),
                        registro.getString("apellidosProveedor"),
                        registro.getString("direccionProveedor"),
                        registro.getString("razonSocial"),
                        registro.getString("contactoPrincipal"),
                        registro.getString("paginaWeb")
                );

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultado;
    }

    // Metodo propio para poder listar los elementos de EmailProveedor
    public ObservableList<EmailProveedor> getEmailProveedor() {
        // Crea Arraylist llamada lista para almacenar los datos del listar
        ArrayList<EmailProveedor> lista = new ArrayList<EmailProveedor>();

        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarEmailProveedor()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new EmailProveedor(resultado.getInt("codigoEmailProveedor"),
                        resultado.getString("emailProveedor"),
                        resultado.getString("descripcion"),
                        resultado.getInt("codigoProveedor")));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Almacena en nuestro ObservableList listaTipoProducto lo agregado anteriormente en lista
        return listaEmailProveedor = FXCollections.observableArrayList(lista);
    }

    public ObservableList<Proveedores> getProveedores() {
        // Crea Arraylist llamada lista para almacenar los datos del listar
        ArrayList<Proveedores> lista = new ArrayList();

        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarProveedores()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                // agrega a nuestro arrayList los datos para listar
                lista.add(new Proveedores(resultado.getInt("codigoProveedor"),
                        resultado.getString("NITproveedor"),
                        resultado.getString("nombreProveedor"),
                        resultado.getString("apellidosProveedor"),
                        resultado.getString("direccionProveedor"),
                        resultado.getString("razonSocial"),
                        resultado.getString("contactoPrincipal"),
                        resultado.getString("paginaWeb")));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Almacena en nuestro ObservableList listaTipoProducto lo agregado anteriormente en lista
        return listaProveedores = FXCollections.observableArrayList(lista);
    }

    //Creamos el metodo Agregar
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
        EmailProveedor registro = new EmailProveedor();
        registro.setCodigoEmailProveedor(Integer.parseInt(txtCodEmailPro.getText()));
        registro.setEmailProveedor(txtEmailPro.getText());
        registro.setDescripcion((txtDescripcion.getText()));
        registro.setCodigoProveedor(((Proveedores) cmbCodigoPro.getSelectionModel().getSelectedItem()).getCodigoProveedor());

        try {
            // haremos conexion con la base de datos para poder registrar datos por medio del procedimiento almacenado
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call Sp_AgregarEmailProveedor(?, ?, ?, ?)}");
            procedimiento.setInt(1, registro.getCodigoEmailProveedor());
            procedimiento.setString(2, registro.getEmailProveedor());
            procedimiento.setString(3, registro.getDescripcion());
            procedimiento.setInt(4, registro.getCodigoProveedor());
            procedimiento.execute();
            // agregamos a la listaProducto el objeto registro que contiene los datos que agregamos.
            listaEmailProveedor.add(registro);

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
                if (tbEmailProveedor.getSelectionModel().getSelectedItem() != null) {
                    int respuesta = JOptionPane.showConfirmDialog(null, "Confirmar si elimina registro", "Eliminar de EmailProveedor", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

                    // si la respuesta es no, no hara nada, pero si la respuesta es positiva removera la tupla que presiono el usuario
                    if (respuesta == JOptionPane.YES_NO_OPTION) {
                        try {
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarEmailProveedor(?)}");
                            procedimiento.setInt(1, ((EmailProveedor) tbEmailProveedor.getSelectionModel().getSelectedItem()).getCodigoEmailProveedor());

                            procedimiento.execute();
                            listaEmailProveedor.remove(tbEmailProveedor.getSelectionModel().getSelectedItem());

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
                if (tbEmailProveedor.getSelectionModel().getSelectedItem() != null) {
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
                    txtCodEmailPro.setEditable(false);
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
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EditarEmailProveedor(?, ?, ?, ?)}");
            // registrara lo que presiono el usuario 
            EmailProveedor registro = (EmailProveedor) tbEmailProveedor.getSelectionModel().getSelectedItem();
            // podremos editar unicamente los datos que no sean la Id, para no afectar nuestro orden en la db
            registro.setCodigoEmailProveedor(Integer.parseInt(txtCodEmailPro.getText()));
            registro.setEmailProveedor((txtEmailPro.getText()));
            registro.setDescripcion((txtDescripcion.getText()));
            registro.setCodigoProveedor(((Proveedores) cmbCodigoPro.getSelectionModel().getSelectedItem()).getCodigoProveedor());

            procedimiento.setInt(1, registro.getCodigoEmailProveedor());
            procedimiento.setString(2, registro.getEmailProveedor());
            procedimiento.setString(3, registro.getDescripcion());
            procedimiento.setInt(4, registro.getCodigoProveedor());
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
        txtCodEmailPro.setEditable(false);
        txtEmailPro.setEditable(false);
        txtDescripcion.setEditable(false);

    }

    // metodo para activarControllers
    public void activarControles() {
        txtCodEmailPro.setEditable(true);
        txtEmailPro.setEditable(true);
        txtDescripcion.setEditable(true);

    }

    // metodo para limpiar
    public void LimpiarControles() {
        txtCodEmailPro.clear();
        txtEmailPro.clear();
        txtDescripcion.clear();
        cmbCodigoPro.getSelectionModel().getSelectedItem();

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

        } else if (event.getSource() == btnRegresarP) {
            escenarioPrincipal.menuProveedoresView();

        }

    }

}
