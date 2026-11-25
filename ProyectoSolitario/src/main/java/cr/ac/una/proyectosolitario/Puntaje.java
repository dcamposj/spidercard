package cr.ac.una.proyectosolitario;

import javafx.scene.control.Label;

public class Puntaje{
    
    int puntos;

    public Puntaje() {
        puntos = 500;
    }

    public String getPuntaje() {
        String s = Integer.toString(puntos);
        return s;
    }
    
    public void aumentarPuntaje(Label puntaje){
        puntos +=100;
        puntaje.setText(getPuntaje());
    }

    public void restarPuntaje(Label puntaje) {
        puntos--;
        puntaje.setText(getPuntaje());
    }
}
