package cr.ac.una.proyectosolitario.controller;

import com.jfoenix.controls.JFXButton;
import cr.ac.una.proyectosolitario.Baraja;
import cr.ac.una.proyectosolitario.Carta;
import cr.ac.una.proyectosolitario.Puntaje;
import cr.ac.una.proyectosolitario.Tablero;
import cr.ac.una.proyectosolitario.util.AppContext;
import cr.ac.una.proyectosolitario.util.FlowController;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.util.Duration;

public class juegoViewController extends Controller implements Initializable, Serializable {

    @FXML
    private AnchorPane paneOpciones;
    @FXML
    private AnchorPane root;
    @FXML
    private GridPane contenedorColumnas;
    @FXML
    private HBox contenedorMazo;
    @FXML
    private HBox contenedorPilas;
    @FXML
    private Label labelPuntaje;
    @FXML
    private JFXButton btnCartas1;
    @FXML
    private JFXButton btnCartas2;
    @FXML
    private JFXButton btnBackground1;
    @FXML
    private JFXButton btnBackground2;
    @FXML
    private JFXButton btnBackground3;
    @FXML
    private JFXButton btnBackground4;
    @FXML
    private AnchorPane paneWin;
    @FXML
    private Label labelNomJugador;
    /*
    clases del juego
     */
    Baraja baraja;
    Tablero tablero;
    Puntaje puntaje;
    Carta carta = new Carta();
    List<Carta> dragCards = new ArrayList<>();
    List<Carta> transferibles;
    String palos;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        root.getStyleClass().add("background4-juego");

