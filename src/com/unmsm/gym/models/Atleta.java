package src.com.unmsm.gym.models;

public class Atleta extends Persona {
    private String deporte;

    public Atleta(
        int id, 
        String nombre, 
        String apellido, 
        String nombreDeUsuario, 
        String contrasenia, 
        String deporte) {

        super(id, nombre, apellido, nombreDeUsuario, contrasenia);
        this.deporte = deporte;
    }

    public void mostrarInformacionPersonal() {
        super.mostrarInformacionPersonal();
        System.out.println("Deporte: " + deporte);
    }
}
