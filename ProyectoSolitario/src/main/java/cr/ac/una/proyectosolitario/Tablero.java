package cr.ac.una.proyectosolitario;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class Tablero {

    public List<Carta> cartasSinRepartir;
    public List<Carta> cartasRepartidas;
    public List<VBox> columnas;
    public List<StackPane> contenedores;
    public List<StackPane> pilas;

    public Tablero(Baraja baraja) {
        cartasSinRepartir = new ArrayList<>(baraja.cartas.subList(0, 50));
        cartasRepartidas = new ArrayList<>(baraja.cartas.subList(50, 104));
        addColumnas();
        addContenedores();
        addPilas();
    }

    private void addColumnas() {
        columnas = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            columnas.add(new VBox());
        }
    }

    private void addContenedores() {
        contenedores = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            contenedores.add(new StackPane());
        }
    }

    private void addPilas() {
        pilas = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            pilas.add(new StackPane());
        }
    }

    public List mazo() {

        int i = 0, j = 0;
        while (i < 5 && j < 50) {
            contenedores.get(i).getChildren().addAll(cartasSinRepartir.subList(j, j + 10));
            i++;
            j += 10;
        }
        return contenedores;
    }

    public List columnas(Baraja baraja) {

        columnas.get(0).getChildren().addAll(cartasRepartidas.subList(0, 6));
        columnas.get(1).getChildren().addAll(cartasRepartidas.subList(6, 12));
        columnas.get(2).getChildren().addAll(cartasRepartidas.subList(12, 18));
        columnas.get(3).getChildren().addAll(cartasRepartidas.subList(18, 24));
        columnas.get(4).getChildren().addAll(cartasRepartidas.subList(24, 29));
        columnas.get(5).getChildren().addAll(cartasRepartidas.subList(29, 34));
        columnas.get(6).getChildren().addAll(cartasRepartidas.subList(34, 39));
        columnas.get(7).getChildren().addAll(cartasRepartidas.subList(39, 44));
        columnas.get(8).getChildren().addAll(cartasRepartidas.subList(44, 49));
        columnas.get(9).getChildren().addAll(cartasRepartidas.subList(49, 54));

        columnas.forEach((VBox columna) -> {
            columna.spacingProperty().set(-123);
            //Habilita la ultima carta de cada columna
            columna.getChildren().get(columna.getChildren().size() - 1).setDisable(false);
        });

        baraja.ocultar();
        return columnas;
    }
}
