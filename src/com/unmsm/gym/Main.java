package src.com.unmsm.gym;

import java.sql.Connection;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

import src.com.unmsm.gym.config.conexion;
import src.com.unmsm.gym.db.db_usuarios;
import src.com.unmsm.gym.db.db_estudiantes;
import src.com.unmsm.gym.db.db_atletas;
import src.com.unmsm.gym.db.db_discapacitados;
import src.com.unmsm.gym.enums.NivelDeDiscapacidad;
import src.com.unmsm.gym.enums.TipoDeDiscapacidad;
import src.com.unmsm.gym.models.Administrador;
import src.com.unmsm.gym.models.Atleta;
import src.com.unmsm.gym.models.Discapacitado;
import src.com.unmsm.gym.models.Estudiante;
import src.com.unmsm.gym.models.Persona;
import src.com.unmsm.gym.models.Regular;
import src.com.unmsm.gym.models.Horarios;
import src.com.unmsm.gym.models.Reserva;

public class Main {

    static Scanner scanner = new Scanner(System.in);
    static List<Persona> usuarios = new ArrayList<>();
    static List<Horarios> horariosInformacion = new ArrayList<>();
    static List<Reserva> reservas = new ArrayList<>();

    public static void main(String args[]) {
        try {
            Connection con = conexion.conectar();
            System.out.println("Conexion exitosa");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al conectar a la base de datos");
        }

        usuarios.add(new Regular(0, "Juan", "Tapia", "estudiante", "123456", "FISI", "Ingenieria de Software", "B25", true, true, false));
        usuarios.add(new Atleta(1, "Lucas", "Sanchez", "atleta", "123456", "FISI", "Ingenieria de Software", "B26", true, true, false, "Basquetbol"));
        usuarios.add(new Discapacitado(2, "Maria", "Velez", "discapacitado", "123456", "FII", "Ingenieria Industrial", "B20", true, true, true, TipoDeDiscapacidad.AUDITIVA, NivelDeDiscapacidad.MODERADO));
        usuarios.add(new Administrador(3, "Luciana", "Vega", "administrador", "123456"));

        for (int i = 8; i < 20; i++) {
            if (i != 12 && i != 13) {
                Horarios nuevoHorario = new Horarios(i - 8, LocalTime.of(i, 0), 25, 0);
                horariosInformacion.add(nuevoHorario);
            }
        }

        int opcion = 0;
        do {
            limpiarPantalla();
            System.out.println("=== GIMNASIO UNMSM ===");
            System.out.println("1. Iniciar sesion");
            System.out.println("2. Registrarse");
            System.out.println("3. Salir del sistema");
            System.out.print("Ingresar opcion >> ");
            opcion = scanner.nextInt();
            scanner.nextLine();

            limpiarPantalla();
            switch (opcion) {
                case 1: {
                    int intentos = 0;
                    Persona usuario = null;

                    do {
                        String nombreDeUsuario = leerNoVacio("Usuario >> ");
                        String contrasenia = leerNoVacio("Contrasena >> ");

                        for (Persona usuarioEncontrado : usuarios) {
                            if (usuarioEncontrado.obtenerNombreDeUsuario().equals(nombreDeUsuario)
                                    && usuarioEncontrado.obtenerContrasenia().equals(contrasenia)) {
                                usuario = usuarioEncontrado;
                            }
                        }

                        if (usuario == null) {
                            intentos++;
                            if (intentos < 3) {
                                System.out.println("(!) Credenciales invalidas [Quedan " + (3 - intentos) + " intentos]");
                            }
                        }
                    } while (usuario == null && intentos < 3);

                    if (usuario == null) {
                        System.out.println("(!) Maximo de intentos, volviendo al menu principal");
                        delay(2);
                        break;
                    }

                    db_atletas atletas_table = new db_atletas();
                    db_usuarios usuarios_table = new db_usuarios();
                    db_discapacitados discapacitados_table = new db_discapacitados();
                    db_estudiantes estudiantes_table = new db_estudiantes();

                    usuarios_table.create(usuario.obtenerNombre(), usuario.obtenerApellido(), usuario.obtenerNombreDeUsuario(), usuario.obtenerContrasenia(), "FISI", "Ingenieria de Software", "B25", true, true);

                    if (usuario instanceof Estudiante) {
                        estudiantes_table.create(usuario.obtenerNombre(), usuario.obtenerApellido(), usuario.obtenerNombreDeUsuario(), usuario.obtenerContrasenia(), "FISI", "Ingenieria de Software", "B25", true, true);
                        menuDeEstudianteRegular((Regular) usuario);
                    }
                    if (usuario instanceof Discapacitado) {
                        discapacitados_table.create(usuario.obtenerNombre(), usuario.obtenerApellido(), usuario.obtenerNombreDeUsuario(), usuario.obtenerContrasenia(), "FISI", "Ingenieria de Software", "B25", "Motriz", "Moderada", true, true);
                        menuDeEstudianteRegular((Regular) usuario);
                    }
                    if (usuario instanceof Atleta) {
                        atletas_table.create(usuario.obtenerNombre(), usuario.obtenerApellido(), usuario.obtenerNombreDeUsuario(), usuario.obtenerContrasenia(), "FISI", "Ingenieria de Software", "B25", "Basquetbol", true, true);
                        menuDeAtleta((Atleta) usuario);
                    }
                    if (usuario instanceof Administrador) {
                        menuDeAdministrador((Administrador) usuario);
                    }
                    break;
                }
                case 2:
                    System.out.println("=== REGISTRO DE NUEVO ESTUDIANTE ===");
                    System.out.println("<< Selecciona tu perfil >>");
                    System.out.println("1. Estudiante regular");
                    System.out.println("2. Atleta universitario");
                    System.out.println("3. Estudiante con discapacidad");
                    System.out.print("Ingresar opcion >> ");
                    int tipoPerfil = scanner.nextInt();
                    scanner.nextLine();

                    if (tipoPerfil < 1 || tipoPerfil > 3) {
                        System.out.println("(!) Opcion invalida");
                        break;
                    }

                    int nuevoId = usuarios.size();
                    String nombre = leerNoVacio("Nombre >> ");
                    String apellido = leerNoVacio("Apellido >> ");
                    String nombreDeUsuario = leerNoVacio("Nombre de usuario >> ");
                    String contrasenia = leerNoVacio("Contrasena >> ");
                    String facultad = leerNoVacio("Facultad >> ");
                    String carrera = leerNoVacio("Carrera >> ");
                    String baseInicio = leerNoVacio("Base de ingreso >> ");

                    System.out.print("Tienes autoseguro activo (1 = Si, 0 = No) >> ");
                    boolean seguroActivo = scanner.nextInt() == 1;
                    System.out.print("Estas matriculado en el semestre actual (1 = Si, 0 = No) >> ");
                    boolean matriculado = scanner.nextInt() == 1;
                    System.out.print("Presentas alguna lesion fisica actual (1 = Si, 0 = No) >> ");
                    boolean lesionado = scanner.nextInt() == 1;
                    scanner.nextLine();

                    switch (tipoPerfil) {
                        case 1:
                            Regular nuevoRegular = new Regular(nuevoId, nombre, apellido, nombreDeUsuario, contrasenia, facultad, carrera, baseInicio, seguroActivo, matriculado, lesionado);
                            usuarios.add(nuevoRegular);
                            System.out.println("(+) Registro exitoso, bienvenido, " + nombre);
                            break;
                        case 2:
                            String deporte = leerNoVacio("Deporte que practicas >> ");
                            Atleta nuevoAtleta = new Atleta(nuevoId, nombre, apellido, nombreDeUsuario, contrasenia, facultad, carrera, baseInicio, seguroActivo, matriculado, lesionado, deporte);
                            usuarios.add(nuevoAtleta);
                            System.out.println("(+) Registro de atleta exitoso, bienvenido, " + nombre);
                            break;
                        case 3:
                            System.out.println("Tipos de discapacidad: FISICA, AUDITIVA, VISUAL, INTELECTUAL, OTRA");
                            String tipoDiscStr = leerNoVacio("Ingresa el tipo >> ").toUpperCase();
                            System.out.println("Niveles: LEVE, MODERADO, GRAVE");
                            String nivelDiscStr = leerNoVacio("Ingresa el nivel >> ").toUpperCase();
                            try {
                                TipoDeDiscapacidad tipoDisc = TipoDeDiscapacidad.valueOf(tipoDiscStr);
                                NivelDeDiscapacidad nivelDisc = NivelDeDiscapacidad.valueOf(nivelDiscStr);
                                Discapacitado nuevoDiscapacitado = new Discapacitado(nuevoId, nombre, apellido, nombreDeUsuario, contrasenia, facultad, carrera, baseInicio, seguroActivo, matriculado, lesionado, tipoDisc, nivelDisc);
                                usuarios.add(nuevoDiscapacitado);
                                System.out.println("(!) Registro exitoso, bienvenido, " + nombre);
                            } catch (IllegalArgumentException e) {
                                System.out.println("(!) Error, el tipo o nivel ingresado no coincide con los registros");
                            }
                            break;
                    }
                    break;
                case 3:
                    System.out.println("Saliendo del sistema...");
                    delay(2);
                    break;
                default:
                    System.out.println("(!) Opcion invalida, intente de nuevo");
                    delay(2);
                    break;
            }
        } while (opcion != 3);
    }

