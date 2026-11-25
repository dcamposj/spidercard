package cr.ac.una.proyectosolitario.service;

import cr.ac.una.proyectosolitario.model.Jugador;
import cr.ac.una.proyectosolitario.model.JugadorDto;
import cr.ac.una.proyectosolitario.util.EntityManagerHelper;
import cr.ac.una.proyectosolitario.util.Respuesta;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Query;

public class JugadorService {

    EntityManager em = EntityManagerHelper.getManager();
    private EntityTransaction et;

    public Respuesta getJugador(String nombre) {
        try {
            Query qryJugador = em.createNamedQuery("Jugador.findByJugNombre", Jugador.class);
            qryJugador.setParameter("nombre", nombre);

            Jugador jugador = (Jugador) qryJugador.getSingleResult();
            JugadorDto jugadorDto = new JugadorDto(jugador);
            return new Respuesta(true, "", "", "Jugador", jugadorDto);

        } catch (NoResultException ex) {
            return new Respuesta(false, "Error obteniendo el jugador.", "getJugador " + ex.getMessage());
        } catch (Exception ex) {
            Logger.getLogger(JugadorService.class.getName()).log(Level.SEVERE, "Error obteniendo el jugador [" + nombre + "]", ex);
            return new Respuesta(false, "Error obteniendo el jugador.", "getJugador " + ex.getMessage());
        }
    }

    public Respuesta getJugadores() {

        Query qryJugador = em.createNamedQuery("Jugador.findAll", Jugador.class);

        List<Jugador> jugadores = (List<Jugador>) qryJugador.getResultList();
        List<JugadorDto> jugadoresDto = new ArrayList<>();
        jugadores.forEach((jugador) -> {
            jugadoresDto.add(new JugadorDto(jugador));
        });
        return new Respuesta(true, "", "", "Jugador", jugadoresDto);

    }

    public Respuesta guardarJugador(JugadorDto jugadorDto) {
        try {
            et = em.getTransaction();
            et.begin();
            Jugador jugador;
            if (jugadorDto.getJugId() != null && jugadorDto.getJugId() > 0) {
                jugador = em.find(Jugador.class, jugadorDto.getJugId());
                if (jugador == null) {
                    et.rollback();
                    return new Respuesta(false, "No se encontro el jugador.",
                            "guardarJugador NoResultException");
                }
                jugador.actualizarJugador(jugadorDto);
                jugador = em.merge(jugador);
            } else {
                jugador = new Jugador(jugadorDto);
                em.persist(jugador);
            }
            et.commit();
            return new Respuesta(true, "", "", "Jugador", new JugadorDto(jugador));
        } catch (Exception ex) {
            et.rollback();
            Logger.getLogger(JugadorService.class.getName()).log(Level.SEVERE, "Ocurrio un error al guardar el jugador.", ex);
            return new Respuesta(false, "Ocurrio un error al guardar el jugador.", "guardarJugador " + ex.getMessage());
        }
    }
}
