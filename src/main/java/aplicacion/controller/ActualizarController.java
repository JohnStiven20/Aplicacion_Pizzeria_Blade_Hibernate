package aplicacion.controller;


import java.util.Date;
import aplicacion.modelo.Coche;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ActualizarController {

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
    int estado = 0;

    public void confirmar() {
        Stage stage = (Stage) this.confirmar.getScene().getWindow();

        if (validar() == 1) {
            this.coche = new Coche(coche.getId(), matricula.getText(), marca.getText(), modelo.getText(), new Date());
            
            stage.close();
        } else {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setContentText("Error en el formato");
            alert.setHeaderText(null); 
            alert.setTitle("Error");
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.showAndWait();
        }
    }

    public void cancelar(){
        Stage stage = (Stage) this.cancelar.getScene().getWindow();
        setCoche(coche);
        stage.close();
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
        return this.estado;
    }
    
    public void setCoche(@SuppressWarnings("exports") Coche coche) {
        this.coche = coche;
        matricula.setText(coche.getMatricula());
        marca.setText(coche.getMarca());
        modelo.setText(coche.getModelo());
    }

    @SuppressWarnings("exports")
    public Coche getCoche() {
        return this.coche;
    }

}
