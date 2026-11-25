package cr.ac.una.proyectosolitario.controller;

import cr.ac.una.proyectosolitario.model.JugadorDto;
import cr.ac.una.proyectosolitario.service.JugadorService;
import com.jfoenix.controls.JFXTextField;
import cr.ac.una.proyectosolitario.util.AppContext;
import cr.ac.una.proyectosolitario.util.FlowController;
import cr.ac.una.proyectosolitario.util.Mensaje;
import cr.ac.una.proyectosolitario.util.Respuesta;
import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class CargarViewController extends Controller implements Initializable {

    @FXML
    private JFXTextField txtNombre;
    @FXML
    private Label labelNombre;
    @FXML
    private Label labelDificultad;
    @FXML
    private Label labelPuntaje;
    @FXML
    private ImageView imgDiseño;
    @FXML
    private ImageView imgCartaPerso;
    JugadorDto jd;
    Image image;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        jd = new JugadorDto();

    }

    @Override
    public void initialize() {

    }

    @FXML
    private void onKeyPressedtxtNombre(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER && !txtNombre.getText().isEmpty()) {
            
            JugadorService service = new JugadorService();
            Respuesta respuesta = service.getJugador(txtNombre.getText());

            if (respuesta.getEstado()) {
                
                jd = (JugadorDto) respuesta.getResultado("Jugador");
                labelNombre.setText("Nombre: " + jd.getJugNombre());
                labelDificultad.setText("Palos : " + jd.getJugDificultad());
                labelPuntaje.setText("Puntaje: " + jd.getJugPuntaje());
                ByteArrayInputStream bis = new ByteArrayInputStream(jd.jugCartapers);
                image = new Image(bis);
                imgCartaPerso.setImage(image);
                imgCartaPerso.setFitHeight(143);
                imgCartaPerso.setFitWidth(95);
                if ("cartas1".equals(jd.getJugDiseño())) {
                    imgDiseño.setImage(new Image("/cr/ac/una/proyectosolitario/resources/cartas1/1e.jpg"));
                    imgDiseño.setFitHeight(143);
                    imgDiseño.setFitWidth(95);
                } else {
                    imgDiseño.setImage(new Image("/cr/ac/una/proyectosolitario/resources/cartas2/1e.jpg"));
                    imgDiseño.setFitHeight(143);
                    imgDiseño.setFitWidth(95);
                }
                AppContext.getInstance().set("JDpalos", jd.getJugDificultad());
                AppContext.getInstance().set("JDpuntaje", jd.getJugPuntaje());
                AppContext.getInstance().set("JDnombre", jd.getJugNombre());
                AppContext.getInstance().set("JDdiseño", jd.getJugDiseño());
                AppContext.getInstance().set("JDcartaPerso", image);

            } else {
                new Mensaje().showModal(Alert.AlertType.ERROR, "Cargar Jugador", getStage(),
                        "Error cargando la partida");
            }
        }

    }

    @FXML
    private void onActionbtnJugar(ActionEvent event) {
        if (AppContext.getInstance().get("JDnombre") != null) {
            FlowController.getInstance().goView("juegoView");
            FlowController.getInstance().initialize();
        } else {
            new Mensaje().showModal(Alert.AlertType.ERROR, "Cargar Jugador", getStage(),
                    "No ha seleccionado su partida");
        }

    }

    @FXML
    private void onActionGoMain(ActionEvent event) {
        FlowController.getInstance().goMain();
    }

}
