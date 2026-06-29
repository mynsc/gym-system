package com.unmsm.gym.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
    public Reserva buscarReservaActiva(Estudiante estudiante, List<Reserva> reservas) {
        // recorrer la lista de reservas
        for (int indiceReserva = 0; indiceReserva < reservas.size(); indiceReserva++) {
            // guardar posible reserva activa
            Reserva reservaActiva = reservas.get(indiceReserva);

            // si tiene una reserva activa, el ID que se acaba de guardar va a coincidir con su ID
            if (reservaActiva.obtenerIdUsuario() == estudiante.obtenerId()) {
                return reservaActiva;
            }
        }

        return null;
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
        String sqlEstudiante = """
                    INSERT INTO estudiante(
                        id_persona,
                        tipo_de_estudiante,
                        facultad,
                        carrera,
                        base_inicio,
                        autoseguro_activo,
                        matriculado_semestre_actual,
                        presenta_lesion,
                        deporte,
                        tipo_de_discapacidad,
                        nivel_de_discapacidad,
                        ultima_visita,
                        visitante_concurrente,
                        presenta_reservacion,
                        presenta_penalidades,
                        cantidad_penalidades,
                        vetado_temporalmente,
                        numero_de_puntos,
                        nivel
                    ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                    """;

        PreparedStatement sentenciaPersona = null;
        PreparedStatement sentenciaEstudiante = null;
        ResultSet idPersonaGenerado = null;

        try {
            // desactivar autocommit para que se realicen ambas inserciones o ninguna
            conexion.setAutoCommit(false);

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

            /*                    INSERTAR EN ESTUDIANTE                        */
            sentenciaEstudiante = conexion.prepareStatement(sqlEstudiante);
            sentenciaEstudiante.setInt(1, estudiante.obtenerId());
            sentenciaEstudiante.setString(2, estudiante.obtenerTipoDeEstudiante());
            sentenciaEstudiante.setString(3, estudiante.obtenerFacultad());
            sentenciaEstudiante.setString(4, estudiante.obtenerCarrera());
            sentenciaEstudiante.setString(5, estudiante.obtenerBaseInicio());
            sentenciaEstudiante.setBoolean(6, estudiante.tieneAutoseguroActivo());
            sentenciaEstudiante.setBoolean(7, estudiante.estaMatriculadoSemestreActual());
            sentenciaEstudiante.setBoolean(8, estudiante.presentaLesion());
            sentenciaEstudiante.setString(9, estudiante.obtenerDeporte());
            sentenciaEstudiante.setString(10, estudiante.obtenerTipoDeDiscapacidad());
            sentenciaEstudiante.setString(11, estudiante.obtenerNivelDeDiscapacidad());
            sentenciaEstudiante.setObject(12, estudiante.obtenerUltimaVisita());
            sentenciaEstudiante.setBoolean(13, estudiante.esVisitanteConcurrente());    
            sentenciaEstudiante.setBoolean(14, estudiante.presentaReservacion());
            sentenciaEstudiante.setBoolean(15, estudiante.presentaPenalidades());
            sentenciaEstudiante.setInt(16, estudiante.obtenerCantidadPenalidades());
            sentenciaEstudiante.setBoolean(17, estudiante.estaVetadoTemporalmente());
            sentenciaEstudiante.setInt(18, estudiante.obtenerNumeroDePuntos());
            sentenciaEstudiante.setInt(19, estudiante.obtenerNivel());
            sentenciaEstudiante.executeUpdate();

            conexion.commit();
            System.out.print("(!) Registro exitoso, bienvenido, " + estudiante.obtenerNombre());
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
                // restaurar el comportamiento por defecto del autocommit
                conexion.setAutoCommit(true);

                // cerrar los PreparedStatement y el ResultSet solo si se llegaron a declarar
                if (sentenciaPersona != null) sentenciaPersona.close();
                if (sentenciaEstudiante != null)sentenciaEstudiante.close();
                if (idPersonaGenerado != null) idPersonaGenerado.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}