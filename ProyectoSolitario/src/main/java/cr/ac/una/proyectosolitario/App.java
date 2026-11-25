package cr.ac.una.proyectosolitario;

import cr.ac.una.proyectosolitario.util.FlowController;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) {
        FlowController.getInstance().InitializeFlow(stage, null);
        FlowController.getInstance().goMain();
        stage.getIcons().add(new Image("cr/ac/una/proyectosolitario/resources/iconos/cards_logo.png"));
        stage.setTitle("SOLITARIO SPIDER");
    }

    public static void main(String[] args) {
        launch();
    }

}
