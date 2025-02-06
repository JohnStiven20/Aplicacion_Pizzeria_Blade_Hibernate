package aplicacion.controller;

import java.util.Date;

import aplicacion.modelo.Coche;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class InsertarController {

    @FXML
    private TextField matricula;

    @FXML
    private TextField marca;
    @FXML
    private TextField modelo;
    @FXML
    private Button confirmar;
    @FXML
    private Button cancelar;

    private Coche ciudad;
    private Coche nuevaCiudad;

    public void insertar() {

        Stage stage = (Stage) this.confirmar.getScene().getWindow();
        nuevaCiudad = new Coche(0, matricula.getText(), marca.getText(), modelo.getText(), new Date());
        this.ciudad = nuevaCiudad;
        stage.close();

        // Alert alert = new Alert(AlertType.ERROR);
        // alert.setContentText("Algun datos erroneo en el input");
        // alert.setHeaderText(null);
        // alert.setTitle("Formato incorrecto");
        // alert.showAndWait();
    }

    public void cancelar() {
        Stage stage = (Stage) this.cancelar.getScene().getWindow();
        stage.close();
        setCoche(ciudad);
    }

    public Coche getCoche() {
        return ciudad;
    }

    public void setCoche(Coche ciudad) {
        this.ciudad = ciudad;
    }
}
