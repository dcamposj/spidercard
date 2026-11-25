
package cr.ac.una.proyectosolitario.controller;

import cr.ac.una.proyectosolitario.util.FlowController;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class AcercaDeViewController extends Controller implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
    }    

    @Override
    public void initialize() {

    }

    @FXML
    private void onActionGoMain(ActionEvent event) {
        FlowController.getInstance().goMain();
    }
    
}
