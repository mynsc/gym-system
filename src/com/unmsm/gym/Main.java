package src.com.unmsm.gym;

import java.sql.Connection;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

import src.com.unmsm.gym.models.Administrador;
import src.com.unmsm.gym.models.Estudiante;
import src.com.unmsm.gym.models.Persona;

public class Main {
    public record HorarioCuposVisitas(LocalTime hora, Integer cupos, Integer cantidadDeVisitas) {
    }

    static Scanner scanner = new Scanner(System.in);
    static List<Persona> usuarios = new ArrayList<>();                         // lista de usuarios
    static List<HorarioCuposVisitas> horariosInformacion = new ArrayList<>();  // lista de horario, aforo y veces que se ha visitado cada horario
    static List<List<Integer>> reservas = new LinkedList<>();                  // lista de listas (ID - horario reservado)

    public static void main(String args[]) {
        usuarios.add(new Estudiante(
                "Juan",
                "Tapia",
                "estudiante",
                "123456",
                "Regular",
                "FISI",
                "Ingenieria de Software",
                "B25",
                true,
                true,
                false
            ));
        usuarios.add(new Estudiante(
                "Lucas",
                "Valdez",
                "atleta",
                "123456",
                "Atleta",
                "FII",
                "Ingenieria Industrial",
                "B23",
                true,
                true,
                false
            ));
        usuarios.add(new Estudiante(
                "Luna",
                "Velez",
                "discapacitado",
                "123456",
                "Discapacitado",
                "FIEE",
                "Ingenieria Electronica",
                "B19",
                true,
                true,
                false
            ));
        usuarios.add(new Administrador("Luciana", "Vega", "administrador", "123456"));

        // fijar horarios desde las 8 hasta las 20 horas
        for (int i = 8; i < 20; i++) {
            // hora de almuerzo desde las 12 hasta las 14 horas
            if (i != 12 && i != 13) {
                // tambien se inicializa el aforo y las veces que se ha visitado un turno
                HorarioCuposVisitas nuevoHorarioInformacion = new HorarioCuposVisitas(LocalTime.of(i, 0), 25, 0);

                horariosInformacion.add(nuevoHorarioInformacion);
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

            switch (opcion) {
                case 1: {
                    limpiarPantalla();
                    /* Login */
                    int intentos = 0;
                    Persona usuario = null;

                    do {
                        String nombreDeUsuario = leerNoVacio("Usuario >> ");
                        String contrasenia = leerNoVacio("Contrasena >> ");

                        db_usuarios usuarios_table = new db_usuarios();
                        // insertar el nuevo usuario en la base de datos1

                        // busca coincidencias entre el nombre de usuario y la contraseña en el
                        // ArrayList
                        for (Persona usuarioEncontrado : usuarios) {
                            if (usuarioEncontrado.obtenerNombreDeUsuario().equals(nombreDeUsuario)
                                    && usuarioEncontrado.obtenerContrasenia().equals(contrasenia)) {
                                usuario = usuarioEncontrado;
                                
                                usuarios_table.create(
                                        usuario.obtenerNombre(),
                                        usuario.obtenerApellido(),
                                        usuario.obtenerNombreDeUsuario(),
                                        usuario.obtenerContrasenia(),
                                        "FISI",
                                        "Ingenieria de Software",
                                        "B25",
                                        true,
                                        true);

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
                        System.out.print("(!) Maximo de intentos, volviendo al menu principal");
                        delay(2);
                        break;
                    }

                    /* Visualizacion de un menu segun el tipo de usuario */
                    if (usuario instanceof Administrador administrador) {
                        menuDeAdministrador(administrador);
                        break;
                    }

                    if (usuario instanceof Estudiante atleta && atleta.obtenerTipoDeEstudiante() == "Atleta") {
                        menuDeAtleta(atleta);
                        break;
                    }

                    if (usuario instanceof Estudiante estudiante) {
                        menuDeEstudianteRegular(estudiante);
                        break;
                    }
                }
                case 2:
                    limpiarPantalla();
                    /* Registro */
                    System.out.println("=== REGISTRO DE NUEVO ESTUDIANTE ===");
                    System.out.println("<< Selecciona tu perfil >>");
                    System.out.println("1. Estudiante regular");
                    System.out.println("2. Atleta universitario");
                    System.out.println("3. Estudiante con discapacidad");
                    System.out.print("Ingresar opcion >> ");
                    int tipoPerfil = scanner.nextInt();
                    scanner.nextLine();

                    if (tipoPerfil < 1 || tipoPerfil > 3) {
                        System.out.print("(!) Opcion invalida, regresando al menu de inicio");
                        delay(2);
                        break;
                    }
                    
                    limpiarPantalla();

                    // pedir atributos comunes de clase Estudiante
                    // el ID se genera automaticamente basado en el tamaño de la lista
                    int nuevoId = usuarios.size(); 
                    
                    String nombre = leerNoVacio("Nombre >> ");
                    String apellido = leerNoVacio("Apellido >> ");
                    String nombreDeUsuario = leerNoVacio("Nombre de usuario >> ");
                    String contrasenia = leerNoVacio("Contrasena >> ");
                    String facultad = leerNoVacio("Facultad >> ");
                    String carrera = leerNoVacio("Carrera >> ");
                    String baseInicio = leerNoVacio("Base de ingreso >> ");

                    // atributos booleanas
                    System.out.print("Tienes autoseguro activo (1 = Si, 0 = No) >> ");
                    boolean autoseguroActivo = scanner.nextInt() == 1;

                    System.out.print("Estas matriculado en el semestre actual (1 = Si, 0 = No) >> ");
                    boolean matriculadoSemestreActual = scanner.nextInt() == 1;

                    System.out.print("Presentas alguna lesion fisica (1 = Si, 0 = No) >> ");
                    boolean presentaLesion = scanner.nextInt() == 1;
                    scanner.nextLine();

                    // crear el objeto segun el tipo de Estudiante y pedir atributos especificos
                    switch (tipoPerfil) {
                        case 1:
                            /*               REGULAR                */
                            Estudiante nuevoRegular = new Estudiante(
                                nombre, 
                                apellido, 
                                nombreDeUsuario, 
                                contrasenia, 
                                "Regular", 
                                facultad, 
                                carrera, 
                                baseInicio, 
                                autoseguroActivo, 
                                matriculadoSemestreActual, 
                                presentaLesion
                            );

                            usuarios.add(nuevoRegular);
                            System.out.print("(!) Registro exitoso, bienvenido, " + nombre);
                            delay(2);
                            break;

                        case 2:
                            /*               ATLETA                */
                            String deporte = leerNoVacio("Deporte que practicas >> ");
                            
                            Estudiante nuevoAtleta = new Estudiante(
                                nombre, 
                                apellido, 
                                nombreDeUsuario, 
                                contrasenia, 
                                "Atleta", 
                                facultad, 
                                carrera, 
                                baseInicio, 
                                autoseguroActivo, 
                                matriculadoSemestreActual, 
                                presentaLesion
                            );
                            nuevoAtleta.establecerDeporte(deporte);

                            usuarios.add(nuevoAtleta);
                            System.out.print("(!) Registro de atleta exitoso, bienvenido, " + nombre);
                            delay(2);
                            break;

                        case 3:
                            /*               DISCAPACITADO                */
                            System.out.println("Tipos de discapacidad: FISICA, AUDITIVA, VISUAL, INTELECTUAL, OTRA");
                            String tipoDeDiscapacidad = leerNoVacio("Ingresa el tipo >> ");
                            
                            System.out.println("Niveles: LEVE, MODERADO, GRAVE");
                            String nivelDeDiscapacidad = leerNoVacio("Ingresa el nivel >> ");
                           
                            Estudiante nuevoDiscapacitado = new Estudiante(
                                nombre, 
                                apellido, 
                                nombreDeUsuario, 
                                contrasenia, 
                                "Discapacitado", 
                                facultad, 
                                carrera, 
                                baseInicio, 
                                autoseguroActivo, 
                                matriculadoSemestreActual, 
                                presentaLesion
                            );
                            nuevoDiscapacitado.establecerTipoDeDiscapacidad(tipoDeDiscapacidad);
                            nuevoDiscapacitado.establecerNivelDeDiscapacidad(nivelDeDiscapacidad);

                            usuarios.add(nuevoDiscapacitado);
                            System.out.println("(!) Registro exitoso, bienvenido, " + nombre);
                            delay(2);
                            break;
                    }
                    break;
                case 3:
                    limpiarPantalla();
                    System.out.print("Saliendo del sistema...");
                    delay(3);
                    break;
                default:
                    System.out.print("(!) Opcion invalida, intente de nuevo");
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

            switch (opcion) {
                case 1:
                    limpiarPantalla();
                    System.out.println("=== ESTUDIANTES ACTIVOS ===");

                    boolean existenEstudiantesActivos = usuarios.stream()
                        .filter(p -> p instanceof Estudiante e && !e.presentaPenalidades())
                        .findAny()
                        .isPresent();
                        
                    if (!existenEstudiantesActivos) {
                        System.out.print("(!) No hay estudiantes sin penalidades");
                        delay(2);
                        break;
                    }

                    // listar estudiantes que no hayan llegado al limite de penalidades
                    usuarios.stream()
                        .filter(p -> p instanceof Estudiante)
                        .map(p -> (Estudiante) p)
                        .forEach( estudiante -> {
                            if (!estudiante.estaVetadoTemporalmente()) {
                                estudiante.mostrarInformacionPersonal();
                            }
                        });

                    pausar();
                    break;
                case 2:
                    limpiarPantalla();
                    System.out.println("=== HORARIOS MAS CONCURRIDOS ===");

                    // ordenar horarios establecidos del mas concurrido al menos concurrido
                    IntStream.range(0, horariosInformacion.size())
                        .boxed()
                        .sorted((a, b) -> Integer.compare(horariosInformacion.get(b).cantidadDeVisitas(), horariosInformacion.get(a).cantidadDeVisitas()))
                        .forEach(i -> 
                            System.out.println(horariosInformacion.get(i).hora() + "-" + horariosInformacion.get(i).hora().plusHours(1) + " | " + horariosInformacion.get(i).cantidadDeVisitas() + " visitas")
                        );

                    pausar();
                    break;
                case 3:
                    int opcionHorario = 0;

                    // visualizar horarios y seleccionar uno para modificar su aforo
                    do {
                        limpiarPantalla();
                        mostrarHorarios();
                        System.out.print("Ingresar opcion >> ");
                        opcionHorario = scanner.nextInt();
                        scanner.nextLine();
                        
                        if (opcionHorario < 1 || opcionHorario > horariosInformacion.size()) {
                            System.out.print("(!) Opcion invalida, intente de nuevo");
                            delay(2);
                        }
                    } while (opcionHorario < 1 || opcionHorario > horariosInformacion.size());

                    int indiceHorario = opcionHorario - 1;
                    Integer nuevoAforo = 0;
                    do {
                        System.out.print("Ingresar nuevo aforo >> ");
                        nuevoAforo = scanner.nextInt();
                        scanner.nextLine();

                        if (nuevoAforo < 1) {
                            System.out.println("(!) Cantidad invalida, intente de nuevo");
                        }
                    } while (nuevoAforo < 1);

                    // cambiar aforo en el horario seleccionado
                    HorarioCuposVisitas horarioConNuevoAforo = new HorarioCuposVisitas(
                            horariosInformacion.get(indiceHorario).hora(),
                            nuevoAforo,
                            horariosInformacion.get(indiceHorario).cantidadDeVisitas());
                    horariosInformacion.set(indiceHorario, horarioConNuevoAforo);

                    System.out.print("(!) Aforo actualizado correctamente");

                    delay(2);
                    break;
                case 4:
                    limpiarPantalla();
                    System.out.println("=== ESTUDIANTES PENALIZADOS ===");

                    boolean existeEstudianteActivo = usuarios.stream()
                        .filter(p -> p instanceof Estudiante e && e.presentaPenalidades())
                        .findAny()
                        .isPresent();
                        
                    if (!existeEstudianteActivo) {
                        System.out.print("(!) No hay estudiantes con penalidades");
                        delay(2);
                        break;
                    }
                    
                    // listar estudiantes que tengan una penalidad o mas
                    usuarios.stream()
                        .filter(p -> p instanceof Estudiante)
                        .map(p -> (Estudiante) p)
                        .forEach( estudiante -> {
                            if (estudiante.presentaPenalidades()) {
                                estudiante.mostrarInformacionPersonal();
                            }
                        });

                    pausar();
                    break;
                case 5:
                    limpiarPantalla();
                    // listar estudiantes que tengan una penalidad o mas
                    // ID | Nombre | Cantidad de penalidades
                    usuarios.stream()
                            .filter(p -> p instanceof Estudiante)
                            .map(p -> (Estudiante) p)
                            .forEach(estudiante -> {
                                if (estudiante.presentaPenalidades()) {
                                    System.out.println(estudiante.obtenerId() + " | " + estudiante.obtenerNombre() + " | " + estudiante.obtenerCantidadPenalidades());
                                }
                            });

                    // encontrar el estudiante por ID
                    int idIngresado = 0;
                    Estudiante estudianteEncontrado = null;
                    do {
                        System.out.print("Ingresar el ID de un usuario >> ");
                        idIngresado = scanner.nextInt();
                        scanner.nextLine();

                        // verificar que sea un ID valido
                        try {
                            estudianteEncontrado = (Estudiante) usuarios.get(idIngresado);
                        } catch (IndexOutOfBoundsException e) {
                            System.err.println("(!) ID no encontrado, intente de nuevo");
                            continue;
                        }

                        // verificar que el estudiante tenga alguna penalidad
                        if (!estudianteEncontrado.presentaPenalidades()) {
                            System.out.println("(!) Este usuario no tiene penalidades, intente de nuevo");
                        }
                    } while (estudianteEncontrado == null || !estudianteEncontrado.presentaPenalidades());

                    // solicitar la cantidad de penalidades que desea descontar
                    // deben estar entre 0 y 3
                    short descuentoPenalidades = 0;
                    do {
                        System.out.print("Cantidad de penalidades a descontar (0 - 3) >> ");
                        descuentoPenalidades = scanner.nextShort();
                        scanner.nextLine();

                        if (descuentoPenalidades < 0 || descuentoPenalidades > 3) {
                            System.out.println("(!) Cantidad invalida, intente de nuevo");
                        }
                    } while (descuentoPenalidades < 0 || descuentoPenalidades > 3);

                    // no hacer nada porque no hay reduccion
                    if (descuentoPenalidades == 0)  {
                        System.out.print("(!) No se redujo la penalidad");
                        delay(2);
                        break;
                    }

                    // revocar el estado de las penalidades cuando se reducen a 0
                    if (descuentoPenalidades == estudianteEncontrado.obtenerCantidadPenalidades()) {
                        estudianteEncontrado.establecerEstadoDePenalidades(false);
                    }

                    // revocar el veto cuando las penalidades se reducen a 0, 1 o 2
                    estudianteEncontrado.establecerVetoTemporal(false);

                    // actualizar la cantidad de penalidades
                    short nuevaCantidadDePenalidades = estudianteEncontrado.obtenerCantidadPenalidades();
                    nuevaCantidadDePenalidades -= descuentoPenalidades;
                    estudianteEncontrado.establecerCantidadPenalidades(nuevaCantidadDePenalidades);

                    System.out.print("(!) Reduccion de penalidad exitosa");
                    delay(2);
                    break;
                case 6:
                    System.out.print("Cerrando sesion...");
                    delay(3);
                    break;
                default:
                    System.out.print("(!) Opcion invalida, intente de nuevo");
                    delay(2);
                    break;
            }
        } while (opcion != 6);
    }

    private static void menuDeAtleta(Estudiante atleta) {
        int opcion = 0;
        do {
            limpiarPantalla();
            System.out.println("=== MENU ESTUDIANTE ===");
            // Ejemplo de salida
            // Bienvenido, Lucas. Tienes 5 punto(s) - Nivel 1
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
                    limpiarPantalla();
                    int opcionHorario = 0;

                    // visualizar horarios y seleccionar uno para reservar turno
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
                    delay(2);
                    break;
                case 2:
                    atleta.cancelarReserva(horariosInformacion, reservas);
                    delay(2);
                    break;
                case 3:
                    limpiarPantalla();
                    atleta.menuRutinas();
                    break;
                case 4:
                    atleta.registrarIngreso(horariosInformacion, reservas);
                    delay(2);
                    break;
                case 5:
                    System.out.println("Mostrando logros desbloqueados...");
                    break;
                case 6:
                    System.out.print("Cerrando sesion...");
                    delay(2);
                    break;
                case 7:
                    System.out.println("Horas de entrenamiento registradas.");
                    break;
                default:
                    System.out.print("(!) Opcion invalida, intente de nuevo");
                    delay(2);
                    break;
            }
        } while (opcion != 6);
    }

    private static void menuDeEstudianteRegular(Estudiante estudianteRegular) {
        int opcion = 0;
        do {
            limpiarPantalla();
            System.out.println("=== MENU ESTUDIANTE ===");
            // Ejemplo de salida
            // Bienvenido, Juan. Tienes 5 punto(s) - Nivel 1
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
                    limpiarPantalla();
                    // visualizar horarios y seleccionar uno para reservar turno
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
                    delay(2);
                    break;
                case 2:
                    estudianteRegular.cancelarReserva(horariosInformacion, reservas);
                    delay(2);
                    break;
                case 3:
                    limpiarPantalla();
                    estudianteRegular.menuRutinas();
                    break;
                case 4:
                    estudianteRegular.registrarIngreso(horariosInformacion, reservas);
                    delay(2);
                    break;
                case 5:
                    System.out.println("Mostrando logros desbloqueados...");
                    break;
                case 6:
                    System.out.print("Cerrando sesion...");
                    delay(3);
                    break;
                default:
                    System.out.print("(!) Opcion invalida, intente de nuevo");
                    delay(2);
                    break;
            }
        } while (opcion != 6);
    }

    private static void mostrarHorarios() {
        System.out.println("=== HORARIOS DISPONIBLES ===");
        for (int i = 0; i < horariosInformacion.size(); i++) {
            // Ejemplo de salida
            // 1. 8:00 - 9:00 | Aforo: 25
            System.out.println((i + 1) + ". " + horariosInformacion.get(i).hora() + "-" + horariosInformacion.get(i).hora().plusHours(1) + " | Aforo: " + horariosInformacion.get(i).cupos()
            );
        }
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

    public static void limpiarPantalla() {
        try {
            ProcessBuilder pb;
            if (System.getProperty("os.name").toLowerCase().contains("win")) {
                pb = new ProcessBuilder("cmd", "/c", "cls");
            } else {
                pb = new ProcessBuilder("clear");
            }

            pb.inheritIO().start().waitFor();
        } catch (Exception e) {
            System.out.print("(!) No se pudo limpiar la pantalla");
            delay(2);
        }
    }

    public static void delay(int segundos) {
        try {
            Thread.sleep(segundos * 1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void pausar() {
        try {
            while (System.in.available() > 0) {
                System.in.read();
            }
            System.in.read();
        } catch (Exception e) {}
    }
}