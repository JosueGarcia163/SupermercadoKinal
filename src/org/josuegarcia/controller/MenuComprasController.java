/* Documentacion Nombre completo: Josue David Garcia Mendez Codigo Tecnico:IN5BV
 * Fecha Creacion: 11/4/24 Fecha Modificaciones: 5/5/24
 */
package org.josuegarcia.controller;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import org.josuegarcia.bean.Compras;
import org.josuegarcia.db.Conexion;
import org.josuegarcia.system.Principal;

public class MenuComprasController implements Initializable {

    private ObservableList<Compras> listaCompras;

    private enum operaciones {
        AGREGAR, ELIMINAR, EDITAR, CANCELAR, NINGUNO
    }
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;

    private Principal escenarioPrincipal;
    @FXML
    private Button btnRegresar;
    @FXML
    private Button btnIrCompras;
    @FXML
    private TextField txtNumeroDocumento;
    @FXML
    private TextField txtFechaDocumento;
    @FXML
    private TextField txtDescripcion;
    @FXML
    private TextField txtTotalDocumento;
    @FXML
    private TableView tbCompras;
    @FXML
    private TableColumn colNumeroDocumento;
    @FXML
    private TableColumn colDocumento;
    @FXML
    private TableColumn colDescripcion;
    @FXML
    private TableColumn colTotalDocumento;
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
        tbCompras.setItems(getCompras());
        colNumeroDocumento.setCellValueFactory(new PropertyValueFactory<Compras, Integer>("numeroDocumento"));
        colDocumento.setCellValueFactory(new PropertyValueFactory<Compras, String>("fechaDocumento"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<Compras, String>("descripcion"));
        colTotalDocumento.setCellValueFactory(new PropertyValueFactory<Compras, Double>("totalDocumento"));

    }

    // Es para identificar la tupla seleccionada por el usuario en la ventana de JavaFx
    public void seleccionarElemento() {
        txtNumeroDocumento.setText(String.valueOf(((Compras) tbCompras.getSelectionModel().getSelectedItem()).getNumeroDocumento()));
        txtFechaDocumento.setText(((Compras) tbCompras.getSelectionModel().getSelectedItem()).getFechaDocumento());
        txtDescripcion.setText(((Compras) tbCompras.getSelectionModel().getSelectedItem()).getDescripcion());
        txtTotalDocumento.setText(String.valueOf(((Compras) tbCompras.getSelectionModel().getSelectedItem()).getTotalDocumento()));

    }

    public ObservableList<Compras> getCompras() {
        // Crea Arraylist llamada lista para almacenar los datos del listar
        ArrayList<Compras> lista = new ArrayList();

        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarCompras()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new Compras(resultado.getInt("numeroDocumento"),
                        resultado.getString("fechaDocumento"),
                        resultado.getString("descripcion"),
                        resultado.getDouble("totalDocumento")));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Almacena en nuestro ObservableList listaTipoProducto lo agregado anteriormente en lista
        return listaCompras = FXCollections.observableArrayList(lista);
    }

    public void agregar() {
        switch (tipoDeOperaciones) {
            case NINGUNO:
                activarControles();
                txtTotalDocumento.setEditable(false);
                btnAgregar.setText("Guardar");
                btnEliminar.setText("Cancelar");
                btnEditar.setDisable(true);
                btnReportes.setDisable(true);
                imgAgregar.setImage(new Image("/org/josuegarcia/images/Guardar.png"));
                imgEliminar.setImage(new Image("/org/josuegarcia/images/Cancelar.png"));
                tipoDeOperaciones = operaciones.EDITAR;
                break;
            case EDITAR:
                txtTotalDocumento.setEditable(false);
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
        Compras registro = new Compras();
        registro.setNumeroDocumento(Integer.parseInt(txtNumeroDocumento.getText()));
        registro.setFechaDocumento(txtFechaDocumento.getText());
        registro.setDescripcion(txtDescripcion.getText());

        try {
            // haremos conexion con la base de datos para poder registrar datos por medio del procedimiento almacenado
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call Sp_AgregarCompras(?, ?, ?)}");
            procedimiento.setInt(1, registro.getNumeroDocumento());
            procedimiento.setString(2, registro.getFechaDocumento());
            procedimiento.setString(3, registro.getDescripcion());

            procedimiento.execute();
            // agregamos a la listaTipoProducto el objeto registro que contiene los datos que agregamos.
            listaCompras.add(registro);

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
                if (tbCompras.getSelectionModel().getSelectedItem() != null) {
                    int respuesta = JOptionPane.showConfirmDialog(null, "Confirmar si elimina registro", "Eliminar Compras", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

                    // si la respuesta es no, no hara nada, pero si la respuesta es positiva removera la tupla que presiono el usuario
                    if (respuesta == JOptionPane.YES_NO_OPTION) {
                        try {
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarCompras(?)}");
                            procedimiento.setInt(1, ((Compras) tbCompras.getSelectionModel().getSelectedItem()).getNumeroDocumento());
                            procedimiento.execute();
                            listaCompras.remove(tbCompras.getSelectionModel().getSelectedItem());
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
                if (tbCompras.getSelectionModel().getSelectedItem() != null) {
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
                    txtNumeroDocumento.setEditable(false);
                    tipoDeOperaciones = operaciones.EDITAR;
                    
                    // Desactivar controles
                    txtTotalDocumento.setEditable(false);

                } else {
                    JOptionPane.showMessageDialog(null, "Debe de seleccionar un elemento");
                }

                break;
            case EDITAR:
                txtTotalDocumento.setEditable(false);
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
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EditarCompras(?, ?, ?)}");
            // registrara lo que presiono el usuario 
            Compras registro = (Compras) tbCompras.getSelectionModel().getSelectedItem();
            // podremos editar unicamente los datos que no sean la Id, para no afectar nuestro orden en la db
            registro.setFechaDocumento(txtFechaDocumento.getText());
            registro.setDescripcion(txtDescripcion.getText());

            procedimiento.setInt(1, registro.getNumeroDocumento());
            procedimiento.setString(2, registro.getFechaDocumento());
            procedimiento.setString(3, registro.getDescripcion());

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
        txtNumeroDocumento.setEditable(false);
        txtFechaDocumento.setEditable(false);
        txtDescripcion.setEditable(false);
        txtTotalDocumento.setEditable(false);

    }

    // metodo para activarControllers
    public void activarControles() {
        txtNumeroDocumento.setEditable(true);
        txtFechaDocumento.setEditable(true);
        txtDescripcion.setEditable(true);
        txtTotalDocumento.setEditable(true);

    }

    // metodo para limpiar
    public void LimpiarControles() {
        txtNumeroDocumento.clear();
        txtFechaDocumento.clear();
        txtDescripcion.clear();
        txtTotalDocumento.clear();

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

        } else if (event.getSource() == btnIrCompras) {
            escenarioPrincipal.MenuDetalleCompraView();

        }

    }

}
