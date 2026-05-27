package src.com.unmsm.gym.models;

import src.com.unmsm.gym.enums.TipoDiscapacidad;
import src.com.unmsm.gym.enums.NivelDiscapacidad;

public class Discapacitado extends Usuario {
    private TipoDiscapacidad tipoDiscapacidad;
    private NivelDiscapacidad nivelDiscapacidad;

    public Discapacitado(
        String nombre, 
        String apellido, 
        String codigo_estudiante, 
        String facultad, 
        String carrera, 
        String baseInicio,
        TipoDiscapacidad tipoDiscapacidad,
        NivelDiscapacidad nivelDiscapacidad) {

        super(nombre, apellido, codigo_estudiante, facultad, carrera, baseInicio);
        this.tipoDiscapacidad = tipoDiscapacidad;
        this.nivelDiscapacidad = nivelDiscapacidad;
    }

    public void mostrarInformacionPersonal() {
        super.mostrarInformacionPersonal();
        System.out.println("Tipo de Discapacidad: " + tipoDiscapacidad);
        System.out.println("Nivel de Discapacidad: " + nivelDiscapacidad);
    }
    
}
