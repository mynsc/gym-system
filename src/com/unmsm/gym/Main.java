package src.com.unmsm.gym;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

import src.com.unmsm.gym.models.Administrador;
import src.com.unmsm.gym.models.AttendanceRecord;
import src.com.unmsm.gym.models.Atleta;
import src.com.unmsm.gym.models.Discapacitado;
import src.com.unmsm.gym.models.Estudiante;
import src.com.unmsm.gym.models.Persona;
import src.com.unmsm.gym.models.Reservation;
import src.com.unmsm.gym.models.ReservationStatus;
import src.com.unmsm.gym.models.ScheduleBlock;

public class Main {
    private final List<Persona> users = new ArrayList<>();
    private final Map<String, ScheduleBlock> scheduleBlocks = new HashMap<>();
    private final Scanner scanner = new Scanner(System.in);
    private int maxCapacityPerBlock = 2;
    private int nextReservationId = 1;

    public Main() {
        seedUsers();
        seedScheduleBlocks();
    }

    public static void main(String args[]) {
        new Main().start();
    }

    public void start() {
        mainMenuLoop();
    }

    private void seedUsers() {
        users.add(new Estudiante(1, "regular", "123456", "Matias", "20210001"));
        users.add(new Atleta(2, "atleta", "123456", "Camila", "20210002", "Natacion", "Intermedio", 8));
        users.add(new Discapacitado(3, "discap", "123456", "Luis", "20210003", "Visual"));
        users.add(new Administrador(4, "admin", "123456", "Rosa", "20210004"));
    }

    private void seedScheduleBlocks() {
        scheduleBlocks.put("08:00", new ScheduleBlock("08:00", "09:00", maxCapacityPerBlock));
        scheduleBlocks.put("09:00", new ScheduleBlock("09:00", "10:00", maxCapacityPerBlock));
        scheduleBlocks.put("10:00", new ScheduleBlock("10:00", "11:00", maxCapacityPerBlock));
        scheduleBlocks.put("11:00", new ScheduleBlock("11:00", "12:00", maxCapacityPerBlock));
    }

    private void mainMenuLoop() {
        int option;
        do {
            System.out.println("=== GIMNASIO UNMSM ===");
            System.out.println("1. Iniciar Sesion");
            System.out.println("2. Registrar nuevo Estudiante");
            System.out.println("3. Salir del sistema");
            option = readInt("Ingrese opcion: ");

            switch (option) {
                case 1:
                    handleLogin();
                    break;
                case 2:
                    registerStudent();
                    break;
                case 3:
                    System.out.println("Saliendo del sistema...");
                    break;
                default:
                    System.out.println("Opcion invalida. Intente de nuevo.");
                    break;
            }
        } while (option != 3);
    }

    private void handleLogin() {
        int attempts = 0;
        Persona user = null;

        do {
            String username = readNonEmpty("Usuario: ");
            String password = readNonEmpty("Contrasena: ");
            user = login(username, password);

            if (user == null) {
                attempts++;
                if (attempts < 3) {
                    System.out.println("Credenciales invalidas. Intento " + attempts + " de 3.");
                }
            }
        } while (user == null && attempts < 3);

        if (user == null) {
            System.out.println("Maximo de intentos. Volviendo al menu principal.");
            return;
        }

        routeToRoleMenu(user);
    }

    private Persona login(String username, String password) {
        for (Persona user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }

        return null;
    }

    private void registerUser(Persona user) {
        if (user != null) {
            users.add(user);
        }
    }

    private void routeToRoleMenu(Persona user) {
        if (user instanceof Administrador) {
            adminMenu((Administrador) user);
            return;
        }

        if (user instanceof Atleta) {
            athleteMenu((Atleta) user);
            return;
        }

        studentMenu(user);
    }

