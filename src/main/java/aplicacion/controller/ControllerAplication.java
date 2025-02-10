package aplicacion.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import aplicacion.modelo.Coche;
import aplicacion.services.CocheService;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ControllerAplication implements Initializable {

    @FXML
    private TableView<Coche> tableView;
    @FXML
    private TextField matricula;
    @FXML
    private TextField modelo;
    @FXML
    private TextField marca;
    @FXML
    private TextField date;
    @FXML
    private TextField id;
    @FXML
    private  TextField encontrarMatricula;

    private Coche coche;
    private ObservableList<Coche> coches;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        coches = FXCollections.observableArrayList();
        cargarCoches();
        tableView.getSelectionModel().selectedItemProperty().addListener((oyente, viejo, nuevo) -> {
            cambiarSelecionado(nuevo);
            this.coche = nuevo;
        });

    }

    public  void buscarCoche() {

        if (!this.encontrarMatricula.getText().isBlank()) {
            new Thread(() -> {

                Coche cocheEncontrado = CocheService.findByMatricula(this.encontrarMatricula.getText());

                if (cocheEncontrado != null) {
                    Platform.runLater(() -> {
                        List<Coche> coches = new ArrayList<>(Arrays.asList(cocheEncontrado));
                        tableView.setItems(FXCollections.observableArrayList(coches));
                    });
                }
                
            }).start();
            
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("El campo esta vacio");
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.showAndWait();
        }

    }

    @FXML
    private void cargarCoches() {
        new Thread(() -> {

            try {
                List<Coche> respuesta = CocheService.getAllCoches();

                Platform.runLater(() -> {
                    coches = FXCollections.observableArrayList(respuesta);

                    String[] campos = { "id", "Matricula", "Marca", "Modelo", "Fecha" };
                    double[] minAnchos = { 25, 100, 100, 100, 200 };

                    tableView.getColumns().clear();

                    for (int i = 0; i < campos.length; i++) {
                        final TableColumn<Coche, String> finalTableColumn = new TableColumn<>(campos[i]);
                        final int index = i;
                        finalTableColumn.setCellValueFactory(cellData -> {
                            Coche coche = cellData.getValue();
                            switch (campos[index]) {
                                case "id":
                                    return new SimpleStringProperty(String.valueOf(coche.getId()));
                                case "Matricula":
                                    return new SimpleStringProperty(coche.getMatricula());
                                case "Marca":
                                    return new SimpleStringProperty(coche.getMarca());
                                case "Modelo":
                                    return new SimpleStringProperty(coche.getModelo());
                                case "Fecha":
                                    return new SimpleStringProperty(String.valueOf(coche.getDate()));
                                default:
                                    return null;
                            }
                        });

                        finalTableColumn.setMinWidth(minAnchos[i]);
                        tableView.getColumns().add(finalTableColumn);
                    }

                    tableView.setItems(coches);
                });

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }).start();
    }

    public void cambiarSelecionado(@SuppressWarnings("exports") Coche coche) {
        if (coche != null) {
            modelo.setText(coche.getModelo());
            matricula.setText(coche.getMatricula());
            marca.setText(coche.getMarca());
            date.setText(coche.getDate() + "");
            id.setText(coche.getId() + "");
        } else {
            modelo.setText("");
            matricula.setText("");
            marca.setText("");
            date.setText("");
            id.setText("");
        }
    }

    @FXML
    public void insertar() {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/aplicacion/insertar.fxml"));

        try {

            Parent parent = loader.load();
            InsertarController insertarController = loader.getController();
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.showAndWait();
        
            int estado = insertarController.getEstado();

            if (estado == 1) {
                new Thread(() -> {

                    Coche ciudadNueva = insertarController.getCoche();
    
                    boolean apto = CocheService.addCoche(ciudadNueva);

                    if (apto) {
                        cargarCoches();
                    }
    
                }).start();
            }

            

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    public void borrar() {

        if (this.coche != null) {

            new Thread(() -> {
                int opcion = CocheService.delete(this.coche);

                if (opcion == 1) {
                    cargarCoches();
                }
            }).start();

        } else {

            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("Tiene que pinchar en la tabla un coche");
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.showAndWait();

        }
    }

    @FXML
    public void actualizar() {

        if (this.coche != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/aplicacion/actualizar.fxml"));

            try {

                Parent parent = loader.load();
                ActualizarController actualizarController = loader.getController();
                Scene scene = new Scene(parent);
                Stage stage = new Stage();
                stage.setScene(scene);
                actualizarController.setCoche(this.coche);
                stage.showAndWait();

                new Thread(() -> {

                    Coche cocheNuevo = actualizarController.getCoche();
                    int estado = actualizarController.getEstado();

                    if (estado == 1) {
                        CocheService.updateCoche(cocheNuevo);
                        cargarCoches();
                    }

                }).start();

            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        } else {

            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("Tiene que pinchar en la tabla un coche");
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.showAndWait();
        }
    }

    
}
