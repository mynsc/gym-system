package src.com.unmsm.gym.models;

import java.time.LocalDate;

public class Persona {
    /* Atributos de la clase Persona  */
    private int id;
    private String nombre;
    private String apellido;
    private String nombreDeUsuario;
    private String contrasenia;

    /* Atributos de una posible clase Cliente  */
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

    /* Constructor no finalizado */
    public Persona(
        int id, 
        String nombre, 
        String apellido, 
        String nombreDeUsuario, 
        String contrasenia) {

        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.nombreDeUsuario = nombreDeUsuario;
        this.contrasenia = contrasenia;
    }

    public void mostrarInformacionPersonal() {
        System.out.println("Nombre: " + nombre);
        System.out.println("Apellido: " + apellido);
    }

    public void aumentarCantidadDePenalidades() {
        if (this.cantidadPenalidades >= 3) {
            this.vetadoTemporalmente = true;
            reiniciarCantidadDePenalidades();
        }

        this.cantidadPenalidades++;
    }
    
    public void reiniciarCantidadDePenalidades() {
        this.cantidadPenalidades = 0;
    }

    /*  Getters y setters de la clase Persona  */
    public int obtenerId() { return id; }
    public void establecerId(int id) {  this.id = id; }

    public String obtenerNombre() { return nombre; }
    public void establecerNombre(String nombre) { this.nombre = nombre; }

    public String obtenerApellido() { return apellido; }
    public void establecerApellido(String apellido) { this.apellido = apellido; }

    public String obtenerNombreDeUsuario() { return nombreDeUsuario; }
    public void establecerNombreDeUsuario(String nombreDeUsuario) { this.nombreDeUsuario = nombreDeUsuario; }

    public String obtenerContrasenia() { return contrasenia; }
    public void establecerContrasenia(String contrasenia) { this.contrasenia = contrasenia; }

    /*  Getters y setters de una posible clase Cliente  */
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