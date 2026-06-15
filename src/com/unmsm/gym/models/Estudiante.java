package src.com.unmsm.gym.models;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import src.com.unmsm.gym.Main.HorarioCuposVisitas;

public class Estudiante extends Persona {
    /*               Atributos               */
    private String facultad;
    private String carrera;
    private String baseInicio;
    private boolean autoseguroActivo;
    private LocalDate ultimaVisita;  
    private boolean visitanteConcurrente;
    private boolean matriculadoSemestreActual;
    private boolean presentaReservacion;
    private boolean presentaPenalidades;
    private short cantidadPenalidades;
    private boolean presentaLesionActual;
    private boolean vetadoTemporalmente;
    private int numeroDePuntos;
    private int nivel;

    /*              Constructor              */
    public Estudiante(
        int id, 
        String nombre, 
        String apellido, 
        String nombreDeUsuario, 
        String contrasenia, 
        String facultad, 
        String carrera, 
        String baseInicio, 
        boolean autoseguroActivo, 
        boolean matriculadoSemestreActual,
        boolean presentaLesionActual) {

        super(id, nombre, apellido, nombreDeUsuario, contrasenia);
        this.facultad = facultad;
        this.carrera = carrera;
        this.baseInicio = baseInicio;
        this.autoseguroActivo = autoseguroActivo;
        this.ultimaVisita = null;
        this.visitanteConcurrente = false;
        this.matriculadoSemestreActual = matriculadoSemestreActual;
        this.presentaReservacion = false;
        this.presentaPenalidades = false;
        this.cantidadPenalidades = 0;
        this.presentaLesionActual = presentaLesionActual;
        this.vetadoTemporalmente = false;
        this.numeroDePuntos = 0;
        this.nivel = 0;
    }

    /*               Metodos               */
    public void reservarTurno(int opcionHorario, Estudiante estudiante, List<HorarioCuposVisitas> horariosInformacion, List<List<Integer>> reservas) {
        
        // verificar que haya disponibilidad en el turno
        int indiceNuevoHorario = opcionHorario - 1;
        if (horariosInformacion.get(indiceNuevoHorario).cupos() == 0) {
            System.out.println("(!) Ese horario ya no tiene cupos");
            return;
        }

        // ubicar la posicion (i) en la que se encuentre una posible reserva anterior
        /*
                                ArrayList reservas
                (i) ID del Estudiante, indice de ArrayList horarios
        */
        int indiceReservaAnterior = 0;
        List<Integer> reservaAnterior = null;
        for (int i = 0; i < reservas.size(); i++) {
            // guardar consecutivamente ID del Estudiante e indice de ArrayList horarios
            
            //   - Siempre tendra 2 elementos [index:0, index:1]
            //   - Se sobreescribe en cada iteración
            
            reservaAnterior = reservas.get(i);


            // si el usuario tiene una reserva anterior, el ID que se acaba de guardar va a coincidir con su ID
            if (reservaAnterior.get(0) == estudiante.obtenerId()) {
                indiceReservaAnterior = i;
                break;
            }
        }

        // si no se encontro una reserva en el bloque de atras, el valor de presentaReservacion es false
        int indiceHorarioAnterior = 0;
        if (estudiante.presentaReservacion()) {
            System.out.println("(!) Se reemplazo su reserva anterior por este nuevo horario");

            // se obtiene el indice de ArrayList horarios
            indiceHorarioAnterior = reservaAnterior.get(1);

            // se libera un cupo de ArrayList horarios para que alguien mas pueda acceder
            HorarioCuposVisitas horarioConNuevoAforo = new HorarioCuposVisitas(
                        horariosInformacion.get(indiceHorarioAnterior).horario(), 
                        (horariosInformacion.get(indiceHorarioAnterior).cupos() + 1), 
                        horariosInformacion.get(indiceHorarioAnterior).cantidadDeVisitas()
                    );
            horariosInformacion.set(indiceHorarioAnterior, horarioConNuevoAforo);

            // se elimina la reserva de ArrayList reservas
            reservas.remove(indiceReservaAnterior);
        }

        // agregar nuevaReserva [ID del Estudiante, indice de ArrayList horarios]
        List<Integer> nuevaReserva = new ArrayList<>();
        nuevaReserva.add(estudiante.obtenerId());
        nuevaReserva.add(indiceNuevoHorario);
        reservas.add(nuevaReserva);

        // se ocupa un cupo de ArrayList horarios para que acceda el estudiante que lo reservo
        HorarioCuposVisitas horarioConNuevoAforo = new HorarioCuposVisitas(
                        horariosInformacion.get(indiceNuevoHorario).horario(), 
                        (horariosInformacion.get(indiceNuevoHorario).cupos() - 1), 
                        horariosInformacion.get(indiceNuevoHorario).cantidadDeVisitas()
                    );
        horariosInformacion.set(indiceNuevoHorario, horarioConNuevoAforo);
        estudiante.establecerEstadoDeReservacion(true);

        System.out.println("Reserva realizada para " + horariosInformacion.get(indiceNuevoHorario).horario());
    }

