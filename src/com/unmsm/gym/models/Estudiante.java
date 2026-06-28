package src.com.unmsm.gym.models;

import java.time.LocalDate;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Estudiante extends Persona {
    private String facultad;
    private String carrera;
    private String baseInicio;
    private boolean matriculadoSemestreActual;
    private boolean autoseguroActivo;
    private LocalDate ultimaVisita;
    private boolean visitanteConcurrente;
    private boolean presentaReservacion;
    private boolean presentaPenalidades;
    private short cantidadPenalidades;
    private boolean presentaLesionActual;
    private boolean vetadoTemporalmente;
    private int numeroDePuntos;
    private int nivel;
    private List<Rutina> rutinas;

    public Estudiante(
            int id, String nombre, String apellido, String nombreDeUsuario, String contrasenia,
            String facultad, String carrera, String baseInicio,
            boolean autoseguroActivo, boolean matriculadoSemestreActual, boolean presentaLesionActual) {

        super(id, nombre, apellido, nombreDeUsuario, contrasenia);
        this.facultad = facultad;
        this.carrera = carrera;
        this.baseInicio = baseInicio;
        this.autoseguroActivo = autoseguroActivo;
        this.ultimaVisita = null;
        this.visitanteConcurrente = false;
        this.matriculadoSemestreActual = matriculadoSemestreActual;
        this.presentaReservacion = false;
        this.presentaPenalidades = false;
        this.cantidadPenalidades = 0;
        this.presentaLesionActual = presentaLesionActual;
        this.vetadoTemporalmente = false;
        this.numeroDePuntos = 0;
        this.nivel = 0;
        this.rutinas = new LinkedList<>();
    }

    public void reservarTurno(int opcionHorario, List<Horarios> horariosInformacion, List<Reserva> reservas) {
        int codigoNuevoHorario = opcionHorario - 1;
        if (horariosInformacion.get(codigoNuevoHorario).getCupos() == 0) {
            System.out.println("(!) Ese horario ya no tiene cupos");
            return;
        }

        int indiceReservaActiva = new Administrador().buscarReservaPorId(this, reservas);

        if (indiceReservaActiva != -1) {
            System.out.println("(!) Se reemplazo su reserva anterior por este nuevo horario");
            int codigoHorarioActivo = reservas.get(indiceReservaActiva).getIdHorario();
            horariosInformacion.get(codigoHorarioActivo).setCupos(
                horariosInformacion.get(codigoHorarioActivo).getCupos() + 1
            );
            reservas.remove(indiceReservaActiva);
        }

        int nuevoId = reservas.size();
        reservas.add(new Reserva(nuevoId, this.obtenerId(), codigoNuevoHorario));

        horariosInformacion.get(codigoNuevoHorario).setCupos(
            horariosInformacion.get(codigoNuevoHorario).getCupos() - 1
        );
        this.establecerEstadoDeReservacion(true);

        System.out.println("Reserva realizada para " + horariosInformacion.get(codigoNuevoHorario).getHora());
    }

    public void cancelarReserva(List<Horarios> horariosInformacion, List<Reserva> reservas) {
        int indiceReserva = new Administrador().buscarReservaPorId(this, reservas);

        if (indiceReserva == -1) {
            System.out.println("(!) No tienes una reserva activa");
            return;
        }

        int indiceHorario = reservas.get(indiceReserva).getIdHorario();
        horariosInformacion.get(indiceHorario).setCupos(
            horariosInformacion.get(indiceHorario).getCupos() + 1
        );
        this.establecerEstadoDeReservacion(false);
        reservas.remove(indiceReserva);

        System.out.println("Reserva cancelada para " + horariosInformacion.get(indiceHorario).getHora());
    }

    public void registrarIngreso(List<Horarios> horariosInformacion, List<Reserva> reservas) {
        int indiceReserva = new Administrador().buscarReservaPorId(this, reservas);

        if (indiceReserva == -1) {
            System.out.println("(!) No tienes una reserva activa para registrar visita");
            return;
        }

        int indiceHorario = reservas.get(indiceReserva).getIdHorario();
        this.establecerUltimaVisita(LocalDate.now());
        horariosInformacion.get(indiceHorario).setCantVisitas(
            horariosInformacion.get(indiceHorario).getCantVisitas() + 1
        );
        reservas.remove(indiceReserva);
        this.establecerEstadoDeReservacion(false);

        System.out.println("Visita registrada en el horario " + horariosInformacion.get(indiceHorario).getHora());
    }

    public void menuRutinas() {
        Scanner scanner = new Scanner(System.in);
        if (this.rutinas == null) {
            Rutina nuevaRutina = crearRutina(scanner);
            rutinas.add(nuevaRutina);
        }

        int opcion = 0;
        do {
            System.out.println("=== MENU RUTINAS ===");
            System.out.println("1. Agregar rutina");
            System.out.println("2. Mostrar rutinas");
            System.out.println("3. Editar rutina");
            System.out.println("4. Eliminar rutina");
            System.out.println("5. Salir");
            System.out.print("Ingresar opcion >> ");
            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    Rutina nuevaRutina = crearRutina(scanner);
                    rutinas.add(nuevaRutina);
                    break;
                case 2:
                    System.out.println("=== TUS RUTINAS ===");
                    if (rutinas.isEmpty()) {
                        System.out.println("(!) No tienes rutinas registradas actualmente.");
                        break;
                    }
                    for (Rutina rutina : rutinas) {
                        System.out.println(rutina.mostrarDetallesDeRutina());
                    }
                    break;
                case 3:
                    editarRutina(scanner);
                    break;
                case 4:
                    eliminarRutina(scanner);
                    break;
                default:
                    break;
            }
        } while (opcion != 5);
    }

    public Rutina crearRutina(Scanner scanner) {
        String nombre = "";
        String objetivo = "";
        List<List<String>> ejercicios = new LinkedList<>();
        int cantidadDeEjercicios = 0;

        System.out.println("=== CREAR RUTINA ===");
        System.out.print("Nombre de la rutina >> ");
        nombre = scanner.nextLine();
        System.out.print("Define el objetivo de la rutina >> ");
        objetivo = scanner.nextLine();
        System.out.print("Cantidad de ejercicios de la rutina >> ");
        cantidadDeEjercicios = scanner.nextInt();
        scanner.nextLine();

        for (int i = 0; i < cantidadDeEjercicios; i++) {
            System.out.print("Nombre del ejercicio >> ");
            String nombreEjercicio = scanner.nextLine();
            System.out.print("Sets del ejercicio >> ");
            String sets = scanner.nextLine();
            System.out.print("Repeticiones del ejercicio >> ");
            String repeticiones = scanner.nextLine();
            ejercicios.add(new LinkedList<>(List.of(nombreEjercicio, sets, repeticiones)));
        }

        int nuevoId = rutinas.size();
        return new Rutina(nuevoId, nombre, objetivo, this, ejercicios);
    }

    public void editarRutina(Scanner scanner) {
        if (this.rutinas.isEmpty()) {
            System.out.println("(!) No tienes rutinas para editar");
            return;
        }

        System.out.print("Ingresa el ID de la rutina que deseas editar >> ");
        int idBuscado = scanner.nextInt();
        scanner.nextLine();

        Rutina rutinaAEditar = null;
        for (Rutina rutina : this.rutinas) {
            if (rutina.obtenerId() == idBuscado) {
                rutinaAEditar = rutina;
                break;
            }
        }

        if (rutinaAEditar != null) {
            System.out.print("Nuevo nombre (deja en blanco para no cambiar) >> ");
            String nuevoNombre = scanner.nextLine();
            if (!nuevoNombre.trim().isEmpty()) rutinaAEditar.establecerNombre(nuevoNombre);

            System.out.print("Nuevo objetivo (deja en blanco para no cambiar) >> ");
            String nuevoObjetivo = scanner.nextLine();
            if (!nuevoObjetivo.trim().isEmpty()) rutinaAEditar.establecerObjetivo(nuevoObjetivo);

            System.out.println("(!) Rutina actualizada correctamente");
        } else {
            System.out.println("(!) No se encontro ninguna rutina con el ID " + idBuscado);
        }
    }

    public void eliminarRutina(Scanner scanner) {
        if (this.rutinas.isEmpty()) {
            System.out.println("(!) No tienes rutinas para eliminar");
            return;
        }

        System.out.print("Ingresa el ID de la rutina que deseas eliminar >> ");
        int idBuscado = scanner.nextInt();
        scanner.nextLine();

        Rutina rutinaAEliminar = null;
        for (Rutina rutina : this.rutinas) {
            if (rutina.obtenerId() == idBuscado) {
                rutinaAEliminar = rutina;
                break;
            }
        }

        if (rutinaAEliminar != null) {
            this.rutinas.remove(rutinaAEliminar);
            System.out.println("(!) La rutina '" + rutinaAEliminar.obtenerNombre() + "' ha sido eliminada");
        } else {
            System.out.println("(!) No se encontro ninguna rutina con el ID " + idBuscado);
        }
    }

    public String obtenerFacultad() { return facultad; }
    public void establecerFacultad(String facultad) { this.facultad = facultad; }
    public String obtenerCarrera() { return carrera; }
    public void establecerCarrera(String carrera) { this.carrera = carrera; }
    public String obtenerBaseInicio() { return baseInicio; }
    public void establecerBaseInicio(String baseInicio) { this.baseInicio = baseInicio; }
    public boolean tieneAutoseguroActivo() { return autoseguroActivo; }
    public void establecerEstadoDeAutoseguro(boolean autoseguroActivo) { this.autoseguroActivo = autoseguroActivo; }

    public LocalDate obtenerUltimaVisita() {
        if (ultimaVisita == null) {
            System.out.println("(!) El usuario no ha registrado una visita aun");
            return null;
        }
        return this.ultimaVisita;
    }

    public void establecerUltimaVisita(LocalDate ultimaVisita) { this.ultimaVisita = ultimaVisita; }
    public boolean esVisitanteConcurrente() { return visitanteConcurrente; }
    public void establecerEstadoDeVisitanteConcurrente(boolean v) { this.visitanteConcurrente = v; }
    public boolean estaMatriculadoSemestreActual() { return matriculadoSemestreActual; }
    public void establecerEstadoDeMatriculaSemestreActual(boolean m) { this.matriculadoSemestreActual = m; }
    public boolean presentaReservacion() { return presentaReservacion; }
    public void establecerEstadoDeReservacion(boolean presentaReservacion) { this.presentaReservacion = presentaReservacion; }
    public boolean presentaPenalidades() { return presentaPenalidades; }
    public void establecerEstadoDePenalidades(boolean presentaPenalidades) { this.presentaPenalidades = presentaPenalidades; }
    public short obtenerCantidadPenalidades() { return cantidadPenalidades; }
    public void establecerCantidadPenalidades(short cantidadPenalidades) { this.cantidadPenalidades = cantidadPenalidades; }
    public boolean presentaLesionActual() { return presentaLesionActual; }
    public void establecerEstadoDeLesionActual(boolean presentaLesionActual) { this.presentaLesionActual = presentaLesionActual; }
    public boolean estaVetadoTemporalmente() { return vetadoTemporalmente; }
    public void establecerVetoTemporal(boolean vetadoTemporalmente) { this.vetadoTemporalmente = vetadoTemporalmente; }
    public int obtenerNumeroDePuntos() { return numeroDePuntos; }
    public void establecerNumeroDePuntos(int numeroDePuntos) { this.numeroDePuntos = numeroDePuntos; }
    public int obtenerNivel() { return nivel; }
    public void establecerNivel(int nivel) { this.nivel = nivel; }
    public List<Rutina> obtenerRutinas() { return rutinas; }
    public void establecerRutinas(List<Rutina> rutinas) { this.rutinas = rutinas; }
}