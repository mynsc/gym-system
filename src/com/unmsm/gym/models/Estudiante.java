package src.com.unmsm.gym.models;

import java.time.LocalDate;

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
}