package src.com.unmsm.gym.models;

import src.com.unmsm.gym.enums.NivelDeDiscapacidad;
import src.com.unmsm.gym.enums.TipoDeDiscapacidad;

public class Discapacitado extends Persona {
    private TipoDeDiscapacidad tipoDeDiscapacidad;
    private NivelDeDiscapacidad nivelDeDiscapacidad;

    public Discapacitado(
        int id, 
        String nombre, 
        String apellido, 
        String nombreDeUsuario, 
        String contrasenia, 
        TipoDeDiscapacidad tipoDeDiscapacidad,
        NivelDeDiscapacidad nivelDeDiscapacidad) {

        super(id, nombre, apellido, nombreDeUsuario, contrasenia);
        this.tipoDeDiscapacidad = tipoDeDiscapacidad;
        this.nivelDeDiscapacidad = nivelDeDiscapacidad;
    }

    public void mostrarInformacionPersonal() {
        super.mostrarInformacionPersonal();
        System.out.println("Tipo de Discapacidad: " + tipoDeDiscapacidad);
        System.out.println("Nivel de Discapacidad: " + nivelDeDiscapacidad);
    }
}
