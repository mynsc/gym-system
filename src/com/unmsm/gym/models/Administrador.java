package src.com.unmsm.gym.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class Administrador extends Persona {
    /*              Constructores              */
    public Administrador() {}
    
    public Administrador(String nombre, String apellido, String nombreDeUsuario, String contrasenia) {
        super(nombre, apellido, nombreDeUsuario, contrasenia);
    }

    /*               Metodos               */
    public int buscarReservaPorId(Estudiante estudiante, List<List<Integer>> reservas) {
        // ubicar la posicion (i) en la que se encuentre una posible reserva activa
        /*
                                LinkedList reservas
                (i) ID del Estudiante, codigo del horario
        */
        for (int indiceReserva = 0; indiceReserva < reservas.size(); indiceReserva++) {
            // guardar consecutivamente ID del Estudiante y codigo del horario
            
            //   - Siempre tendra 2 elementos [index:0, index:1]
            //   - Se sobreescribe en cada iteración
            
            List<Integer> reservaActiva = reservas.get(indiceReserva);


            // si tiene una reserva activa, el ID que se acaba de guardar va a coincidir con su ID
            if (reservaActiva.get(0) == estudiante.obtenerId()) {
                return indiceReserva;
            }
        }

        // si no tiene una reserva activa, retorna un valor que sirve de flag: no hay reserva activa
        return -1;
    }

    public static void registrarEstudianteEnBD(Connection conexion, Estudiante estudiante) {
        String sqlPersona = """
                    INSERT INTO persona(
                        nombre,
                        apellido,
                        nombre_de_usuario,
                        contrasenia,
                        rol
                    ) VALUES (?, ?, ?, ?, 'ESTUDIANTE')
                    """;
        PreparedStatement sentenciaPersona = null;

        try {
            /*                    INSERTAR EN PERSONA                           */
            // RETURN_GENERATED_KEYS permite recuperar la el ID de la persona
            sentenciaPersona = conexion.prepareStatement(sqlPersona, Statement.RETURN_GENERATED_KEYS);
            sentenciaPersona.setString(1, estudiante.obtenerNombre());
            sentenciaPersona.setString(2, estudiante.obtenerApellido());
            sentenciaPersona.setString(3, estudiante.obtenerNombreDeUsuario());
            sentenciaPersona.setString(4, estudiante.obtenerContrasenia());
            sentenciaPersona.executeUpdate();

            // recuperar el ID y asginarlo al estudiante
            idPersonaGenerado = sentenciaPersona.getGeneratedKeys();
            if (idPersonaGenerado.next()) estudiante.establecerId(idPersonaGenerado.getInt(1));
        } catch (SQLException e) {
            // cancelar todo si algun INSERT falla
            try {
                // deshacer la insercion de uno u otro INSERT para no dejar huerfanos
                conexion.rollback();
                System.out.println("(!) Error, cancelando el registro");
            } catch (SQLException re) {}
            e.printStackTrace();
        } finally {
            try {
                // cerrar los PreparedStatement y el ResultSet solo si se llegaron a declarar
                if (sentenciaPersona != null) sentenciaPersona.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}