    /*           Getters y setters           */
    public String obtenerFacultad() { return facultad; }
    public void establecerFacultad(String facultad) { this.facultad = facultad; }

    public String obtenerCarrera() { return carrera; }
    public void establecerCarrera(String carrera) { this.carrera = carrera; }

    public String obtenerBaseInicio() { return baseInicio; }
    public void establecerBaseInicio(String baseInicio) { this.baseInicio = baseInicio; }

    public boolean tieneAutoseguroActivo() { return autoseguroActivo; }
    public void establecerEstadoDeAutoseguro(boolean autoseguroActivo) { this.autoseguroActivo = autoseguroActivo; }

    public LocalDate obtenerUltimaVisita() {
        if (ultimaVisita == null) {
            System.out.println("(!) El usuario no ha registrado una visita aun");
            return null;
        }
        return this.ultimaVisita;
    }
    public void establecerUltimaVisita(LocalDate ultimaVisita) { this.ultimaVisita = ultimaVisita; }

    public boolean esVisitanteConcurrente() { return visitanteConcurrente;  }
    public void establecerEstadoDeVisitanteConcurrente(boolean visitanteConcurrente) { this.visitanteConcurrente = visitanteConcurrente; }

    public boolean estaMatriculadoSemestreActual() { return matriculadoSemestreActual; }
    public void establecerEstadoDeMatriculaSemestreActual(boolean matriculadoSemestreActual) { this.matriculadoSemestreActual = matriculadoSemestreActual; }

    public boolean presentaReservacion() { return presentaReservacion; }
    public void establecerEstadoDeReservacion(boolean presentaReservacion) { this.presentaReservacion = presentaReservacion; }

    public boolean presentaPenalidades() { return presentaPenalidades; }
    public void establecerEstadoDePenalidades(boolean presentaPenalidades) { this.presentaPenalidades = presentaPenalidades; }

    public short obtenerCantidadPenalidades() { return cantidadPenalidades; }
    public void establecerCantidadPenalidades(short cantidadPenalidades) { this.cantidadPenalidades = cantidadPenalidades; }

    public boolean presentaLesionActual() { return presentaLesionActual;  }
    public void establecerEstadoDeLesionActual(boolean presentaLesionActual) { this.presentaLesionActual = presentaLesionActual; }

    public boolean estaVetadoTemporalmente() { return vetadoTemporalmente; }
    public void establecerVetoTemporal(boolean vetadoTemporalmente) { this.vetadoTemporalmente = vetadoTemporalmente; }

    public int obtenerNumeroDePuntos() { return numeroDePuntos; }
    public void establecerNumeroDePuntos(int numeroDePuntos) { this.numeroDePuntos = numeroDePuntos; }

    public int obtenerNivel() { return nivel; }
    public void establecerNivel(int nivel) { this.nivel = nivel; }
}