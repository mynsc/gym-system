package src.com.unmsm.gym.models;

import src.com.unmsm.gym.enums.NivelDeDiscapacidad;
import src.com.unmsm.gym.enums.TipoDeDiscapacidad;

public class Discapacitado extends Estudiante {
    /* Atributos */
    private TipoDeDiscapacidad tipoDeDiscapacidad;
    private NivelDeDiscapacidad nivelDeDiscapacidad;

    /* Constructor */
    public Discapacitado(
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
            TipoDeDiscapacidad tipoDeDiscapacidad,
            NivelDeDiscapacidad nivelDeDiscapacidad) {

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

        this.tipoDeDiscapacidad = tipoDeDiscapacidad;
        this.nivelDeDiscapacidad = nivelDeDiscapacidad;
    }

    public void mostrarInformacionPersonal() {
        super.mostrarInformacionPersonal();
        System.out.println("Tipo de Discapacidad: " + tipoDeDiscapacidad);
        System.out.println("Nivel de Discapacidad: " + nivelDeDiscapacidad);
    }

    /* Getters y setters */
    public TipoDeDiscapacidad obtenerTipoDeDiscapacidad() {
        return tipoDeDiscapacidad;
    }

    public void establecerTipoDeDiscapacidad(TipoDeDiscapacidad tipoDeDiscapacidad) {
        this.tipoDeDiscapacidad = tipoDeDiscapacidad;
    }

    public NivelDeDiscapacidad obtenerNivelDeDiscapacidad() {
        return nivelDeDiscapacidad;
    }

    public void establecerNivelDeDiscapacidad(NivelDeDiscapacidad nivelDeDiscapacidad) {
        this.nivelDeDiscapacidad = nivelDeDiscapacidad;
    }
}
