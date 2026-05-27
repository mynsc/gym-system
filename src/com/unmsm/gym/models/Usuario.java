package src.com.unmsm.gym.models;

import java.time.LocalDate;

public class Usuario {
    private String nombre;

    private String apellido;

    private String codigo_estudiante;   

    private String facultad;

    private String carrera;

    private String baseInicio;

    private boolean autoseguroActivo;

    private LocalDate ultimaVisita;  

    private boolean visitanteConcurrente;

    private boolean matriculadoSemestreActual;

    private boolean presentaReservacion;

    private boolean presentaPenalidades;

    private short cantidadPenalidades = 0;

    private boolean presentaLesionActual;

    private boolean vetadoTemporalmente;



    public Usuario(
        String nombre, 
        String apellido, 
        String codigo_estudiante, 
        String facultad, 
        String carrera, 
        String baseInicio) {

        this.nombre = nombre;
        this.apellido = apellido;
        this.codigo_estudiante = codigo_estudiante;
        this.facultad = facultad;
        this.carrera = carrera;
        this.baseInicio = baseInicio;
    }


    public void mostrarInformacionPersonal() {
        System.out.println("Nombre  :             " + nombre);
        System.out.println("Apellido:             " + apellido);
        System.out.println("Código de Estudiante: " + codigo_estudiante);
        System.out.println("Facultad:             " + facultad);
        System.out.println("Carrera:              " + carrera);
        System.out.println("Base de Inicio:       " + baseInicio);
    }


    public void ActualizarUltimaVisita() {
        this.ultimaVisita = LocalDate.now();
    }
    public LocalDate mostrarUltimaVisita() {
        if(ultimaVisita == null) {
            System.out.println("\t== El usuario no ha registrado una visita aún ==\t");
            return null;
        }
        return this.ultimaVisita;
    }


    public void ActualizarAutoseguroActivo(boolean estado) {
        this.autoseguroActivo = estado;
    }
    public boolean mostrarAutoseguroActivo() {
        return this.autoseguroActivo;
    }


    public void ActualizarVisitanteConcurrente(boolean estado) {
        this.visitanteConcurrente = estado;
    }
    public boolean mostrarVisitanteConcurrente() {
        return this.visitanteConcurrente;
    }

    public boolean mostrarMatriculadoSemestreActual() {
        return this.matriculadoSemestreActual;
    }


    public void ActualizarPresentaReservacion(boolean estado) {
        this.presentaReservacion = estado;
    } 
    public  boolean mostrarPresentaReservacion() {
        return this.presentaReservacion;
    }

    public void ActualizarPresentaPenalidades(boolean estado) {
        this.presentaPenalidades = estado;
    }
    public boolean mostrarPresentaPenalidades() {
        return this.presentaPenalidades;
    }
    
    public void AumentarCantidadPenalidades() {
        this.cantidadPenalidades ++;

        if (this.cantidadPenalidades >= 3) {
            this.vetadoTemporalmente = true;
        }
    }
    public void ReiniciarCantidadPenalidades() {
        this.cantidadPenalidades = 0;
    }

    public void ActualizarVetadoTemporalmente(boolean estado) {
        this.vetadoTemporalmente = estado;
    }
    public boolean mostrarVetadoTemporalmente() {
        return this.vetadoTemporalmente;
    }


    public void ActualizarPresentaLesionActual(boolean estado) {
        this.presentaLesionActual = estado;
    }
    public boolean mostrarPresentaLesionActual() {
        return this.presentaLesionActual;
    }
    
}
