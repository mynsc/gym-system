package src.com.unmsm.gym.models;

public class Regular extends Estudiante {
    /* Constructor */
    public Regular(
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
                presentaLesionActual);
    }
}
