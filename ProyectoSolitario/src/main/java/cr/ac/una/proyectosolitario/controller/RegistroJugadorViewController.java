package cr.ac.una.proyectosolitario.controller;

import cr.ac.una.proyectosolitario.model.JugadorDto;
import cr.ac.una.proyectosolitario.service.JugadorService;
import com.jfoenix.controls.JFXTextField;
import cr.ac.una.proyectosolitario.util.AppContext;
import cr.ac.una.proyectosolitario.util.FlowController;
import cr.ac.una.proyectosolitario.util.Formato;
import cr.ac.una.proyectosolitario.util.Mensaje;
import cr.ac.una.proyectosolitario.util.Respuesta;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class RegistroJugadorViewController extends Controller implements Initializable {

    @FXML
    private AnchorPane selector;
    @FXML
    private JFXTextField txtNombre;
    List<JugadorDto> lista = new ArrayList<>();
    JugadorDto jd;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        txtNombre.setTextFormatter(Formato.getInstance().letrasFormat(30));
        jd = new JugadorDto();

    }

    @Override
    public void initialize() {

    }

    @FXML
    private void onActiontxtNombre(ActionEvent event) {

    }

    @FXML
    private void onMouseClicked1Palo(MouseEvent event) {
        JugadorService service = new JugadorService();
        Respuesta respuesta = service.getJugador(txtNombre.getText());
        jd = (JugadorDto) respuesta.getResultado("Jugador");

        if (!respuesta.getEstado()) {
            if (!txtNombre.getText().isEmpty()) {
                AppContext.getInstance().set("nombre", txtNombre.getText());
                AppContext.getInstance().set("palos", "1");
                FlowController.getInstance().goView("juegoView");
                FlowController.getInstance().initialize();
            }
        } else {
            new Mensaje().showModal(Alert.AlertType.ERROR, "Registro", getStage(),
                    "Ya existe un jugador con ese nombre, elija otro");
        }
    }

    @FXML
    private void onMouseClicked2Palos(MouseEvent event) {
        JugadorService service = new JugadorService();
        Respuesta respuesta = service.getJugador(txtNombre.getText());
        jd = (JugadorDto) respuesta.getResultado("Jugador");

        if (!respuesta.getEstado()) {
            if (!txtNombre.getText().isEmpty()) {
                AppContext.getInstance().set("nombre", txtNombre.getText());
                AppContext.getInstance().set("palos", "2");
                FlowController.getInstance().goView("juegoView");
                FlowController.getInstance().initialize();
            }
        } else {
            new Mensaje().showModal(Alert.AlertType.ERROR, "Registro", getStage(),
                    "Ya existe un jugador con ese nombre, elija otro");
        }
    }

    @FXML
    private void onMouseClicked4Palos(MouseEvent event) {
        JugadorService service = new JugadorService();
        Respuesta respuesta = service.getJugador(txtNombre.getText());
        jd = (JugadorDto) respuesta.getResultado("Jugador");

        if (!respuesta.getEstado()) {
            if (!txtNombre.getText().isEmpty()) {
                AppContext.getInstance().set("nombre", txtNombre.getText());
                AppContext.getInstance().set("palos", "4");
                FlowController.getInstance().goView("juegoView");
                FlowController.getInstance().initialize();
            }
        } else {
            new Mensaje().showModal(Alert.AlertType.ERROR, "Registro", getStage(),
                    "Ya existe un jugador con ese nombre, elija otro");
        }
    }
}
