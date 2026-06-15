package src.com.unmsm.gym.models;

public class Atleta extends Estudiante {
    /*                Atributo                */
    private String deporte;

    /*              Constructor              */
    public Atleta(
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
        boolean presentaLesionActual, 
        String deporte) {

        super(
            id, 
            nombre, 
            apellido, 
            nombreDeUsuario, 
            contrasenia, 
            facultad, 
            carrera, 
            baseInicio, 
            autoseguroActivo, 
            matriculadoSemestreActual, 
            presentaLesionActual
        );
        this.deporte = deporte;
    }

    public void mostrarInformacionPersonal() {
        super.mostrarInformacionPersonal();
        System.out.println("Deporte: " + deporte);
    }

    /*           Getters y setters           */
    public String obtenerDeporte() { return deporte; }
    public void establecerDeporte(String deporte) { this.deporte = deporte; }
}
