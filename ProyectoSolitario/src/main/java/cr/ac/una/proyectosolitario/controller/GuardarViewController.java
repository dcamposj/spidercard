package cr.ac.una.proyectosolitario.controller;

import cr.ac.una.proyectosolitario.model.JugadorDto;
import cr.ac.una.proyectosolitario.service.JugadorService;
import cr.ac.una.proyectosolitario.util.AppContext;
import cr.ac.una.proyectosolitario.util.FlowController;
import cr.ac.una.proyectosolitario.util.Mensaje;
import cr.ac.una.proyectosolitario.util.Respuesta;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javax.imageio.ImageIO;

public class GuardarViewController extends Controller implements Initializable {

    Label p = (Label) AppContext.getInstance().get("puntaje");
    String nombre = (String) AppContext.getInstance().get("nombre");
    String dificultad = (String) AppContext.getInstance().get("dificultad");
    String diseño = (String) AppContext.getInstance().get("diseño");
    File file = (File) AppContext.getInstance().get("cartaPerso");
    JugadorDto jugadorDto;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        jugadorDto = new JugadorDto();
    }

    @Override
    public void initialize() {

    }

    @FXML
    private void onActionAceptar(ActionEvent event) {
        try {
            jugadorDto.setJugPuntaje(p.getText());
            jugadorDto.setJugNombre(nombre);
            jugadorDto.setJugDiseño(diseño);
            jugadorDto.setJugDificultad(dificultad);
            if (file != null) {
                BufferedImage bImage = ImageIO.read(file);
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                ImageIO.write(bImage, "jpg", bos);
                jugadorDto.jugCartapers = bos.toByteArray();
            }
            JugadorService service = new JugadorService();
            Respuesta respuesta = service.guardarJugador(jugadorDto);
            if (!respuesta.getEstado()) {
                new Mensaje().showModal(Alert.AlertType.ERROR, "Guardar Partida", getStage(), respuesta.getMensaje());
            } else {
                jugadorDto = (JugadorDto) respuesta.getResultado("Jugador");
                new Mensaje().showModal(Alert.AlertType.INFORMATION, "Guardar partida", getStage(),
                        "Partida guardada correctamente");
                FlowController.getInstance().getController("guardarView").getStage().close();
            }
        } catch (Exception ex) {
            Logger.getLogger(GuardarViewController.class.getName()).log(Level.SEVERE, "Error guardando la partida", ex);
            new Mensaje().showModal(Alert.AlertType.ERROR, "Guardar jugador", getStage(),
                    "Ocurrio un error guardando la partida");
        }
    }

    @FXML
    private void onActionCancelar(ActionEvent event) {
        FlowController.getInstance().getController("guardarView").getStage().close();
    }

}
