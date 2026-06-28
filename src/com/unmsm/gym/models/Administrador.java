package src.com.unmsm.gym.models;

import java.util.List;

public class Administrador extends Persona {

    public Administrador() {
        super(0, "Administrador", "Principal", "admin", "admin123");
    }

    public Administrador(int id, String nombre, String apellido, String nombreDeUsuario, String contrasenia) {
        super(id, nombre, apellido, nombreDeUsuario, contrasenia);
    }

    public int buscarReservaPorId(Estudiante estudiante, List<Reserva> reservas) {
        for (int indiceReserva = 0; indiceReserva < reservas.size(); indiceReserva++) {
            if (reservas.get(indiceReserva).getIdUsuario() == estudiante.obtenerId()) {
                return indiceReserva;
            }
        }
        return -1;
    }
}