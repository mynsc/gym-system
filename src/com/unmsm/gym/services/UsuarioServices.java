package src.com.unmsm.gym.services;

import java.util.Scanner;

import src.com.unmsm.gym.models.Usuario;

public class UsuarioServices {
    Scanner scanner = new Scanner(System.in);

    // Obtenemos los datos del usuario para clasificarlo como Atleta o Discapacitado
    public Usuario UsuarioDto() {
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Apellidos: ");
        String apellidos = scanner.nextLine();
        System.out.print("Código de Estudiante: ");
        String codigo_estudiante = scanner.nextLine();
        System.out.print("Facultad: ");
        String facultad = scanner.nextLine();
        System.out.print("Carrera: ");
        String carrera = scanner.nextLine();
        System.out.print("Base de Inicio: ");
        String baseInicio = scanner.nextLine();

        Usuario nuevoUsuario = new Usuario(nombre, apellidos, codigo_estudiante, facultad, carrera, baseInicio);
        return nuevoUsuario;

    }


}
