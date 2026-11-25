package cr.ac.una.proyectosolitario.controller;

import cr.ac.una.proyectosolitario.model.JugadorDto;
import cr.ac.una.proyectosolitario.service.JugadorService;
import cr.ac.una.proyectosolitario.util.FlowController;
import cr.ac.una.proyectosolitario.util.Respuesta;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class TablaJugadoresViewController extends Controller implements Initializable {

    @FXML
    private TableView<JugadorDto> tbvJugadores;
    @FXML
    private TableColumn<JugadorDto, String> tbcApodo;
    @FXML
    private TableColumn<JugadorDto, String> tbcPuntaje;

    List<JugadorDto> lista = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        JugadorService service = new JugadorService();
        Respuesta respuesta = service.getJugadores();
        lista = (List<JugadorDto>) respuesta.getResultado("Jugador");
        tbcApodo.setCellValueFactory(cd -> cd.getValue().jugNombre);
        tbcPuntaje.setCellValueFactory(cd -> cd.getValue().jugPuntaje);
        tbvJugadores.getItems().addAll(lista);
    }

    @Override
    public void initialize() {

    }

    @FXML
    private void onActiongoMain(ActionEvent event) {
        FlowController.getInstance().goMain();
    }

}