    private static void menuDeAdministrador(Administrador usuario) {
        int opcion = 0;
        do {
            limpiarPantalla();
            System.out.println("=== MENU ADMINISTRADOR ===");
            System.out.println("1. Ver reporte de estudiantes activos");
            System.out.println("2. Ver reporte de horarios mas concurridos");
            System.out.println("3. Modificar aforo de un bloque de horario");
            System.out.println("4. Ver lista de alumnos penalizados");
            System.out.println("5. Revocar penalidad a un alumno");
            System.out.println("6. Cerrar sesion");
            System.out.print("Ingresar opcion >> ");
            opcion = scanner.nextInt();
            scanner.nextLine();
            limpiarPantalla();

            switch (opcion) {
                case 1:
                    System.out.println("=== ESTUDIANTES ACTIVOS ===");
                    usuarios.stream()
                            .filter(p -> p instanceof Estudiante)
                            .map(p -> (Estudiante) p)
                            .forEach(estudiante -> {
                                if (!estudiante.estaVetadoTemporalmente()) {
                                    System.out.println("------------------------------------------");
                                    estudiante.mostrarInformacionPersonal();
                                    System.out.println("------------------------------------------");
                                }
                            });
                    break;
                case 2:
                    System.out.println("=== HORARIOS MAS CONCURRIDOS ===");
                    IntStream.range(0, horariosInformacion.size())
                            .boxed()
                            .sorted((a, b) -> Integer.compare(
                                    horariosInformacion.get(b).getCantVisitas(),
                                    horariosInformacion.get(a).getCantVisitas()))
                            .forEach(i -> System.out.println(
                                    horariosInformacion.get(i).getHora() + "-"
                                    + horariosInformacion.get(i).getHora().plusHours(1) + " | "
                                    + horariosInformacion.get(i).getCantVisitas() + " visitas"));
                    delay(2);
                    break;
                case 3:
                    int opcionHorario = 0;
                    mostrarHorarios();
                    do {
                        System.out.print("Ingresar opcion >> ");
                        opcionHorario = scanner.nextInt();
                        scanner.nextLine();
                        if (opcionHorario < 1 || opcionHorario > horariosInformacion.size()) {
                            System.out.println("(!) Opcion invalida, intente de nuevo");
                        }
                    } while (opcionHorario < 1 || opcionHorario > horariosInformacion.size());

                    int indiceHorario = opcionHorario - 1;
                    int nuevoAforo = 0;
                    do {
                        System.out.print("Ingresar nuevo aforo >> ");
                        nuevoAforo = scanner.nextInt();
                        scanner.nextLine();
                        if (nuevoAforo < 1) {
                            System.out.println("(!) Cantidad invalida, intente de nuevo");
                        }
                    } while (nuevoAforo < 1);

                    horariosInformacion.get(indiceHorario).setCupos(nuevoAforo);
                    System.out.println("Aforo actualizado correctamente");
                    delay(2);
                    break;
                case 4:
                    System.out.println("=== ESTUDIANTES PENALIZADOS ===");
                    usuarios.stream()
                            .filter(p -> p instanceof Estudiante)
                            .map(p -> (Estudiante) p)
                            .forEach(estudiante -> {
                                if (estudiante.presentaPenalidades()) {
                                    System.out.println("------------------------------------------");
                                    estudiante.mostrarInformacionPersonal();
                                    System.out.println("------------------------------------------");
                                }
                            });
                    break;
                case 5:
                    usuarios.stream()
                            .filter(p -> p instanceof Estudiante)
                            .map(p -> (Estudiante) p)
                            .forEach(estudiante -> {
                                if (estudiante.presentaPenalidades()) {
                                    System.out.println(estudiante.obtenerId() + " | " + estudiante.obtenerNombre() + " | " + estudiante.obtenerCantidadPenalidades());
                                }
                            });

                    int idIngresado = 0;
                    Estudiante estudianteEncontrado = null;
                    do {
                        System.out.print("Ingresar el ID de un usuario >> ");
                        idIngresado = scanner.nextInt();
                        scanner.nextLine();
                        try {
                            estudianteEncontrado = (Estudiante) usuarios.get(idIngresado);
                        } catch (IndexOutOfBoundsException e) {
                            System.err.println("(!) ID no encontrado, intente de nuevo");
                            continue;
                        }
                        if (!estudianteEncontrado.presentaPenalidades()) {
                            System.out.println("(!) Este usuario no tiene penalidades, intente de nuevo");
                        }
                    } while (estudianteEncontrado == null || !estudianteEncontrado.presentaPenalidades());

                    short descuentoPenalidades = 0;
                    do {
                        System.out.print("Cantidad de penalidades a descontar (0 - 3) >> ");
                        descuentoPenalidades = scanner.nextShort();
                        scanner.nextLine();
                        if (descuentoPenalidades < 0 || descuentoPenalidades > 3) {
                            System.out.println("(!) Cantidad invalida, intente de nuevo");
                        }
                    } while (descuentoPenalidades < 0 || descuentoPenalidades > 3);

                    if (descuentoPenalidades == 0) {
                        System.out.println("No se redujo la penalidad");
                        break;
                    }
                    if (descuentoPenalidades == estudianteEncontrado.obtenerCantidadPenalidades()) {
                        estudianteEncontrado.establecerEstadoDePenalidades(false);
                    }
                    estudianteEncontrado.establecerVetoTemporal(false);
                    short nuevaCantidadDePenalidades = estudianteEncontrado.obtenerCantidadPenalidades();
                    nuevaCantidadDePenalidades -= descuentoPenalidades;
                    estudianteEncontrado.establecerCantidadPenalidades(nuevaCantidadDePenalidades);
                    System.out.println("Reduccion de penalidad exitosa");
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

    private static void menuDeAtleta(Atleta atleta) {
        int opcion = 0;
        do {
            limpiarPantalla();
            System.out.println("=== MENU ESTUDIANTE ===");
            System.out.println("Bienvenido, " + atleta.obtenerNombre() + ". Tienes " + atleta.obtenerNumeroDePuntos() + " punto(s) - Nivel " + atleta.obtenerNivel());
            System.out.println("1. Reservar turno");
            System.out.println("2. Cancelar reserva");
            System.out.println("3. Ver y editar mi rutina");
            System.out.println("4. Registrar ingreso");
            System.out.println("5. Ver mis logros desbloqueados");
            System.out.println("6. Cerrar sesion");
            System.out.println("7. Registrar horas de entrenamiento");
            System.out.print("Ingresar opcion >> ");
            opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    int opcionHorario = 0;
                    mostrarHorarios();
                    do {
                        System.out.print("Ingresar opcion >> ");
                        opcionHorario = scanner.nextInt();
                        scanner.nextLine();
                        if (opcionHorario < 1 || opcionHorario > horariosInformacion.size()) {
                            System.out.println("(!) Opcion invalida, intente de nuevo");
                        }
                    } while (opcionHorario < 1 || opcionHorario > horariosInformacion.size());
                    atleta.reservarTurno(opcionHorario, horariosInformacion, reservas);
                    break;
                case 2:
                    atleta.cancelarReserva(horariosInformacion, reservas);
                    break;
                case 3:
                    atleta.menuRutinas();
                    break;
                case 4:
                    atleta.registrarIngreso(horariosInformacion, reservas);
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

    private static void menuDeEstudianteRegular(Regular estudianteRegular) {
        int opcion = 0;
        do {
            limpiarPantalla();
            System.out.println("=== MENU ESTUDIANTE ===");
            System.out.println("Bienvenido, " + estudianteRegular.obtenerNombre() + ". Tienes " + estudianteRegular.obtenerNumeroDePuntos() + " punto(s) - Nivel " + estudianteRegular.obtenerNivel());
            System.out.println("1. Reservar turno");
            System.out.println("2. Cancelar reserva");
            System.out.println("3. Ver y editar mi rutina");
            System.out.println("4. Registrar ingreso");
            System.out.println("5. Ver mis logros desbloqueados");
            System.out.println("6. Cerrar sesion");
            System.out.print("Ingresar opcion >> ");
            opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    int opcionHorario = 0;
                    mostrarHorarios();
                    do {
                        System.out.print("Ingresar opcion >> ");
                        opcionHorario = scanner.nextInt();
                        scanner.nextLine();
                        if (opcionHorario < 1 || opcionHorario > horariosInformacion.size()) {
                            System.out.println("(!) Opcion invalida, intente de nuevo");
                        }
                    } while (opcionHorario < 1 || opcionHorario > horariosInformacion.size());
                    estudianteRegular.reservarTurno(opcionHorario, horariosInformacion, reservas);
                    break;
                case 2:
                    estudianteRegular.cancelarReserva(horariosInformacion, reservas);
                    break;
                case 3:
                    estudianteRegular.menuRutinas();
                    break;
                case 4:
                    estudianteRegular.registrarIngreso(horariosInformacion, reservas);
                    break;
                case 5:
                    System.out.println("Mostrando logros desbloqueados...");
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

    private static void mostrarHorarios() {
        System.out.println("=== HORARIOS DISPONIBLES ===");
        for (int i = 0; i < horariosInformacion.size(); i++) {
            System.out.println((i + 1) + ". " + horariosInformacion.get(i).getHora() + "-"
                    + horariosInformacion.get(i).getHora().plusHours(1) + " | Aforo: "
                    + horariosInformacion.get(i).getCupos());
        }
    }

    private static String leerNoVacio(String textoIngresado) {
        while (true) {
            System.out.print(textoIngresado);
            String linea = scanner.nextLine().trim();
            if (!linea.isEmpty()) {
                return linea;
            }
            limpiarPantalla();
            System.out.println("(!) El valor no puede estar vacio");
        }
    }

    public static void limpiarPantalla() {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (Exception e) {
            System.out.println("No se pudo limpiar la pantalla");
        }
    }

    public static void delay(int segundos) {
        try {
            Thread.sleep(segundos * 1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}