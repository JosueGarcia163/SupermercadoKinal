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
import org.josuegarcia.bean.Productos;
import org.josuegarcia.bean.Proveedores;
import org.josuegarcia.bean.TipoProducto;
import org.josuegarcia.db.Conexion;
import org.josuegarcia.system.Principal;

public class MenuProductosController implements Initializable {

    

    private enum operaciones {
        AGREGAR, ELIMINAR, EDITAR, CANCELAR, NINGUNO
    }
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;
    private ObservableList<Productos> listaProductos;
    private ObservableList<Proveedores> listaProveedores;
    private ObservableList<TipoProducto> listaTipoProducto;
    
    private Principal escenarioPrincipal;

    @FXML
    private Button btnRegresar;
    @FXML
    private TextField txtCodigoP;
    @FXML
    private TextField txtDescripcionP;
    @FXML
    private TextField txtPrecioUnitarioP;
    @FXML
    private TextField txtPrecioD;
    @FXML
    private TextField txtPrecioM;
    @FXML
    private TextField txtExistencia;
    @FXML
    private ComboBox cmbCodigoTipoProducto;
    @FXML
    private ComboBox cmbCodigoProveedor;
    @FXML
    private TableView tbProducto;
    @FXML
    private TableColumn colCodigoP;
    @FXML
    private TableColumn colDescripcionP;
    @FXML
    private TableColumn colPrecioU;
    @FXML
    private TableColumn colPrecioD;
    @FXML
    private TableColumn colPrecioM;
    @FXML
    private TableColumn colExistencia;
    @FXML
    private TableColumn colCodigoTipo;
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
        cmbCodigoTipoProducto.setItems(getTipoProducto());
        cmbCodigoProveedor.setItems(getProveedores());
    }

    // Este metodo nos sirve para listar los datos agregados
    public void CargarDatos() {
        tbProducto.setItems(getProducto());
        colCodigoP.setCellValueFactory(new PropertyValueFactory<Productos, String>("codigoProducto"));
        colDescripcionP.setCellValueFactory(new PropertyValueFactory<Productos, String>("descripcionProducto"));
        colPrecioU.setCellValueFactory(new PropertyValueFactory<Productos, Double>("precioUnitario"));
        colPrecioD.setCellValueFactory(new PropertyValueFactory<Productos, Double>("precioDocena"));
        colPrecioM.setCellValueFactory(new PropertyValueFactory<Productos, Double>("precioMayor"));
        colExistencia.setCellValueFactory(new PropertyValueFactory<Productos, Integer>("existencia"));
        colCodigoTipo.setCellValueFactory(new PropertyValueFactory<Productos, Integer>("codigoTipoProducto"));
        colCodigoPro.setCellValueFactory(new PropertyValueFactory<Productos, Integer>("codigoProveedor"));

    }

    // Es para identificar la tupla seleccionada por el usuario en la ventana de JavaFx
    public void seleccionarElemento() {
        txtCodigoP.setText(String.valueOf(((Productos) tbProducto.getSelectionModel().getSelectedItem()).getCodigoProducto()));
        txtDescripcionP.setText(((Productos) tbProducto.getSelectionModel().getSelectedItem()).getDescripcionProducto());
        txtPrecioUnitarioP.setText(String.valueOf(((Productos) tbProducto.getSelectionModel().getSelectedItem()).getPrecioUnitario()));
        txtPrecioD.setText(String.valueOf(((Productos) tbProducto.getSelectionModel().getSelectedItem()).getPrecioDocena()));
        txtPrecioM.setText(String.valueOf(((Productos) tbProducto.getSelectionModel().getSelectedItem()).getPrecioMayor()));
        txtExistencia.setText(String.valueOf(((Productos) tbProducto.getSelectionModel().getSelectedItem()).getExistencia()));
        cmbCodigoTipoProducto.getSelectionModel().select(buscarTipoP(((Productos) tbProducto.getSelectionModel().getSelectedItem()).getCodigoTipoProducto()));

    }

    public TipoProducto buscarTipoP(int codigoTipoProducto) {
        TipoProducto resultado = null;
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarTipoProducto(?)}");
            procedimiento.setInt(1, codigoTipoProducto);
            ResultSet registro = procedimiento.executeQuery();

            while (registro.next()) {
                resultado = new TipoProducto(registro.getInt("codigoTipoProducto"),
                        registro.getString("descripcion")
                );

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultado;
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
                        resultado.getString("imagenProducto"),
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

    public ObservableList<TipoProducto> getTipoProducto() {
        // Crea Arraylist llamada lista para almacenar los datos del listar
        ArrayList<TipoProducto> lista = new ArrayList();

        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarTipoProducto()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                // agrega a nuestro arrayList los datos para listar
                lista.add(new TipoProducto(resultado.getInt("codigoTipoProducto"),
                        resultado.getString("descripcion")));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Almacena en nuestro ObservableList listaTipoProducto lo agregado anteriormente en lista
        return listaTipoProducto = FXCollections.observableArrayList(lista);
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
        Productos registro = new Productos();
        registro.setCodigoProducto((txtCodigoP.getText()));
        registro.setDescripcionProducto(txtDescripcionP.getText());
        registro.setPrecioUnitario(Double.parseDouble(txtPrecioUnitarioP.getText()));
        registro.setPrecioDocena(Double.parseDouble(txtPrecioD.getText()));
        registro.setPrecioMayor(Double.parseDouble(txtPrecioM.getText()));
        registro.setExistencia(Integer.parseInt(txtExistencia.getText()));
        registro.setCodigoTipoProducto(((TipoProducto) cmbCodigoTipoProducto.getSelectionModel().getSelectedItem()).getCodigoTipoProducto());
        registro.setCodigoProveedor(((Proveedores) cmbCodigoProveedor.getSelectionModel().getSelectedItem()).getCodigoProveedor());

        try {
            // haremos conexion con la base de datos para poder registrar datos por medio del procedimiento almacenado
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call Sp_AgregarProductos(?, ?, ?, ?, ?, ?, ?, ?)}");
            procedimiento.setString(1, registro.getCodigoProducto());
            procedimiento.setString(2, registro.getDescripcionProducto());
            procedimiento.setDouble(3, registro.getPrecioUnitario());
            procedimiento.setDouble(4, registro.getPrecioDocena());
            procedimiento.setDouble(5, registro.getPrecioMayor());
            procedimiento.setInt(6, registro.getExistencia());
            procedimiento.setInt(7, registro.getCodigoTipoProducto());
            procedimiento.setInt(8, registro.getCodigoProveedor());
            procedimiento.execute();
            // agregamos a la listaTipoProducto el objeto registro que contiene los datos que agregamos.
            listaProductos.add(registro);

        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    // Metodo para desactivar controller
    public void desactivarControles() {
        txtCodigoP.setEditable(false);
        txtDescripcionP.setEditable(false);
        txtPrecioUnitarioP.setEditable(false);
        txtPrecioD.setEditable(false);
        txtPrecioM.setEditable(false);
        txtExistencia.setEditable(false);
    }

    // metodo para activarControllers
    public void activarControles() {
        txtCodigoP.setEditable(true);
        txtDescripcionP.setEditable(true);
        txtPrecioUnitarioP.setEditable(true);
        txtPrecioD.setEditable(true);
        txtPrecioM.setEditable(true);
        txtExistencia.setEditable(true);

    }

    // metodo para limpiar
    public void LimpiarControles() {
        txtCodigoP.clear();
        txtDescripcionP.clear();
        txtPrecioUnitarioP.clear();
        txtPrecioD.clear();
        txtPrecioM.clear();
        txtExistencia.clear();
        cmbCodigoTipoProducto.getSelectionModel().getSelectedItem();

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