    private void studentMenu(Persona user) {
        int option;
        do {
            System.out.println("=== MENU ESTUDIANTE ===");
            System.out.println("Bienvenido, " + user.getName() + ". Tienes " + user.getHasGamification().getTotalPoints() + " punto(s) (Nivel " + user.getHasGamification().getLevel() + ").");
            System.out.println("1. Ver Horarios y Reservar Turno");
            System.out.println("2. Cancelar una Reserva activa");
            System.out.println("3. Mi Rutina (Ver/Editar Ejercicios)");
            System.out.println("4. Simular paso por Torniquete (Check-In)");
            System.out.println("5. Ver mis Logros Desbloqueados");
            System.out.println("6. Cerrar Sesion");
            option = readInt("Ingrese opcion: ");

            switch (option) {
                case 1:
                    handleReservation(user);
                    break;
                case 2:
                    handleCancelReservation(user);
                    break;
                case 3:
                    System.out.println("Mostrando rutina...");
                    break;
                case 4:
                    handleCheckIn(user);
                    break;
                case 5:
                    System.out.println("Mostrando logros desbloqueados...");
                    break;
                case 6:
                    System.out.println("Cerrando sesion...");
                    break;
                default:
                    System.out.println("Opcion invalida. Intente de nuevo.");
                    break;
            }
        } while (option != 6);
    }

    private void athleteMenu(Atleta user) {
        int option;
        do {
            System.out.println("=== MENU ESTUDIANTE ===");
            System.out.println("Bienvenido, " + user.getName() + ". Tienes " + user.getHasGamification().getTotalPoints() + " punto(s) (Nivel " + user.getHasGamification().getLevel() + ").");
            System.out.println("1. Ver Horarios y Reservar Turno");
            System.out.println("2. Cancelar una Reserva activa");
            System.out.println("3. Mi Rutina (Ver/Editar Ejercicios)");
            System.out.println("4. Simular paso por Torniquete (Check-In)");
            System.out.println("5. Ver mis Logros Desbloqueados");
            System.out.println("6. Cerrar Sesion");
            System.out.println("7. Registrar horas de entrenamiento");
            option = readInt("Ingrese opcion: ");

            switch (option) {
                case 1:
                    handleReservation(user);
                    break;
                case 2:
                    handleCancelReservation(user);
                    break;
                case 3:
                    System.out.println("Mostrando rutina...");
                    break;
                case 4:
                    handleCheckIn(user);
                    break;
                case 5:
                    System.out.println("Mostrando logros desbloqueados...");
                    break;
                case 6:
                    System.out.println("Cerrando sesion...");
                    break;
                case 7:
                    user.train();
                    user.complete();
                    System.out.println("Horas de entrenamiento registradas.");
                    break;
                default:
                    System.out.println("Opcion invalida. Intente de nuevo.");
                    break;
            }
        } while (option != 6);
    }

    private void handleReservation(Persona user) {
        showSchedule();
        String time = readNonEmpty("Hora de inicio (ej. 08:00): ");
        createReservation(user, time);
    }

    private void handleCancelReservation(Persona user) {
        showSchedule();
        String time = readNonEmpty("Hora de inicio a cancelar: ");
        cancelReservation(user, time);
    }

    private void handleCheckIn(Persona user) {
        processCheckIn(user);
    }

    private boolean createReservation(Persona user, String time) {
        if (user == null) {
            System.out.println("Usuario invalido.");
            return false;
        }

        if (!user.canAccess()) {
            System.out.println("Acceso denegado. Tienes una penalidad activa.");
            return false;
        }

        String key = time == null ? "" : time.trim();
        if (key.isEmpty()) {
            System.out.println("Debe ingresar una hora valida.");
            return false;
        }

        ScheduleBlock block = scheduleBlocks.get(key);
        if (block == null) {
            System.out.println("Turno no encontrado. Verifica la hora ingresada.");
            return false;
        }

        if (block.findReservationByUser(user) != null) {
            System.out.println("Ya tienes una reserva para este turno.");
            return false;
        }

        if (block.isUserWaiting(user)) {
            System.out.println("Ya estas en lista de espera para este turno.");
            return false;
        }

        Reservation reservation = new Reservation(nextReservationId++, LocalDate.now(), user, null);
        if (block.addReservation(reservation)) {
            System.out.println("Reserva confirmada para " + block.getStartTime() + " - " + block.getEndTime() + ".");
            return true;
        }

        block.addToWaitList(user);
        System.out.println("Aforo lleno. Ingresas a lista de espera...");
        return false;
    }

