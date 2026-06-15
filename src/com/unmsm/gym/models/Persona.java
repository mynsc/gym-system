package src.com.unmsm.gym.models;

public class Persona {
    /*               Atributos               */
    private int id;
    private String nombre;
    private String apellido;
    private String nombreDeUsuario;
    private String contrasenia;    

    /*              Constructor              */
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

    /*           Getters y setters           */
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
}