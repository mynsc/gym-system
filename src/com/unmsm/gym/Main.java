package src.com.unmsm.gym;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import src.com.unmsm.gym.enums.NivelDeDiscapacidad;
import src.com.unmsm.gym.enums.TipoDeDiscapacidad;
import src.com.unmsm.gym.models.Administrador;
import src.com.unmsm.gym.models.Atleta;
import src.com.unmsm.gym.models.Discapacitado;
import src.com.unmsm.gym.models.Estudiante;
import src.com.unmsm.gym.models.Persona;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    static List<Persona> usuarios = new ArrayList<>();

    public static void main(String args[]) {
        usuarios.add(new Estudiante(1, "Juan", "Tapia", "estudiante", "123456"));
        usuarios.add(new Atleta(2, "Lucas", "Sanchez", "atleta", "123456", "Basquetbol"));
        usuarios.add(new Discapacitado(3, "Maria", "Velez", "discapacitado", "123456", TipoDeDiscapacidad.AUDITIVA, NivelDeDiscapacidad.MODERADO));
        usuarios.add(new Administrador(4, "Luciana", "Vega", "administrador", "123456"));

        int opcion = 0;
        do {
            System.out.println("=== GIMNASIO UNMSM ===");
            System.out.println("1. Iniciar sesion");
            System.out.println("2. Registrarse");
            System.out.println("3. Salir del sistema");
            System.out.println("Ingresar opcion >>");
            opcion = scanner.nextInt();

            switch (opcion) {
                case 1: {
                    int intentos = 0;
                    Persona usuario = null;

                    /* Login */
                    do {
                        String nombreDeUsuario = leerNoVacio("Usuario >> ");
                        String contrasenia = leerNoVacio("Contrasena >> ");
                        
                        // busca coincidencias entre el nombre de usuario y la contraseña en el ArrayList
                        for (Persona usuarioEncontrado : usuarios) {
                            if (usuarioEncontrado.obtenerNombreDeUsuario().equals(nombreDeUsuario) && usuarioEncontrado.obtenerContrasenia().equals(contrasenia)) {
                                usuario = usuarioEncontrado;
                            }
                        }

                        // si no hay coincidencias, incrementa los intentos
                        if (usuario == null) {
                            intentos++;
                            if (intentos < 3) {
                                System.out.println("(!) Credenciales invalidas [Quedan " + (3 - intentos) + " intentos]");
                            }
                        }
                    } while (usuario == null && intentos < 3);

                    // si se agotan los intentos, regresa al menu
                    if (usuario == null) {
                        System.out.println("(!) Maximo de intentos, volviendo al menu principal");
                        break;
                    }

                    /* Visualizacion de un menu segun el tipo de usuario */
                    if (usuario instanceof Administrador) {
                        menuDeAdministrador((Administrador) usuario);
                        break;
                    }

                    if (usuario instanceof Atleta) {
                        menuDeAtleta((Atleta) usuario);
                        break;
                    }

                    menuDeEstudiante((Estudiante) usuario);
                }
                case 2:
                    break;
                case 3:
                    System.out.println("Saliendo del sistema...");
                    break;
                default:
                    System.out.println("(!) Opcion invalida, intente de nuevo");
                    break;
            }
        } while (opcion != 3);
    }

    private static void menuDeAdministrador(Administrador usuario) {
        int opcion;
        do {
            System.out.println("=== MENU ADMINISTRADOR ===");
            System.out.println("Panel de Control - Gimnasio Central");
            System.out.println("1. Ver Reporte de Usuarios Activos");
            System.out.println("2. Ver Reporte de Horarios mas concurridos");
            System.out.println("3. Modificar Aforo de un Bloque Horario");
            System.out.println("4. Ver lista de alumnos penalizados");
            System.out.println("5. Revocar penalidad a un alumno");
            System.out.println("6. Cerrar Sesion");
            System.out.println("Ingresar opcion >>");
            opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    break;
                case 6:
                    System.out.println("Cerrando sesion...");
                    break;
                default:
                    System.out.println("(!) Opcion invalida, intente de nuevo");
                    break;
            }
        } while (opcion != 6);
    }

    private static void menuDeAtleta(Atleta usuario) {
        int opcion;
        do {
            System.out.println("=== MENU ESTUDIANTE ===");
            System.out.println("Bienvenido, " + usuario.obtenerNombre() + ". Tienes " + 1 + " punto(s) (Nivel " +  0);
            System.out.println("1. Ver Horarios y Reservar Turno");
            System.out.println("2. Cancelar una Reserva activa");
            System.out.println("3. Mi Rutina (Ver/Editar Ejercicios)");
            System.out.println("4. Simular paso por Torniquete (Check-In)");
            System.out.println("5. Ver mis Logros Desbloqueados");
            System.out.println("6. Cerrar Sesion");
            System.out.println("7. Registrar horas de entrenamiento");
            System.out.println("Ingresar opcion >>");
            opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    System.out.println("Mostrando rutina...");
                    break;
                case 4:
                    break;
                case 5:
                    System.out.println("Mostrando logros desbloqueados...");
                    break;
                case 6:
                    System.out.println("Cerrando sesion...");
                    break;
                case 7:
                    System.out.println("Horas de entrenamiento registradas.");
                    break;
                default:
                    System.out.println("(!) Opcion invalida, intente de nuevo");
                    break;
            }
        } while (opcion != 6);
    }

    private static void menuDeEstudiante(Estudiante usuario) {
    }

    private static String leerNoVacio(String textoIngresado) {
        while (true) {
            System.out.print(textoIngresado);
            String linea = scanner.nextLine().trim();
            if (!linea.isEmpty()) {
                return linea;
            }

            System.out.println("(!) El valor no puede estar vacio");
        }
    }
}