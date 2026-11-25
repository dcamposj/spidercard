package cr.ac.una.proyectosolitario.model;

import javafx.beans.property.SimpleStringProperty;
import javax.xml.bind.annotation.XmlTransient;

public class JugadorDto {

    public SimpleStringProperty jugId;
    public SimpleStringProperty jugNombre;
    public SimpleStringProperty jugPuntaje;
    public SimpleStringProperty jugDificultad;
    public SimpleStringProperty jugDiseño;
    public byte[] jugCartapers;
    @XmlTransient
    private Boolean modificado;

    public JugadorDto() {
        this.jugId = new SimpleStringProperty();
        this.jugNombre = new SimpleStringProperty();
        this.jugPuntaje = new SimpleStringProperty();
        this.jugDificultad = new SimpleStringProperty();
        this.jugDiseño = new SimpleStringProperty();
        this.jugCartapers = new byte[100];
    }

    public JugadorDto(Long id, String Apodo) {
        this();
        setJugId(id);
        setJugNombre(Apodo);

    }

    public JugadorDto(Jugador jugador) {
        this();
        this.jugId.set(jugador.getId().toString());
        this.jugNombre.set(jugador.getNombre());
        this.jugPuntaje.set(jugador.getPuntaje());
        this.jugDificultad.set(jugador.getDificultad());
        this.jugDiseño.set(jugador.getDiseñoCarta());
        this.jugCartapers = (byte[]) jugador.getCartaPerso();
    }

    public Long getJugId() {
        if (jugId.get() != null && !jugId.get().isEmpty()) {
            return Long.valueOf(jugId.get());
        } else {
            return null;
        }
    }

    public void setJugId(Long jugId) {
        this.jugId.set(jugId.toString());
    }

    public void setJugNombre(String jugApodo) {
        this.jugNombre.set(jugApodo);
    }

    public void setJugPuntaje(String jugPuntaje) {
        this.jugPuntaje.set(jugPuntaje);
    }

    public void setJugDificultad(String jugDificultad) {
        this.jugDificultad.set(jugDificultad);
    }

    public void setJugDiseño(String jugDiseño) {
        this.jugDiseño.set(jugDiseño);
    }

    public void setJugCartapers(byte[] jugCartapers) {
        this.jugCartapers = jugCartapers;
    }
    

    public void setModificado(Boolean modificado) {
        this.modificado = modificado;
    }

    public String getJugNombre() {
        return jugNombre.get();
    }

    public String getJugPuntaje() {
        return jugPuntaje.get();
    }

    public Boolean getModificado() {
        return modificado;
    }

    public String getJugDificultad() {
        return jugDificultad.get();
    }

    public String getJugDiseño() {
        return jugDiseño.get();
    }

    public byte[] getJugCartapers() {
        return jugCartapers;
    }
    

}