    private void cancelReservation(Persona user, String time) {
        if (user == null) {
            System.out.println("Usuario invalido.");
            return;
        }

        String key = time == null ? "" : time.trim();
        if (key.isEmpty()) {
            System.out.println("Debe ingresar una hora valida.");
            return;
        }

        ScheduleBlock block = scheduleBlocks.get(key);
        if (block == null) {
            System.out.println("Turno no encontrado. Verifica la hora ingresada.");
            return;
        }

        Reservation reservation = block.findReservationByUser(user);
        if (reservation == null) {
            System.out.println("No tienes reservas activas para cancelar.");
            return;
        }

        if (!block.removeReservation(reservation)) {
            System.out.println("No se pudo cancelar la reserva.");
            return;
        }

        reservation.updateStatus(ReservationStatus.CANCELLED);
        System.out.println("Reserva cancelada.");

        Persona promoted = block.pollWaitList();
        if (promoted != null) {
            Reservation newReservation = new Reservation(nextReservationId++, LocalDate.now(), promoted, null);
            if (block.addReservation(newReservation)) {
                System.out.println("Cupo liberado. " + promoted.getName() + " ha sido promovido.");
            }
        }
    }

    private boolean processCheckIn(Persona user) {
        if (user == null) {
            System.out.println("Usuario invalido.");
            return false;
        }

        if (!user.canAccess()) {
            System.out.println("Acceso denegado. Tienes una penalidad activa.");
            return false;
        }

        Reservation reservation = findReservationForUser(user);
        if (reservation == null) {
            System.out.println("No tienes una reserva activa. Reserva primero.");
            return false;
        }

        if (reservation.getStatus() != ReservationStatus.PENDING) {
            System.out.println("Tu reserva no esta disponible para check-in.");
            return false;
        }

        reservation.updateStatus(ReservationStatus.ATTENDED);
        reservation.setValidates(new AttendanceRecord(LocalDateTime.now()));
        System.out.println("Check-In registrado correctamente.");
        return true;
    }

    private Reservation findReservationForUser(Persona user) {
        for (ScheduleBlock block : scheduleBlocks.values()) {
            Reservation reservation = block.findReservationByUser(user);
            if (reservation != null) {
                return reservation;
            }
        }

        return null;
    }

    private void showSchedule() {
        if (scheduleBlocks.isEmpty()) {
            System.out.println("No hay turnos disponibles.");
            return;
        }

        System.out.println("=== HORARIOS DISPONIBLES ===");
        for (Map.Entry<String, ScheduleBlock> entry : new TreeMap<>(scheduleBlocks).entrySet()) {
            ScheduleBlock block = entry.getValue();
            int blockCapacity = block.getMaxCapacity();
            String capacityLabel = blockCapacity > 0 ? String.valueOf(blockCapacity) : "-";
            System.out.println(
                entry.getKey() + " -> " + block.getStartTime() + " - " + block.getEndTime()
                    + " | Ocupacion: " + block.getConfirmedCount() + "/" + capacityLabel
                    + " | Espera: " + block.getWaitListSize()
            );
        }
    }

