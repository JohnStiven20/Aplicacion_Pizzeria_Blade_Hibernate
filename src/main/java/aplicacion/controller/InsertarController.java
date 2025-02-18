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

    private Coche coche;
    private Coche cocheNuevo;
    private int estado = 0;

    public void insertar() {

        if (validar() == 1) {
            Stage stage = (Stage) this.confirmar.getScene().getWindow();
            cocheNuevo = new Coche(0, matricula.getText(), marca.getText(), modelo.getText(), new Date());
            this.coche = cocheNuevo;
            stage.close();

        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("Algun datos erroneo en el input");
            alert.setHeaderText(null);
            alert.setTitle("Formato incorrecto");
            alert.showAndWait();

        }

    }

    public void cancelar() {
        Stage stage = (Stage) this.cancelar.getScene().getWindow();
        stage.close();
        setCoche(coche);
    }

    public int validar() {

        if (this.matricula.getText().isBlank()) {
            this.estado = 0;
            return 0;

        } else if (marca.getText().isBlank()) {
            this.estado = 0;
            return 0;

        } else if (modelo.getText().isBlank()) {
            this.estado = 0;
            return 0;

        }

        this.estado = 1;
        return 1;
    }

    public int getEstado() {
        return estado;
    }

    @SuppressWarnings("exports")
    public Coche getCoche() {
        return coche;
    }

    public void setCoche(@SuppressWarnings("exports") Coche ciudad) {
        this.coche = ciudad;
    }
}
