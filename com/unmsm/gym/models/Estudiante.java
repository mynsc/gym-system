package com.unmsm.gym.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.unmsm.gym.Main;
import com.unmsm.gym.Main.HorarioCuposVisitas;

public class Estudiante extends Persona {
    /*               Atributos               */
    private String tipoDeEstudiante;
    private String facultad;
    private String carrera;
    private String baseInicio;
    private boolean autoseguroActivo;
    private boolean matriculadoSemestreActual;
    private boolean presentaLesion;
    private String deporte;
    private String tipoDeDiscapacidad;
    private String nivelDeDiscapacidad;

    private LocalDate ultimaVisita;  
    private boolean visitanteConcurrente;
    private boolean presentaReservacion;
    private boolean presentaPenalidades;
    private short cantidadPenalidades;
    private boolean vetadoTemporalmente;
    private int numeroDePuntos;
    private int nivel;
    
    private List<Rutina> rutinas;

    /*              Constructor              */
    public Estudiante(
        String nombre, 
        String apellido, 
        String nombreDeUsuario, 
        String contrasenia, 
        String tipoDeEstudiante, 
        String facultad, 
        String carrera, 
        String baseInicio, 
        boolean autoseguroActivo, 
        boolean matriculadoSemestreActual,
        boolean presentaLesion) {

        super(nombre, apellido, nombreDeUsuario, contrasenia);
        this.tipoDeEstudiante = tipoDeEstudiante;
        this.facultad = facultad;
        this.carrera = carrera;
        this.baseInicio = baseInicio;
        this.autoseguroActivo = autoseguroActivo;
        this.matriculadoSemestreActual = matriculadoSemestreActual;
        this.presentaLesion = presentaLesion;
        this.deporte = null;
        this.tipoDeDiscapacidad = null;
        this.nivelDeDiscapacidad = null;
        this.ultimaVisita = null;
        this.visitanteConcurrente = false;
        this.presentaReservacion = false;
        this.presentaPenalidades = false;
        this.cantidadPenalidades = 0;
        this.vetadoTemporalmente = false;
        this.numeroDePuntos = 0;
        this.nivel = 0;
        this.rutinas = new LinkedList<>();
    }

    /*               Metodos               */
    public void reservarTurno(int opcionHorario, List<HorarioCuposVisitas> horariosInformacion, List<List<Integer>> reservas) {
        
        // verificar que haya disponibilidad en el turno
        int codigoNuevoHorario = opcionHorario - 1;
        if (horariosInformacion.get(codigoNuevoHorario).cupos() == 0) {
            System.out.println("(!) Ese horario ya no tiene cupos");
            return;
        }

        // buscar reserva activa
        int indiceReservaActiva = new Administrador().buscarReservaPorId(this, reservas);

        // si se encuentra una reserva, liberar un cupo en ese horario y se elimina la reserva
        if (indiceReservaActiva != -1) {
            System.out.println("(!) Se reemplazo su reserva anterior por este nuevo horario");

            // obtener el codigo del horario de la reserva activa
            int codigoHorarioActivo = reservas.get(indiceReservaActiva).get(1);

            // liberar un cupo de ArrayList horariosInformacion para que alguien mas pueda acceder
            HorarioCuposVisitas horarioConNuevoAforo = new HorarioCuposVisitas(
                        horariosInformacion.get(codigoHorarioActivo).hora(), 
                        (horariosInformacion.get(codigoHorarioActivo).cupos() + 1), 
                        horariosInformacion.get(codigoHorarioActivo).cantidadDeVisitas()
                    );
            horariosInformacion.set(codigoHorarioActivo, horarioConNuevoAforo);

            // eliminar la reserva de LinkedList reservas
            reservas.remove(indiceReservaActiva);
        }

        // agregar nuevaReserva [ID del Estudiante, indice de ArrayList horariosInformacion]
        List<Integer> nuevaReserva = new ArrayList<>();
        nuevaReserva.add(this.obtenerId());
        nuevaReserva.add(codigoNuevoHorario);
        reservas.add(nuevaReserva);

        // ocupar un cupo de ArrayList horariosInformacion para que acceda el estudiante que lo reservo
        HorarioCuposVisitas horarioConNuevoAforo = new HorarioCuposVisitas(
                        horariosInformacion.get(codigoNuevoHorario).hora(), 
                        (horariosInformacion.get(codigoNuevoHorario).cupos() - 1), 
                        horariosInformacion.get(codigoNuevoHorario).cantidadDeVisitas()
                    );
        horariosInformacion.set(codigoNuevoHorario, horarioConNuevoAforo);
        this.establecerEstadoDeReservacion(true);

        System.out.print("(!) Reserva realizada para " + horariosInformacion.get(codigoNuevoHorario).hora());
    }

