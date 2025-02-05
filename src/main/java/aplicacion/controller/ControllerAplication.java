package aplicacion.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import aplicacion.modelo.Coche;
import aplicacion.services.CocheService;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

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


    private Coche coche;
    private ObservableList<Coche> coches;
    private CocheService cocheService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cocheService = new CocheService();
        coches = FXCollections.observableArrayList();
        cargarCoches();
        tableView.getSelectionModel().selectedItemProperty().addListener((oyente, viejo, nuevo) -> cambiarSelecionado(nuevo));
    }

    @FXML
    private void cargarCoches() {
        new Thread(() -> {
            try {
                List<Coche> respuesta = CocheService.getAllCoches();

                // Actualizar UI en el hilo de JavaFX
                Platform.runLater(() -> {
                    // Convertir lista a ObservableList
                    coches = FXCollections.observableArrayList(respuesta);

                    // Definir columnas de la tabla
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

                    // Asignar la lista actualizada a la tabla
                    tableView.setItems(coches);
                });

            } catch (Exception e) {
               e.printStackTrace();
            }
        }).start();
    }

    public void cambiarSelecionado(Coche coche) {
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

    // public void borrar() {
    //     int opcion = cocheDao.borrar(coche);

    //     if (opcion == 1) {
    //         cargarCoches();
    //     }

    // }

    // public void guardar() {
    //     cocheService.addCoche(coche);
    //     cargarCoches();
    // }

    // public void actualizar() {
    //     if (coche != null) {
    //         Coche ciudadActualizada = cocheDao.actualizar(coche);
    //         cargarCoches();
    //     }
    // }

}
