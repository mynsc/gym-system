package com.unmsm.gym;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

import com.unmsm.gym.config.Conexion;
import com.unmsm.gym.models.Administrador;
import com.unmsm.gym.models.Estudiante;
import com.unmsm.gym.models.Persona;
import com.unmsm.gym.models.Rutina;

public class Main {
    public record HorarioCuposVisitas(LocalTime hora, Integer cupos, Integer cantidadDeVisitas) {}
    public static Scanner scanner = new Scanner(System.in);
    public static Connection conexion = null;
    static List<Persona> usuarios = new ArrayList<>();                         // lista de usuarios
    static List<HorarioCuposVisitas> horariosInformacion = new ArrayList<>();  // lista de horario, aforo y veces que se ha visitado cada horario
    static List<List<Integer>> reservas = new LinkedList<>();                  // lista de listas (ID - horario reservado)

    public static void main(String args[]) {
        // establecer conexion a la base de datos o detener el programa
        conexion = Conexion.conectar();
        if (!Conexion.hayConexion(conexion)) {
            System.out.println("(!) No se pudo establecer la conexion a la base de datos");
            return;
        }

        System.out.println("(!) Conexion a la base de datos exitosa");
        pausar();

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
            opcion = leerEntero("Ingresar opcion >> ");

            switch (opcion) {
                case 1: {
                    limpiarPantalla();
                    /* Login */
                    int intentos = 0;
                    Persona usuario = null;

                    do {
                        String nombreDeUsuario = leerNoVacio("Usuario >> ");
                        String contrasenia = leerNoVacio("Contrasena >> ");
                        
                        PreparedStatement sentenciaPersona = null;
                        ResultSet resultadoPersona = null;

                        try {
                            // buscar en la tabla Persona el nombre de usuario y contraseña
                            String sqlLogin = "SELECT * FROM persona WHERE nombre_de_usuario = ? AND contrasenia = ?";
                            sentenciaPersona = conexion.prepareStatement(sqlLogin);
                            sentenciaPersona.setString(1, nombreDeUsuario);
                            sentenciaPersona.setString(2, contrasenia);
                            
                            resultadoPersona = sentenciaPersona.executeQuery();

                            // verificar si hay coincidencias
                            if (resultadoPersona.next()) {
                                // si los datos son correctos, extraer los valores de las columnas
                                int idUsuario = resultadoPersona.getInt("id");
                                String nombre = resultadoPersona.getString("nombre");
                                String apellido = resultadoPersona.getString("apellido");
                                String rol = resultadoPersona.getString("rol");

                                // instanciar si es Administrador
                                if (rol.equalsIgnoreCase("ADMINISTRADOR")) {
                                    usuario = new Administrador(nombre, apellido, nombreDeUsuario, contrasenia);
                                    
                                } else if (rol.equalsIgnoreCase("ESTUDIANTE")) {
                                    PreparedStatement sentenciaEstudiante = null;
                                    ResultSet resultadoEstudiante = null;
                                    
                                    try {
                                        String sqlEstudiante = "SELECT * FROM estudiante WHERE id_persona = ?";
                                        sentenciaEstudiante = conexion.prepareStatement(sqlEstudiante);
                                        sentenciaEstudiante.setInt(1, idUsuario);
                                        resultadoEstudiante = sentenciaEstudiante.executeQuery();
                                        
                                        // verificar si la persona es estudiante
                                        if (resultadoEstudiante.next()) {
                                            // si lo es, extraer los valores de las columnas
                                            String tipoDeEstudiante = resultadoEstudiante.getString("tipo_de_estudiante");
                                            String facultad = resultadoEstudiante.getString("facultad");
                                            String carrera = resultadoEstudiante.getString("carrera");
                                            String baseInicio = resultadoEstudiante.getString("base_inicio");
                                            boolean autoseguroActivo = resultadoEstudiante.getBoolean("autoseguro_activo");
                                            boolean matriculadoSemestreActual = resultadoEstudiante.getBoolean("matriculado_semestre_actual");
                                            boolean presentaLesion = resultadoEstudiante.getBoolean("presenta_lesion");
                                            
                                            // instanciar al Estudiante con todos sus datos
                                            usuario = new Estudiante(nombre, apellido, nombreDeUsuario, 
                                                                     contrasenia, tipoDeEstudiante, facultad, 
                                                                     carrera, baseInicio, autoseguroActivo, 
                                                                     matriculadoSemestreActual, presentaLesion);
                                        }
                                    } finally {
                                        // cerrar el PreparedStatement y ResultSet solo si se llegaron a declarar
                                        if (resultadoEstudiante != null) resultadoEstudiante.close();
                                        if (sentenciaEstudiante != null) sentenciaEstudiante.close();
                                    }
                                }
                            }
                        } catch (SQLException e) {
                            System.out.println("(!) Error de base de datos: " + e.getMessage());
                        } finally {
                            try {
                                // cerrar el PreparedStatement y ResultSet solo si se llegaron a declarar
                                if (resultadoPersona != null) resultadoPersona.close();
                                if (sentenciaPersona != null) sentenciaPersona.close();
                            } catch (SQLException e) {
                                e.printStackTrace();
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

                    if (usuario instanceof Estudiante estudiante) {
                        menuDeEstudiante(estudiante);
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
                    int tipoPerfil = leerEntero("Ingresar opcion >> ");

                    if (tipoPerfil < 1 || tipoPerfil > 3) {
                        System.out.print("(!) Opcion invalida, regresando al menu de inicio");
                        delay(2);
                        break;
                    }
                    
                    limpiarPantalla();

                    // pedir atributos comunes de clase Estudiante
                    String nombre = leerNoVacio("Nombre >> ");
                    String apellido = leerNoVacio("Apellido >> ");
                    String nombreDeUsuario = leerNoVacio("Nombre de usuario >> ");
                    String contrasenia = leerNoVacio("Contrasena >> ");
                    String facultad = leerNoVacio("Facultad >> ");
                    String carrera = leerNoVacio("Carrera >> ");
                    String baseInicio = leerNoVacio("Base de ingreso >> ");

                    // atributos booleanas
                    boolean autoseguroActivo = leerEntero("Tienes autoseguro activo (1 = Si, 0 = No) >> ") == 1;
                    boolean matriculadoSemestreActual = leerEntero("Estas matriculado en el semestre actual (1 = Si, 0 = No) >> ") == 1;
                    boolean presentaLesion = leerEntero("Presentas alguna lesion fisica (1 = Si, 0 = No) >> ") == 1;

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

                            Administrador.registrarEstudianteEnBD(conexion, nuevoRegular);
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

                            Administrador.registrarEstudianteEnBD(conexion, nuevoAtleta);
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

                            Administrador.registrarEstudianteEnBD(conexion, nuevoDiscapacitado);
                            delay(2);
                            break;
                    }
                    break;
                case 3:
                    limpiarPantalla();
                    System.out.print("Saliendo del sistema...");
                    Conexion.cerrarConexion(conexion);
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
            opcion = leerEntero("Ingresar opcion >> ");

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
                        opcionHorario = leerEntero("Ingresar opcion >> ");

                        if (opcionHorario < 1 || opcionHorario > horariosInformacion.size()) {
                            System.out.print("(!) Opcion invalida, intente de nuevo");
                            delay(2);
                        }
                    } while (opcionHorario < 1 || opcionHorario > horariosInformacion.size());

                    int indiceHorario = opcionHorario - 1;
                    Integer nuevoAforo = 0;
                    do {
                        nuevoAforo = leerEntero("Ingresar nuevo aforo >> ");

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
                        idIngresado = leerEntero("Ingresar el ID de un usuario >> ");

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
                        descuentoPenalidades = (short) leerEntero("Cantidad de penalidades a descontar (0 - 3) >> ");

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

    private static void menuDeEstudiante(Estudiante estudiante) {
        boolean esAtleta = estudiante.obtenerTipoDeEstudiante().equals("Atleta");
        int opcion = 0;
        do {
            limpiarPantalla();
            System.out.println("=== MENU ESTUDIANTE ===");
            // Ejemplo de salida
            // Bienvenido, Juan. Tienes 5 punto(s) - Nivel 1
            System.out.println("Bienvenido, " + estudiante.obtenerNombre() + ". Tienes " + estudiante.obtenerNumeroDePuntos() + " punto(s) - Nivel " + estudiante.obtenerNivel());
            System.out.println("1. Reservar turno");
            System.out.println("2. Cancelar reserva");
            System.out.println("3. Ver y editar mi rutina");
            System.out.println("4. Registrar ingreso");
            System.out.println("5. Ver mis logros desbloqueados");
            System.out.println("6. Cerrar sesion");
            if (esAtleta) {
                System.out.println("7. Registrar horas de entrenamiento");
            }
            opcion = leerEntero("Ingresar opcion >> ");

            switch (opcion) {
                case 1:
                    limpiarPantalla();
                    // visualizar horarios y seleccionar uno para reservar turno
                    int opcionHorario = 0;

                    mostrarHorarios();
                    do {
                        opcionHorario = leerEntero("Ingresar opcion >> ");

                        if (opcionHorario < 1 || opcionHorario > horariosInformacion.size()) {
                            System.out.println("(!) Opcion invalida, intente de nuevo");
                        }
                    } while (opcionHorario < 1 || opcionHorario > horariosInformacion.size());

                    estudiante.reservarTurno(opcionHorario, horariosInformacion, reservas);
                    delay(2);
                    break;
                case 2:
                    estudiante.cancelarReserva(horariosInformacion, reservas);
                    delay(2);
                    break;
                case 3:
                    limpiarPantalla();
                    if (estudiante.obtenerRutinas().isEmpty()) {
                        Rutina nuevaRutina = estudiante.crearRutina();
                        estudiante.obtenerRutinas().add(nuevaRutina);
                    }

                    int opcionCRUD = 0;
                    do {
                        limpiarPantalla();
                        System.out.println("=== MENU RUTINAS ===");
                        System.out.println("1. Agregar rutina");
                        System.out.println("2. Mostrar rutinas");
                        System.out.println("3. Editar rutina");
                        System.out.println("4. Eliminar rutina");
                        System.out.println("5. Salir");
                        opcionCRUD = leerEntero("Ingresar opcion >> ");

                        switch (opcionCRUD) {
                            case 1:
                                limpiarPantalla();
                                Rutina nuevaRutina = estudiante.crearRutina();
                                estudiante.obtenerRutinas().add(nuevaRutina);
                                break;
                            case 2:
                                limpiarPantalla();
                                System.out.println("=== TUS RUTINAS ===");
                                
                                if (estudiante.obtenerRutinas().isEmpty()) {
                                    System.out.print("(!) No tienes rutinas registradas actualmente");
                                    delay(2);
                                    break;
                                }

                                // listar rutinas si no es una lista vacia
                                for (Rutina rutina : estudiante.obtenerRutinas()) {
                                    System.out.println(rutina.mostrarDetallesDeRutina());
                                }
                                pausar();
                                break;
                            case 3:
                                if (estudiante.obtenerRutinas().isEmpty()) {
                                    System.out.print("(!) No tienes rutinas registradas actualmente");
                                    delay(2);
                                    break;
                                }

                                limpiarPantalla();
                                estudiante.editarRutina();
                                break;
                            case 4:
                                estudiante.eliminarRutina();
                                break;
                            case 5:
                                break;
                            default:
                                System.out.println("(!) Opcion invalida, intente de nuevo");
                                delay(2);
                                break;
                        }
                    } while (opcion != 5);

                    break;
                case 4:
                    estudiante.registrarIngreso(horariosInformacion, reservas);
                    delay(2);
                    break;
                case 5:
                    System.out.println("Mostrando logros desbloqueados...");
                    break;
                case 6:
                    System.out.print("Cerrando sesion...");
                    delay(3);
                    break;
                case 7:
                    if (esAtleta) {
                        System.out.println("Horas de entrenamiento registradas.");
                        break;
                    }
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

    public static String leerNoVacio(String textoIngresado) {
        while (true) {
            System.out.print(textoIngresado);
            String linea = scanner.nextLine().trim();
            if (!linea.isEmpty()) {
                return linea;
            }

            System.out.println("(!) El valor no puede estar vacio");
        }
    }

    // lee una linea completa y la valida como entero, reintentando si el texto no es numerico
    public static int leerEntero(String textoIngresado) {
        while (true) {
            System.out.print(textoIngresado);
            String linea = scanner.nextLine().trim();
            try {
                return Integer.parseInt(linea);
            } catch (NumberFormatException e) {
                System.out.println("(!) Ingresa un numero valido");
            }
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