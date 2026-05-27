package src.com.unmsm.gym.models;





public class Atleta extends Usuario {
    private String deporte;


    public Atleta(
        String nombre, 
        String apellido, 
        String codigo_estudiante, 
        String facultad, 
        String carrera, 
        String baseInicio,
        String deporte) {

        super(nombre, apellido, codigo_estudiante, facultad, carrera, baseInicio);
        this.deporte = deporte;
    }

    public void mostrarInformacionPersonal() {
        super.mostrarInformacionPersonal();
        System.out.println("Deporte:              " + deporte);
    }

    
}
