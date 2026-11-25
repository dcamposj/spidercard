package cr.ac.una.proyectosolitario.controller;

import com.jfoenix.controls.JFXButton;
import cr.ac.una.proyectosolitario.util.FlowController;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

public class MainController extends Controller implements Initializable {

    @FXML
    private BorderPane root;
    @FXML
    private JFXButton btnIniciarJuego;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @Override
    public void initialize() {

    }

    @FXML
    private void onActionbtnIniciarJuego(ActionEvent event) {
        FlowController.getInstance().goView("RegistroJugadorView");
        FlowController.getInstance().initialize();
    }

    @FXML
    private void onActionbtnPuntuaciones(ActionEvent event) {
        FlowController.getInstance().goView("TablaJugadoresView");
        FlowController.getInstance().initialize();
    }

    @FXML
    private void onActionInstrucciones(ActionEvent event) {
        FlowController.getInstance().goView("InstruccionesView");
    }

    @FXML
    private void onActionAcercaDe(ActionEvent event) {
        FlowController.getInstance().goView("AcercaDeView");
    }

    @FXML
    private void onActionbtnCargar(ActionEvent event) {
        FlowController.getInstance().goView("CargarView");
        FlowController.getInstance().initialize();
    }
}
