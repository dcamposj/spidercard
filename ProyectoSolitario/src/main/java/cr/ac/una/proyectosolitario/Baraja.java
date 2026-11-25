package cr.ac.una.proyectosolitario;

import animatefx.animation.FlipInY;
import java.util.Collections;
import java.util.List;
import java.util.Stack;
import javafx.scene.layout.VBox;

public class Baraja {

    public Stack<Carta> cartas = new Stack<>();

    public Baraja(int palos) {

        int i = 0;
        while (i < 104) {
            for (int Palo = 1; Palo <= palos; Palo++) {
                for (int Rango = 1; Rango < 14; Rango++) {
                    cartas.push(new Carta(Palo, Rango));
                    i++;
                }
            }
        }
        cartas = desordenar();
    }

    private Stack<Carta> desordenar() {
        Collections.shuffle(cartas);
        return cartas;
    }

    public void deshabilitar() {
        cartas.forEach((Carta carta) -> {
            carta.setDisable(true);
        });
    }

    public void ocultar() {
        cartas.forEach((Carta carta) -> {
            if (carta.isDisable()) {
                carta.setBackImage();
            } else {
                carta.setFaceImage();
            }
        });
    }

    public void mostrar() {
        cartas.forEach((Carta carta) -> {
            if (!carta.isDisable()) {
                carta.setFaceImage();
            }
        });
    }
    //Animacion de la carta

    private void voltear(Carta carta) {
        FlipInY f = new FlipInY(carta);
        f.play();
    }

    // En caso de que arrastre varias cartas
    public void habilitarAnterior(VBox contenedor, List c) {
        if (contenedor.getChildren().get((contenedor.getChildren().size() - 1) - c.size()).isDisable()) {
            contenedor.getChildren().get((contenedor.getChildren().size() - 1) - c.size()).setDisable(false);
            voltear((Carta) contenedor.getChildren().get((contenedor.getChildren().size() - 1) - c.size()));
            mostrar();
        }
    }

    // En caso de que solo arrastre una carta
    public void habilitarAnterior(VBox contenedor) {
        if (contenedor.getChildren().get((contenedor.getChildren().size() - 1) - 1).isDisable()) {
            contenedor.getChildren().get((contenedor.getChildren().size() - 1) - 1).setDisable(false);
            voltear((Carta) contenedor.getChildren().get((contenedor.getChildren().size() - 1) - 1));
            mostrar();
        }
    }
}
