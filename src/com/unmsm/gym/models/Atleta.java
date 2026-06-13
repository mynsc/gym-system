package src.com.unmsm.gym.models;

import src.com.unmsm.gym.enums.Deportes;

public class Atleta extends Usuario {
    private Deportes deporte;


    public Atleta(
        String nombre, 
        String apellido, 
        String codigo_estudiante, 
        String facultad, 
        String carrera, 
        String baseInicio,
        Deportes deporte) {

        super(nombre, apellido, codigo_estudiante, facultad, carrera, baseInicio);
        this.deporte = deporte;
    }

    public void mostrarInformacionPersonal() {
        super.mostrarInformacionPersonal();
        System.out.println("Deporte:              " + deporte);
    }

    
}