    private void adminMenu(Administrador user) {
        int option;
        do {
            System.out.println("=== MENU ADMINISTRADOR ===");
            System.out.println("Panel de Control - Gimnasio Central");
            System.out.println("1. Ver Reporte de Usuarios Activos");
            System.out.println("2. Ver Reporte de Horarios mas concurridos");
            System.out.println("3. Modificar Aforo de un Bloque Horario");
            System.out.println("4. Ver lista de alumnos penalizados");
            System.out.println("5. Revocar penalidad a un alumno");
            System.out.println("6. Cerrar Sesion");
            option = readInt("Ingrese opcion: ");

            switch (option) {
                case 1:
                    showActiveUsersReport();
                    break;
                case 2:
                    showMostUsedSchedule();
                    break;
                case 3:
                    modifyCapacity();
                    break;
                case 4:
                    showPenalizedUsers();
                    break;
                case 5:
                    revokePenalty();
                    break;
                case 6:
                    System.out.println("Cerrando sesion...");
                    break;
                default:
                    System.out.println("Opcion invalida. Intente de nuevo.");
                    break;
            }
        } while (option != 6);
    }

    private void showActiveUsersReport() {
        System.out.println("=== USUARIOS ACTIVOS ===");
        for (Persona user : users) {
            if (user.canAccess()) {
                System.out.println(user.getDetails());
            }
        }
    }

    private void showMostUsedSchedule() {
        ScheduleBlock mostUsedBlock = null;
        String mostUsedKey = null;

        for (Map.Entry<String, ScheduleBlock> entry : scheduleBlocks.entrySet()) {
            if (mostUsedBlock == null || entry.getValue().getConfirmedCount() > mostUsedBlock.getConfirmedCount()) {
                mostUsedBlock = entry.getValue();
                mostUsedKey = entry.getKey();
            }
        }

        if (mostUsedBlock == null) {
            System.out.println("No hay turnos para reportar.");
            return;
        }

        System.out.println("Horario mas concurrido: " + mostUsedKey + " con " + mostUsedBlock.getConfirmedCount() + " reserva(s).");
    }

    private void modifyCapacity() {
        String time = readNonEmpty("Hora del bloque a modificar: ");
        String key = time.trim();
        ScheduleBlock block = scheduleBlocks.get(key);

        if (block == null) {
            System.out.println("Turno no encontrado.");
            return;
        }

        int newCapacity = readInt("Nuevo aforo: ");
        if (newCapacity <= 0) {
            System.out.println("El aforo debe ser mayor a cero.");
            return;
        }

        scheduleBlocks.put(key, new ScheduleBlock(block.getStartTime(), block.getEndTime(), newCapacity));
        System.out.println("Aforo actualizado.");
    }

    private void showPenalizedUsers() {
        System.out.println("=== ALUMNOS PENALIZADOS ===");
        for (Persona user : users) {
            if (user.isHasActivePenalty()) {
                System.out.println(user.getDetails());
            }
        }
    }

    private void revokePenalty() {
        String username = readNonEmpty("Usuario a revocar penalidad: ");
        Persona user = findUserByUsername(username);

        if (user == null) {
            System.out.println("Usuario no encontrado.");
            return;
        }

        user.clearPenalties();
        System.out.println("Penalidad revocada.");
    }

    private Persona findUserByUsername(String username) {
        for (Persona user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }

        return null;
    }

    private void registerStudent() {
        System.out.println("=== REGISTRO ESTUDIANTE ===");
        int id = readInt("ID: ");
        String name = readNonEmpty("Nombre: ");
        String username = readNonEmpty("Usuario: ");
        String studentCode = readNonEmpty("Codigo: ");
        String password = readNonEmpty("Contrasena: ");

        Estudiante student = new Estudiante(id, username, password, name, studentCode);
        registerUser(student);

        System.out.println("Registro exitoso. Contrasena asignada: " + student.getPassword());
    }

    private int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine().trim();

            try {
                return Integer.parseInt(line);
            } catch (NumberFormatException ex) {
                System.out.println("Ingrese un numero valido.");
            }
        }
    }

    private String readNonEmpty(String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine().trim();
            if (!line.isEmpty()) {
                return line;
            }

            System.out.println("El valor no puede estar vacio.");
        }
    }
}