        if (AppContext.getInstance().get("nombre") != null) {
            nuevoJuego();
        } else if (AppContext.getInstance().get("JDnombre") != null) {
            juegoCargado();
        }
        /*
        metodos del drag and drop
         */
        moverCarta();
        validarMovimiento();
        colocarCarta();
        /*
        metodo para repartir el mazo
         */
        repartir();

    }

    private void nuevoJuego() {
        palos = (String) AppContext.getInstance().get("palos");
        AppContext.getInstance().set("dificultad", palos);
        labelNomJugador.setText("Jugador: " + (String) AppContext.getInstance().get("nombre"));
        /*
        Inicializa clases del juego
         */
        puntaje = new Puntaje();
        baraja = new Baraja(Integer.valueOf(palos));
        tablero = new Tablero(baraja);
        baraja.deshabilitar();
        baraja.cartas.forEach(c -> {
            c.setCarpeta("cartas1");
            AppContext.getInstance().set("diseño", "cartas1");
        });
        /*
        se colocan las cartas repartidas sobre las columnas 
        y las cartas sin repartir en el mazo
         */
        tablero.columnas(baraja);
        for (int i = 0; i < 10; i++) {
            contenedorColumnas.addColumn(i, tablero.columnas.get(i));
        }
        contenedorMazo.getChildren().addAll(tablero.mazo());
        animacionEntrada();
        /*
        coloca las pilas vacias en el tablero
         */
        contenedorPilas.getChildren().addAll(tablero.pilas);
        /*
        Efectos para las cartas repartidas y el mazo
         */
        tablero.contenedores.forEach(contenedor -> {
            contenedor.getStyleClass().add("hbox-contenedor-mazo-contenedores");
        });
        tablero.columnas.forEach(columna -> {
            for (int i = 0; i < columna.getChildren().size(); i++) {
                columna.getChildren().get(i).getStyleClass().add("cartas-repartidas");
            }
        });
        /*
        Se define el puntaje inicial
         */
        labelPuntaje.setText(puntaje.getPuntaje());
    }

    private void juegoCargado() {
        palos = (String) AppContext.getInstance().get("JDpalos");
        AppContext.getInstance().set("dificultad", palos);
        labelNomJugador.setText("Jugador: " + (String) AppContext.getInstance().get("JDnombre"));
        /*
        Inicializa clases del juego
         */
        puntaje = new Puntaje();
        baraja = new Baraja(Integer.valueOf(palos));
        tablero = new Tablero(baraja);
        baraja.deshabilitar();
        baraja.cartas.forEach(c -> {
            c.setCarpeta((String) AppContext.getInstance().get("JDdiseño"));
            AppContext.getInstance().set("diseño", "cartas1");
        });
        /*
        se colocan las cartas repartidas sobre las columnas 
        y las cartas sin repartir en el mazo
         */
        tablero.columnas(baraja);
        for (int i = 0; i < 10; i++) {
            contenedorColumnas.addColumn(i, tablero.columnas.get(i));
        }
        contenedorMazo.getChildren().addAll(tablero.mazo());
        animacionEntrada();
        /*
        coloca las pilas vacias en el tablero
         */
        contenedorPilas.getChildren().addAll(tablero.pilas);
        /*
        Efectos para las cartas repartidas y el mazo
         */
        tablero.contenedores.forEach(contenedor -> {
            contenedor.getStyleClass().add("hbox-contenedor-mazo-contenedores");
        });
        tablero.columnas.forEach(columna -> {
            for (int i = 0; i < columna.getChildren().size(); i++) {
                columna.getChildren().get(i).getStyleClass().add("cartas-repartidas");
            }
        });
        if (AppContext.getInstance().get("JDcartaPerso") != null) {
            baraja.cartas.forEach(carta -> {
                if (carta.isDisable()) {
                    carta.setImage((Image) AppContext.getInstance().get("JDcartaPerso"));
                    carta.setFitHeight(143);
                    carta.setFitWidth(95);
                }
            });
        } else {
            baraja.cartas.forEach(carta -> {
                if (carta.isDisable()) {
                    carta.setBackImage();
                }
            });
        }
        labelPuntaje.setText((String) AppContext.getInstance().get("JDpuntaje"));
    }

    private void mover() {

        transferibles = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            transferibles.add((Carta) tablero.contenedores.get(tablero.contenedores.size() - 1).
                    getChildren().get(i));
        }
    }

    /**
     * reparte 10 cartas del mazo en el tablero
     */
    public void repartir() {

        tablero.contenedores.forEach(contenedor -> {
            contenedor.setOnMouseClicked((event) -> {
                mover();
                animacionMazo();
                for (int i = 0; i < tablero.columnas.size(); i++) {
                    transferibles.get(i).setDisable(false);
                    transferibles.get(i).setFaceImage();
                    transferibles.get(i).getStyleClass().add("cartas-repartidas");
                    tablero.columnas.get(i).getChildren().addAll(transferibles.get(i));
                }
                transferibles.clear();
                tablero.contenedores.remove(tablero.contenedores.size() - 1);
            });

        });
    }

    public void animacionMazo() {

        int duracion = 100;
        for (int i = 0; i < transferibles.size(); i++) {
            TranslateTransition tt = new TranslateTransition();
            tt.setDuration(Duration.millis(duracion));
            tt.setFromX(tablero.contenedores.get(tablero.contenedores.size() - 1).getParent().getLayoutX());
            tt.setFromY(tablero.contenedores.get(tablero.contenedores.size() - 1).getParent().getLayoutY());
            tt.setToX(transferibles.get(i).getX());
            tt.setToY(transferibles.get(i).getY());
            tt.setNode(transferibles.get(i));
            tt.play();
            duracion += 60;
        }
    }

    private void animacionEntrada() {
        int duracion = 100;
        for (int j = 0; j < tablero.cartasRepartidas.size(); j++) {
            TranslateTransition tt = new TranslateTransition();
            tt.setDuration(Duration.millis(duracion));
            tt.setFromX(tablero.contenedores.get(0).getParent().getLayoutX());
            tt.setFromY(tablero.contenedores.get(0).getParent().getLayoutY());
            tt.setToX(tablero.cartasRepartidas.get(j).getParent().getLayoutX());
            tt.setToY(tablero.cartasRepartidas.get(j).getParent().getLayoutY());
            tt.setNode(tablero.cartasRepartidas.get(j));
            tt.play();
            duracion += 20;
        }
    }

    private void moverCarta() {
        baraja.cartas.forEach((Carta cartaSource) -> {
            cartaSource.setOnDragDetected((MouseEvent event) -> {
                dragCards.clear();
                carta = cartaSource;
//                carta.setVisible(false);
                //inserto en la lista las cartas que esten debajo de la que toco
                for (int i = carta.getParent().getChildrenUnmodifiable().indexOf(carta);
                        i < carta.getParent().getChildrenUnmodifiable().size(); i++) {
                    dragCards.add((Carta) carta.getParent().getChildrenUnmodifiable().get(i));
                }
                Dragboard db = carta.startDragAndDrop(TransferMode.MOVE);
                ClipboardContent content = new ClipboardContent();
                content.putImage(carta.getImage());
                //validaciones
                if (dragCards.size() != 1) {//si arrastro dos o mas cartas
                    /* 
                        si las cartas no son del mismo palo, no se pueden
                        arrastrar todas a la vez, o no son escalera
                     */
                    for (int i = 1; i < dragCards.size(); i++) {
                        if (dragCards.get(i).getPalo() != carta.getPalo()
                                || dragCards.get(i).getRango() != carta.getRango() - i) {
                            carta.getStyleClass().add("cartas-repartidas-bloqueadas");
                            db.setContent(null);
                            i = dragCards.size();
                        } else {
                            db.setContent(content);
                        }
                    }
                } else {//si solo arrastro una carta
                    db.setContent(content);
                }
                event.consume();
            });
        });
    }

    private void validarMovimiento() {
        baraja.cartas.forEach((Carta cartaTarget) -> {
            cartaTarget.setOnDragOver((DragEvent event) -> {
                //Solo permite colocar cartas en escalera
                if (cartaTarget.getRango() == carta.getRango() + 1) {
                    event.acceptTransferModes(TransferMode.ANY);
                }
                event.consume();
            });
        });
        /*
        Si la columna esta vacia se permite colocar cualquier
        carta sobre ella
         */
        tablero.columnas.forEach(columna -> {
            columna.setOnDragOver((event) -> {
                if (columna.getChildren().isEmpty()) {
                    event.acceptTransferModes(TransferMode.MOVE);
                }
                event.consume();
            });
        });
    }

    private void colocarCarta() {
        tablero.columnas.forEach((VBox columna) -> {
            columna.setOnDragDropped((DragEvent event) -> {
                /*
                metodos para voltear las cartas boca abajo
                 */
                if (carta.getParent().getChildrenUnmodifiable().indexOf(carta) != 0) {
                    if (dragCards.size() != 1) {
                        baraja.habilitarAnterior((VBox) carta.getParent(), dragCards);
                    } else {
                        baraja.habilitarAnterior((VBox) carta.getParent());
                    }
                }
                Dragboard db = event.getDragboard();
                //Si la jugada es permitida
                if (db.hasImage()) {
                    carta.setImage(db.getImage());
//                    carta.setVisible(true);
                    columna.getChildren().addAll(dragCards);
                    //resta un punto por el movimiento
                    puntaje.restarPuntaje(labelPuntaje);
                    //valida si ha completado una escalera
                    llenarPilas(columna);
                    resetearEstilos();
                }
                event.consume();
            });
        });
    }

    private void llenarPilas(VBox columna) {
        //inserta todas las cartas habilitadas de una columna
        List<Carta> escalera = new ArrayList<>();
        for (int i = 0; i < columna.getChildren().size(); i++) {
            if (!columna.getChildren().get(i).isDisable()) {
                escalera.add((Carta) columna.getChildren().get(i));
            }
        }

        if (escalera.size() >= 13) {
            for (int j = 0; j < escalera.size(); j++) {
                //Si hay 13 cartas en orden descendente
                if (escalera.get(j).getRango() == 13 && escalera.get(j + 12).getRango() == 1
                        && escalera.get(j).getPalo() == escalera.get(j + 1).getPalo()) {
                    for (int i = 0; i < tablero.pilas.size(); i++) {
                        //Verifica si hay una pila vacia
                        if (tablero.pilas.get(i).getChildren().isEmpty()) {
                            //Inserta la escalera en esa pila
                            tablero.pilas.get(i).getChildren().addAll(escalera.subList(j, j + 13));
                            /*
                            valida si ya no quedan cartas en las
                            columnas
                             */
                            int cont = 0;
                            for (VBox columna1 : tablero.columnas) {
                                if (columna1.getChildren().isEmpty()) {
                                    cont++;
                                    if (cont == tablero.columnas.size()) {
                                        paneWin.setVisible(true);
                                    }
                                }
                            }
                            //las deshabilita para que no se puedan usar mas
                            tablero.pilas.get(i).setDisable(true);
                            //aumenta 100 puntos por completar una escalera
                            puntaje.aumentarPuntaje(labelPuntaje);
                            tablero.pilas.get(i).getStyleClass().add("pilas-llenas");
                            escalera.clear();
                            baraja.habilitarAnterior(columna, escalera);
                        }
                    }
                }
            }
        }
    }

    private void resetearEstilos() {
        baraja.cartas.forEach(c -> {
            c.getStyleClass().clear();
        });
        tablero.columnas.forEach(columna -> {
            for (int i = 0; i < columna.getChildren().size(); i++) {
                columna.getChildren().get(i).getStyleClass().add("cartas-repartidas");
            }
        });
    }

    @FXML
    private void onActionbtnSalir(ActionEvent event) {
        AppContext.getInstance().delete("nombre");
        AppContext.getInstance().delete("JDnombre");
        FlowController.getInstance().goMain();

    }

    @FXML
    private void onAxtionOpciones(ActionEvent event) {
        root.setVisible(false);
        paneOpciones.setVisible(true);
    }

    @FXML
    private void onActionBtnGuardar(ActionEvent event) {
        AppContext.getInstance().set("puntaje", labelPuntaje);
        FlowController.getInstance().goViewInWindowModal("guardarView", getStage(), Boolean.FALSE);
        FlowController.getInstance().initialize();
    }

    @FXML
    private void onActionBtnHint(ActionEvent event) {
        List<Carta> jugables = new ArrayList<>();
        tablero.columnas.forEach(columna -> {
            if (!columna.getChildren().isEmpty()) {
                jugables.add((Carta) columna.getChildren().get(columna.getChildren().size() - 1));
            }
        });
        for (int i = 0; i < jugables.size(); i++) {
            for (int j = 1; j < jugables.size(); j++) {
                if (jugables.get(i).getRango() == jugables.get(j).getRango() + 1) {
                    jugables.get(i).getStyleClass().add("pilas-llenas");
                    jugables.get(j).getStyleClass().add("pilas-llenas");
                    i = jugables.size();
                    j = jugables.size();
                }
            }
        }
        jugables.clear();
    }

    @FXML
    private void onActionAceptar(ActionEvent event) {
        paneOpciones.setVisible(false);
        root.setVisible(true);
    }

    @FXML
    private void onActionCancelar(ActionEvent event) {
        paneOpciones.setVisible(false);
        root.setVisible(true);
    }

    @FXML
    private void onActionCartas1(ActionEvent event) {
        baraja.cartas.forEach((Carta c) -> {
            c.setCarpeta("cartas1");
            AppContext.getInstance().set("diseño", "cartas1");
            btnCartas1.getStyleClass().add("marco-cartas1");
            btnCartas2.getStyleClass().clear();
            btnCartas2.getStyleClass().add("jfx-btn-cartas2");

        });
        baraja.ocultar();
    }

    @FXML
    private void onActionCartas2(ActionEvent event) {
        baraja.cartas.forEach((Carta c) -> {
            c.setCarpeta("cartas2");
            AppContext.getInstance().set("diseño", "cartas2");
            btnCartas2.getStyleClass().add("marco-cartas2");
            btnCartas1.getStyleClass().clear();
            btnCartas1.getStyleClass().add("jfx-btn-cartas1");
        });
        baraja.ocultar();
    }

    @FXML
    private void onActionbtnBackground1(ActionEvent event) {
        root.getStyleClass().clear();
        root.getStyleClass().add("background1-juego");
        btnBackground1.getStyleClass().add("jfx-btn-background1-marco");
        btnBackground2.getStyleClass().clear();
        btnBackground3.getStyleClass().clear();
        btnBackground4.getStyleClass().clear();
        btnBackground2.getStyleClass().add("jfx-btn-background2");
        btnBackground3.getStyleClass().add("jfx-btn-background3");
        btnBackground4.getStyleClass().add("jfx-btn-background4");
    }

    @FXML
    private void onActionbtnBackground2(ActionEvent event) {
        root.getStyleClass().clear();
        root.getStyleClass().add("background2-juego");
        btnBackground2.getStyleClass().add("jfx-btn-background2-marco");
        btnBackground1.getStyleClass().clear();
        btnBackground3.getStyleClass().clear();
        btnBackground4.getStyleClass().clear();
        btnBackground1.getStyleClass().add("jfx-btn-background1");
        btnBackground3.getStyleClass().add("jfx-btn-background3");
        btnBackground4.getStyleClass().add("jfx-btn-background4");
    }

    @FXML
    private void onActionbtnBackground3(ActionEvent event) {
        root.getStyleClass().clear();
        root.getStyleClass().add("background3-juego");
        btnBackground3.getStyleClass().add("jfx-btn-background3-marco");
        btnBackground1.getStyleClass().clear();
        btnBackground2.getStyleClass().clear();
        btnBackground4.getStyleClass().clear();
        btnBackground1.getStyleClass().add("jfx-btn-background1");
        btnBackground2.getStyleClass().add("jfx-btn-background2");
        btnBackground4.getStyleClass().add("jfx-btn-background4");
    }

    @FXML
    private void onActionbtnBackground4(ActionEvent event) {
        root.getStyleClass().clear();
        root.getStyleClass().add("background4-juego");
        btnBackground4.getStyleClass().add("jfx-btn-background4-marco");
        btnBackground1.getStyleClass().clear();
        btnBackground2.getStyleClass().clear();
        btnBackground3.getStyleClass().clear();
        btnBackground1.getStyleClass().add("jfx-btn-background1");
        btnBackground2.getStyleClass().add("jfx-btn-background2");
        btnBackground3.getStyleClass().add("jfx-btn-background3");
    }

    @Override
    public void initialize() {

    }

    @FXML
    private void onMouseClickPaneWinSalir(MouseEvent event) {
        paneWin.setVisible(false);
    }

    @FXML
    private void onActionImportarDiseño(ActionEvent event) throws FileNotFoundException {

        FileChooser fileChooser = new FileChooser();
        List<File> selectedFiles = fileChooser.showOpenMultipleDialog(null);
        Image img = new Image(new FileInputStream(selectedFiles.get(0)));
        AppContext.getInstance().set("cartaPerso", selectedFiles.get(0).getAbsoluteFile());
        baraja.cartas.forEach(carta -> {
            if (carta.isDisable()) {
                carta.setImage(img);
                carta.setFitHeight(143);
                carta.setFitWidth(95);
            }
        });
    }
}
