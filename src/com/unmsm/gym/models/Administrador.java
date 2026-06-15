package src.com.unmsm.gym.models;

import java.util.List;

public class Administrador extends Persona {
    /*              Constructores              */
    public Administrador() {
        super(0, "Administrador", "Principal", "admin", "admin123"); 
    }
    
    public Administrador(int id, String nombre, String apellido, String nombreDeUsuario, String contrasenia) {
        super(id, nombre, apellido, nombreDeUsuario, contrasenia);
    }

    /*               Metodos               */
    public int buscarReservaPorId(Estudiante estudiante, List<List<Integer>> reservas) {
        // ubicar la posicion (i) en la que se encuentre una posible reserva activa
        /*
                                ArrayList reservas
                (i) ID del Estudiante, codigo del horario
        */
        for (int indiceReserva = 0; indiceReserva < reservas.size(); indiceReserva++) {
            // guardar consecutivamente ID del Estudiante y codigo del horario
            
            //   - Siempre tendra 2 elementos [index:0, index:1]
            //   - Se sobreescribe en cada iteración
            
            List<Integer> reservaAnterior = reservas.get(indiceReserva);


            // si tiene una reserva activa, el ID que se acaba de guardar va a coincidir con su ID
            if (reservaAnterior.get(0) == estudiante.obtenerId()) {
                return indiceReserva;
            }
        }

        // si no tiene una reserva activa, retorna un valor que sirve de flag: no hay reserva activa
        return -1;
    }
}