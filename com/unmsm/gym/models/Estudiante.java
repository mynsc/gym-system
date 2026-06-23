package com.unmsm.gym.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
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

    public Rutina crearRutina() {
        String nombre = "";
        String objetivo = "";
        List<List<String>> ejercicios = new LinkedList<>();
        int cantidadDeEjercicios = 0;

        System.out.println("=== CREAR RUTINA ===");
        System.out.print("Nombre de la rutina >> ");
        nombre = Main.scanner.nextLine();
        System.out.print("Define el objetivo de la rutina >> ");
        objetivo = Main.scanner.nextLine();
        cantidadDeEjercicios = Main.leerEntero("Cantidad de ejercicios de la rutina >> ");

        // crear ejercicio(s) para una rutina
        for (int i = 0; i < cantidadDeEjercicios; i++) {
            String nombreEjercicio = "";
            String sets = "";
            String repeticiones = "";

            System.out.print("Nombre del ejercicio >> ");
            nombreEjercicio = Main.scanner.nextLine();
            System.out.print("Sets del ejercicio >> ");
            sets = Main.scanner.nextLine();
            System.out.print("Repeticiones del ejercicio >> ");
            repeticiones = Main.scanner.nextLine();

            ejercicios.add(new LinkedList<>(List.of(nombreEjercicio, sets, repeticiones)));
        }

        // generar ID distinto en un estudiante, pero no en todos
        // todo: generar IDs distintos para cada rutina de cada estudiante
        int nuevoId = rutinas.size();
        return new Rutina(nuevoId, nombre, objetivo, this, ejercicios);
    }

    public void editarRutina() {
        Main.limpiarPantalla();
        int idBuscado = Main.leerEntero("Ingresa el ID de la rutina que deseas editar >> ");

        Rutina rutinaAEditar = null;
        
        // buscar la rutina en la lista
        for (Rutina rutina : this.rutinas) {
            if (rutina.obtenerId() == idBuscado) {
                rutinaAEditar = rutina;
                break;
            }
        }

        // si se encuentra la rutina, pedir los nuevos datos
        if (rutinaAEditar != null) {
            System.out.print("Nuevo nombre (deja en blanco para no cambiar) >> ");
            String nuevoNombre = Main.scanner.nextLine();
            if (!nuevoNombre.trim().isEmpty()) {
                rutinaAEditar.establecerNombre(nuevoNombre);
            }

            System.out.print("Nuevo objetivo (deja en blanco para no cambiar) >> ");
            String nuevoObjetivo = Main.scanner.nextLine();
            if (!nuevoObjetivo.trim().isEmpty()) {
                rutinaAEditar.establecerObjetivo(nuevoObjetivo);
            }

            System.out.println("(!) Rutina actualizada correctamente");
        } else {
            System.out.println("(!) No se encontro ninguna rutina con el ID " + idBuscado);
        }
    }

    public boolean eliminarRutinaDeBD(Connection conexion, int id) {
        String sql = "DELETE FROM rutina WHERE id = ?";
        
        try {
            PreparedStatement sentenciaEliminar = conexion.prepareStatement(sql);
            sentenciaEliminar.setInt(1, id);

            int filaAfectada = sentenciaEliminar.executeUpdate();
            
            // si la fila fue afectada, hubo eliminacion y retorna true
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