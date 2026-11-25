package cr.ac.una.proyectosolitario.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "SS_JUGADORES", schema = "una")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Jugador.findAll", query = "SELECT j FROM Jugador j"),
    @NamedQuery(name = "Jugador.findByJugId", query = "SELECT j FROM Jugador j WHERE j.id = :id"),
    @NamedQuery(name = "Jugador.findByJugNombre", query = "SELECT j FROM Jugador j WHERE j.nombre = :nombre")})
public class Jugador implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @SequenceGenerator(name = "SS_JUGADORES_JUG_ID_GENERATOR", sequenceName = "una.SS_JUGADORES_SEQ01", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SS_JUGADORES_JUG_ID_GENERATOR")
    @Basic(optional = false)
    @Column(name = "JUG_ID")
    private Long id;
    @Basic(optional = false)
    @Column(name = "JUG_NOMBRE")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "JUG_PUNTAJE")
    private String puntaje;
    @Version
    @Basic(optional = false)
    @Column(name = "JUG_VERSION")
    private Long version;
    @Basic(optional = false)
    @Column(name = "JUG_DIFICULTAD")
    private String dificultad;
    @Basic(optional = false)
    @Column(name = "JUG_DISE\u00d1O")
    private String diseñoCarta;
    @Lob
    @Column(name = "JUG_PARTIDA")
    private String partida;
    @Lob
    @Column(name = "JUG_CARTPER")
    private byte[] cartaPerso;

    public Jugador() {
        this.version = Long.valueOf(1);
    }

    public Jugador(Long jugId) {
        this.id = jugId;
        this.version = Long.valueOf(1);
    }

    public Jugador(Long jugId, String jugNombre, String jugPuntaje, Long jugVersion, String jugDificultad, String jugDiseño) {
        this.id = jugId;
        this.nombre = jugNombre;
        this.puntaje = jugPuntaje;
        this.version = jugVersion;
        this.dificultad = jugDificultad;
        this.diseñoCarta = jugDiseño;
        this.version = Long.valueOf(1);
    }

    public Jugador(JugadorDto jugadorDto) {
        this.id = jugadorDto.getJugId();
        actualizarJugador(jugadorDto);
    }

    public void actualizarJugador(JugadorDto jugadorDto) {
        this.nombre = jugadorDto.getJugNombre();
        this.puntaje = jugadorDto.getJugPuntaje();
        this.dificultad = jugadorDto.getJugDificultad();
        this.diseñoCarta = jugadorDto.getJugDiseño();
        this.cartaPerso = jugadorDto.getJugCartapers();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(String puntaje) {
        this.puntaje = puntaje;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getDificultad() {
        return dificultad;
    }

    public void setDificultad(String dificultad) {
        this.dificultad = dificultad;
    }

    public String getDiseñoCarta() {
        return diseñoCarta;
    }

    public void setDiseñoCarta(String diseñoCarta) {
        this.diseñoCarta = diseñoCarta;
    }

    public String getPartida() {
        return partida;
    }

    public void setPartida(String partida) {
        this.partida = partida;
    }

    public Serializable getCartaPerso() {
        return cartaPerso;
    }

    public void setCartaPerso(byte[] cartaPerso) {
        this.cartaPerso = cartaPerso;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Jugador)) {
            return false;
        }
        Jugador other = (Jugador) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cr.ac.una.proyectosolitario.model.Jugador[ jugId=" + id + " ]";
    }

}