    public void cancelarReserva(List<HorarioCuposVisitas> horariosInformacion, List<List<Integer>> reservas) {
        // buscar reserva activa
        int indiceReserva = new Administrador().buscarReservaPorId(this, reservas);

        // si no se encuentra una reserva activa, regresar al menu
        if (indiceReserva == -1) {
            System.out.print("(!) No tienes una reserva activa");
            return;
        }

        // obtener el codigo del horario de la reserva activa
        int indiceHorario = reservas.get(indiceReserva).get(1);

        // liberar un cupo de ArrayList horariosInformacion para que alguien mas pueda acceder
        HorarioCuposVisitas horarioConNuevoAforo = new HorarioCuposVisitas(
                    horariosInformacion.get(indiceHorario).hora(), 
                    (horariosInformacion.get(indiceHorario).cupos() + 1), 
                    horariosInformacion.get(indiceHorario).cantidadDeVisitas()
                );
        horariosInformacion.set(indiceHorario, horarioConNuevoAforo);
        this.establecerEstadoDeReservacion(false);

        // eliminar la reserva de LinkedList reservas
        reservas.remove(indiceReserva);

        System.out.print("Reserva cancelada para " + horariosInformacion.get(indiceHorario).hora());
    }

    public boolean registrarRutinaEnBD(Connection conexion) {
        String nombre = "";
        String objetivo = "";
        List<List<String>> ejercicios = new LinkedList<>();
        int cantidadDeEjercicios = 0;

        System.out.println("=== CREAR RUTINA ===");
        nombre = Main.leerNoVacio("Nombre de la rutina >> ");
        objetivo = Main.leerNoVacio("Define el objetivo de la rutina >> ");
        cantidadDeEjercicios = Main.leerEntero("Cantidad de ejercicios de la rutina >> ");

        // crear ejercicio(s) para una rutina
        for (int i = 0; i < cantidadDeEjercicios; i++) {
            String nombreEjercicio = "";
            String sets = "";
            String repeticiones = "";

            nombreEjercicio = Main.leerNoVacio("Nombre del ejercicio >> ");
            sets = Main.leerNoVacio("Sets del ejercicio >> ");
            repeticiones = Main.leerNoVacio("Repeticiones del ejercicio >> ");

            ejercicios.add(new LinkedList<>(List.of(nombreEjercicio, sets, repeticiones)));
        }
        Rutina rutina = new Rutina(nombre, objetivo, this, ejercicios);

        String sqlRutina = "INSERT INTO rutina (id_estudiante, nombre, objetivo) VALUES (?, ?, ?)";
        String sqlEjercicio = "INSERT INTO ejercicio (id_rutina, nombre, series, repeticiones) VALUES (?, ?, ?, ?)";

        PreparedStatement sentenciaRutinas = null;
        PreparedStatement sentenciaEjercicio = null;
        ResultSet idRutinaGenerado = null;

        try {
            // desactivar autocommit para que se realicen ambas inserciones o ninguna
            conexion.setAutoCommit(false); 

            /*                    INSERTAR EN RUTINA                           */
            // RETURN_GENERATED_KEYS permite recuperar la el ID de la rutina
            sentenciaRutinas = conexion.prepareStatement(sqlRutina, PreparedStatement.RETURN_GENERATED_KEYS);
            sentenciaRutinas.setInt(1, rutina.obtenerEstudiante().obtenerId());
            sentenciaRutinas.setString(2, rutina.obtenerNombre());
            sentenciaRutinas.setString(3, rutina.obtenerObjetivo());
            sentenciaRutinas.executeUpdate();

            // recuperar el id y asignarlo a la rutina
            idRutinaGenerado = sentenciaRutinas.getGeneratedKeys();
            if (idRutinaGenerado.next()) rutina.establecerId(idRutinaGenerado.getInt(1)); 

            /*                    INSERTAR EN EJERCICIOS                        */
            if (ejercicios != null && !ejercicios.isEmpty()) {
                sentenciaEjercicio = conexion.prepareStatement(sqlEjercicio);
                    for (List<String> ejercicio : ejercicios) {
                        sentenciaEjercicio.setInt(1, rutina.obtenerId());
                        sentenciaEjercicio.setString(2, ejercicio.get(0));                // nombre
                        sentenciaEjercicio.setInt(3, Integer.parseInt(ejercicio.get(1))); // series
                        sentenciaEjercicio.setInt(4, Integer.parseInt(ejercicio.get(2))); // repeticiones
                        sentenciaEjercicio.executeUpdate();
                    }
            }

            conexion.commit();
            return true;
        } catch (SQLException | NumberFormatException e) {
            // cancelar todo si algun INSERT falla
            try {
                // deshacer la insercion de uno u otro INSERT para no dejar huerfanos
                conexion.rollback();
            } catch (SQLException re) {}
            e.printStackTrace();
            return false;
        } finally {
            try {
                // restaurar el comportamiento por defecto del autocommit
                conexion.setAutoCommit(true);

                // cerrar los PreparedStatement y el ResultSet solo si se llegaron a declarar
                if (sentenciaRutinas != null) sentenciaRutinas.close();
                if (sentenciaEjercicio != null)sentenciaEjercicio.close();
                if (idRutinaGenerado != null) idRutinaGenerado.close();
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }
    }

    public void leerRutinasDesdeBD(Connection conexion) {
        // vaciar rutinas para evitar duplicados
        if (this.rutinas != null) {
            this.rutinas.clear();
        }

        String sqlRutina = "SELECT * FROM rutina WHERE id_estudiante = ?";
        String sqlEjercicio = "SELECT * FROM ejercicio WHERE id_rutina = ?";

        PreparedStatement sentenciaRutinas = null;
        PreparedStatement sentenciaEjercicios = null;
        ResultSet rutinasEncontradas = null;
        ResultSet ejerciciosEncontrados = null;
        try {
            sentenciaRutinas = conexion.prepareStatement(sqlRutina);
            sentenciaRutinas.setInt(1, this.obtenerId());

            // recuperar las rutinas registradas en la base de datos
            rutinasEncontradas = sentenciaRutinas.executeQuery();
                
            while (rutinasEncontradas.next()) {
                // obtener el id, nombre y objetivo de la rutina desde la base de datos
                int idRutina = rutinasEncontradas.getInt("id");
                String nombre = rutinasEncontradas.getString("nombre");
                String objetivo = rutinasEncontradas.getString("objetivo");

                List<List<String>> ejercicios = new ArrayList<>();
                sentenciaEjercicios = conexion.prepareStatement(sqlEjercicio);
                sentenciaEjercicios.setInt(1, idRutina);

                // recuperar los ejercicios registrados en la base de datos
                ejerciciosEncontrados = sentenciaEjercicios.executeQuery();
                while (ejerciciosEncontrados.next()) {
                    List<String> detalles = new ArrayList<>();
                    detalles.add(ejerciciosEncontrados.getString("nombre"));
                    detalles.add(String.valueOf(ejerciciosEncontrados.getInt("series")));
                    detalles.add(String.valueOf(ejerciciosEncontrados.getInt("repeticiones")));
                    ejercicios.add(detalles);
                }

                // instanciar cada rutina encontrada y guardarla(s) en rutinas
                Rutina nuevaRutina = new Rutina(nombre, objetivo, this, ejercicios);
                nuevaRutina.establecerId(idRutina);
                this.rutinas.add(nuevaRutina);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        } finally {
            try {
                // cerrar los PreparedStatement y los ResultSet solo si se llegaron a declarar
                if (sentenciaRutinas != null) sentenciaRutinas.close();
                if (sentenciaEjercicios != null)sentenciaEjercicios.close();
                if (rutinasEncontradas != null) rutinasEncontradas.close();
                if (ejerciciosEncontrados != null) ejerciciosEncontrados.close();
            } catch (SQLException e) {
                e.printStackTrace();
                return;
            }
        }
    }

    public boolean actualizarRutinaEnBD(Connection conexion, Rutina rutina) {
        System.out.println("=== EDITAR RUTINA ===");
        System.out.println(rutina.mostrarDetallesDeRutina());
        System.out.println("==================================================================================");

        // actualizar campos de rutina si no se dejaron vacios
        System.out.print("Nuevo nombre de la rutina >> ");
        String nuevoNombreRutina = Main.scanner.nextLine();
        if (!nuevoNombreRutina.trim().isEmpty()) {
            rutina.establecerNombre(nuevoNombreRutina);
        }

        System.out.print("Nuevo objetivo de la rutina >> ");
        String nuevoObjetivo = Main.scanner.nextLine();
        if (!nuevoObjetivo.trim().isEmpty()) {
            rutina.establecerObjetivo(nuevoObjetivo);
        }

        int nuevaCantidadDeEjercicios = Main.leerEntero("Cantidad de ejercicios de la rutina >> ");
        
        List<List<String>> nuevosEjercicios = new LinkedList<>();
        for (int i = 0; i < nuevaCantidadDeEjercicios; i++) {
            // actualizar campos de un ejercicio si no se dejaron vacios
            String nombreEjercicio = Main.leerNoVacio("Nombre del ejercicio >> ");
            String sets = Main.leerNoVacio("Nombre del ejercicio >> ");
            String repeticiones = Main.leerNoVacio("Nombre del ejercicio >> ");

            nuevosEjercicios.add(new LinkedList<>(List.of(nombreEjercicio, sets, repeticiones)));
        }
        rutina.establecerEjercicios(nuevosEjercicios);

        String sqlUpdateRutina = "UPDATE rutina SET nombre = ?, objetivo = ? WHERE id = ? AND id_estudiante = ?";
        String sqlDeleteEjercicios = "DELETE FROM ejercicio WHERE id_rutina = ?";
        String sqlInsertEjercicios = "INSERT INTO ejercicio (id_rutina, nombre, series, repeticiones) VALUES (?, ?, ?, ?)";

        PreparedStatement sentenciaRutina = null;
        PreparedStatement sentenciaEliminarEjercicio = null;
        PreparedStatement sentenciaInsertarEjercicio = null;
        try {
            // desactivar autocommit para que se realicen actualizacion, eliminacion e insercion o ninguna
            conexion.setAutoCommit(false);

            /*                    INSERTAR EN RUTINA                           */
            sentenciaRutina = conexion.prepareStatement(sqlUpdateRutina);
            sentenciaRutina.setString(1, rutina.obtenerNombre());
            sentenciaRutina.setString(2, rutina.obtenerObjetivo());
            sentenciaRutina.setInt(3, rutina.obtenerId());
            sentenciaRutina.setInt(4, rutina.obtenerEstudiante().obtenerId());

            int filaAfectada = sentenciaRutina.executeUpdate();

            // verificar que la rutina fue actualizada, es decir, existe y le pertenece al estudiante que hizo la peticion
            if (filaAfectada == 0) {
                conexion.rollback();
                return false;
            }

            /*                    ELIMINAR EJERCICIOS                           */
            sentenciaEliminarEjercicio = conexion.prepareStatement(sqlDeleteEjercicios);
            sentenciaEliminarEjercicio.setInt(1, rutina.obtenerId());
            sentenciaEliminarEjercicio.executeUpdate();

            /*                    INSERTAR EN EJERCICIO                           */
            // registrar ejercicios nuevos/actualizados
            if (nuevosEjercicios != null && !nuevosEjercicios.isEmpty()) {
                sentenciaInsertarEjercicio = conexion.prepareStatement(sqlInsertEjercicios);
                for (List<String> ejercicio : nuevosEjercicios) {
                    sentenciaInsertarEjercicio.setInt(1, rutina.obtenerId());
                    sentenciaInsertarEjercicio.setString(2, ejercicio.get(0));
                    sentenciaInsertarEjercicio.setInt(3, Integer.parseInt(ejercicio.get(1)));
                    sentenciaInsertarEjercicio.setInt(4, Integer.parseInt(ejercicio.get(2)));
                    sentenciaInsertarEjercicio.executeUpdate();
                }
            }

            conexion.commit();
            return true;

        } catch (SQLException | NumberFormatException e) {
            // cancelar todo si algun INSERT falla
            try {
                // deshacer la insercion de uno u otro INSERT para no dejar huerfanos
                conexion.rollback();
                System.out.println("(!) Error, cancelando la edicion de rutina");
            } catch (SQLException re) {}
            e.printStackTrace();
            return false;
        } finally {
            try {
                // restaurar el comportamiento por defecto del autocommit
                conexion.setAutoCommit(true);

                // cerrar los PreparedStatement solo si se llegaron a declarar
                if (sentenciaRutina != null) sentenciaRutina.close();
                if (sentenciaEliminarEjercicio != null) sentenciaEliminarEjercicio.close();
                if (sentenciaInsertarEjercicio != null) sentenciaInsertarEjercicio.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    public boolean eliminarRutinaDeBD(Connection conexion, int idRutina, int idEstudiante) {
        String sql = "DELETE FROM rutina WHERE id = ? AND id_estudiante = ?";
        
        try {
            PreparedStatement sentenciaEliminar = conexion.prepareStatement(sql);
            sentenciaEliminar.setInt(1, idRutina);
            sentenciaEliminar.setInt(2, idEstudiante);

            int filaAfectada = sentenciaEliminar.executeUpdate();
            
            // verificar que la rutina fue eliminada, es decir, existia y le pertenecia al estudiante que hizo la peticion
            return filaAfectada != 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void registrarIngreso(List<HorarioCuposVisitas> horariosInformacion, List<List<Integer>> reservas) {
        // buscar reserva activa
        int indiceReserva = new Administrador().buscarReservaPorId(this, reservas);

        // si no se encuentra una reserva activa, regresar al menu
        if (indiceReserva == -1) {
            System.out.print("(!) No tienes una reserva activa para registrar visita");
            return;
        }

        // obtener el codigo del horario de la reserva activa
        int indiceHorario = reservas.get(indiceReserva).get(1);

        // establecer ultima visita en hoy y aumentar el contador de visitas en 1
        this.establecerUltimaVisita(LocalDate.now());
        HorarioCuposVisitas horarioActualizado = new HorarioCuposVisitas(
            horariosInformacion.get(indiceHorario).hora(), 
            horariosInformacion.get(indiceHorario).cupos(), 
            horariosInformacion.get(indiceHorario).cantidadDeVisitas() + 1
        );
        horariosInformacion.set(indiceHorario, horarioActualizado);

        // elimiar la reserva de LinkedList reservas y cambiar su estado de reservación a false
        reservas.remove(indiceReserva);
        this.establecerEstadoDeReservacion(false);

        System.out.print("Visita registrada en el horario " + horariosInformacion.get(indiceHorario).hora());
    }

    /*           Getters y setters           */
    public String obtenerTipoDeEstudiante() { return tipoDeEstudiante; }
    public void establecerTipoDeEstudiante(String tipoDeEstudiante) { this.tipoDeEstudiante = tipoDeEstudiante; }

    public String obtenerFacultad() { return facultad; }
    public void establecerFacultad(String facultad) { this.facultad = facultad; }

    public String obtenerCarrera() { return carrera; }
    public void establecerCarrera(String carrera) { this.carrera = carrera; }

    public String obtenerBaseInicio() { return baseInicio; }
    public void establecerBaseInicio(String baseInicio) { this.baseInicio = baseInicio; }

    public boolean tieneAutoseguroActivo() { return autoseguroActivo; }
    public void establecerEstadoDeAutoseguro(boolean autoseguroActivo) { this.autoseguroActivo = autoseguroActivo; }

    public boolean estaMatriculadoSemestreActual() { return matriculadoSemestreActual; }
    public void establecerEstadoDeMatriculaSemestreActual(boolean matriculadoSemestreActual) { this.matriculadoSemestreActual = matriculadoSemestreActual; }
    
    public boolean presentaLesion() { return presentaLesion;  }
    public void establecerEstadoDeLesion(boolean presentaLesion) { this.presentaLesion = presentaLesion; }

    public String obtenerDeporte() { return deporte; }
    public void establecerDeporte(String deporte) { this.deporte = deporte; }

    public String obtenerTipoDeDiscapacidad() { return tipoDeDiscapacidad; }
    public void establecerTipoDeDiscapacidad(String tipoDeDiscapacidad) { this.tipoDeDiscapacidad = tipoDeDiscapacidad; }

    public String obtenerNivelDeDiscapacidad() { return nivelDeDiscapacidad; }
    public void establecerNivelDeDiscapacidad(String nivelDeDiscapacidad) { this.nivelDeDiscapacidad = nivelDeDiscapacidad; }

    public LocalDate obtenerUltimaVisita() { return this.ultimaVisita; }
    public void establecerUltimaVisita(LocalDate ultimaVisita) { this.ultimaVisita = ultimaVisita; }

    public boolean esVisitanteConcurrente() { return visitanteConcurrente;  }
    public void establecerEstadoDeVisitanteConcurrente(boolean visitanteConcurrente) { this.visitanteConcurrente = visitanteConcurrente; }

    public boolean presentaReservacion() { return presentaReservacion; }
    public void establecerEstadoDeReservacion(boolean presentaReservacion) { this.presentaReservacion = presentaReservacion; }

    public boolean presentaPenalidades() { return presentaPenalidades; }
    public void establecerEstadoDePenalidades(boolean presentaPenalidades) { this.presentaPenalidades = presentaPenalidades; }

    public short obtenerCantidadPenalidades() { return cantidadPenalidades; }
    public void establecerCantidadPenalidades(short cantidadPenalidades) { this.cantidadPenalidades = cantidadPenalidades; }

    public boolean estaVetadoTemporalmente() { return vetadoTemporalmente; }
    public void establecerVetoTemporal(boolean vetadoTemporalmente) { this.vetadoTemporalmente = vetadoTemporalmente; }

    public int obtenerNumeroDePuntos() { return numeroDePuntos; }
    public void establecerNumeroDePuntos(int numeroDePuntos) { this.numeroDePuntos = numeroDePuntos; }

    public int obtenerNivel() { return nivel; }
    public void establecerNivel(int nivel) { this.nivel = nivel; }

    public List<Rutina> obtenerRutinas() { return rutinas; }
    public void establecerRutinas(List<Rutina> rutinas) { this.rutinas = rutinas; }
}