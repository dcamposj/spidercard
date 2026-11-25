package cr.ac.una.proyectosolitario;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Carta extends ImageView {

    private int palo;
    private int rango;
    private Image faceImage;
    private Image backImage;
    private String carpeta;

    public Carta() {

    }

    public Carta(int palo, int rango) {
        this.palo = palo;
        this.rango = rango;
    }

    public void setCarpeta(String carpeta) {
        this.carpeta = carpeta;
    }

    public String getCarpeta() {
        return carpeta;
    }

    public static final String[] RANGOS = {
        null, "As", "2", "3", "4", "5", "6", "7",
        "8", "9", "10", "Jack", "Queen", "King"
    };

    public static final String[] PALOS = {
        null, "Espadas", "Diamantes", "Corazones", "Trebol"
    };

    @Override
    public String toString() {
        return RANGOS[this.rango] + " de " + PALOS[this.palo];
    }

    public int getPalo() {
        return palo;
    }

    public int getRango() {
        return rango;
    }

    public Image getFaceImage() {
        return faceImage;
    }

    public Image getBackImage() {
        return backImage;
    }

    public void setFaceImage() {

        String url = "/cr/ac/una/proyectosolitario/resources/" + carpeta + "/";
        switch (palo) {
            case 1:
                setImage(new Image(url + String.valueOf(rango) + "e.jpg"));
                break;
            case 2:
                setImage(new Image(url + String.valueOf(rango) + "d.jpg"));
                break;
            case 3:
                setImage(new Image(url + String.valueOf(rango) + "c.jpg"));
                break;
            case 4:
                setImage(new Image(url + String.valueOf(rango) + "t.jpg"));
                break;
            default:
                break;
        }

    }

    public void setBackImage() {
        setImage(this.backImage = new Image("/cr/ac/una/proyectosolitario"
                + "/resources/" + carpeta + "/back.jpg"));
    }
}
