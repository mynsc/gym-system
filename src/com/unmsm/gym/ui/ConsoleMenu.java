package src.com.unmsm.gym.ui;

import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

import src.com.unmsm.gym.auth.AuthManager;
import src.com.unmsm.gym.model.Administrator;
import src.com.unmsm.gym.model.Athlete;
import src.com.unmsm.gym.model.RegularStudent;
import src.com.unmsm.gym.model.ScheduleBlock;
import src.com.unmsm.gym.model.Usuario;
import src.com.unmsm.gym.service.ReservationManager;

public class ConsoleMenu {
	private final AuthManager authManager;
	private final ReservationManager reservationManager;
	private final Scanner scanner;

	public ConsoleMenu(AuthManager authManager, ReservationManager reservationManager) {
		this.authManager = authManager;
		this.reservationManager = reservationManager;
		this.scanner = new Scanner(System.in);
	}

	public void start() {
		mainMenuLoop();
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
		Usuario user = null;

		do {
			String username = readNonEmpty("Usuario: ");
			String password = readNonEmpty("Contrasena: ");
			user = authManager.login(username, password);

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

	private void routeToRoleMenu(Usuario user) {
		if (user instanceof Administrator) {
			adminMenu((Administrator) user);
			return;
		}

		if (user instanceof Athlete) {
			athleteMenu((Athlete) user);
			return;
		}

		studentMenu(user);
	}

	private void studentMenu(Usuario user) {
		int option;
		do {
			System.out.println("=== MENU ESTUDIANTE ===");
			System.out.println("Bienvenido, " + user.getName() + ". Tienes " + user.getHasGamification().getTotalPoints() + " punto(s) (Nivel " + user.getHasGamification().getLevel()+ ").");
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

	private void athleteMenu(Athlete user) {
		int option;
		do {
			System.out.println("=== MENU ESTUDIANTE ===");
			System.out.println("Bienvenido, " + user.getName() + ". Tienes " + user.getHasGamification().getTotalPoints() + " punto(s) (Nivel " + user.getHasGamification().getLevel()+ ").");
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
					System.out.println("Registrando horas de entrenamiento...");
					break;
				default:
					System.out.println("Opcion invalida. Intente de nuevo.");
					break;
			}
		} while (option != 6);
	}

	private void handleReservation(Usuario user) {
		showSchedule();
		String time = readNonEmpty("Hora de inicio (ej. 08:00): ");
		reservationManager.createReservation(user, time);
	}

	private void handleCancelReservation(Usuario user) {
		showSchedule();
		String time = readNonEmpty("Hora de inicio a cancelar: ");
		reservationManager.cancelReservation(user, time);
	}

	private void handleCheckIn(Usuario user) {
		reservationManager.processCheckIn(user);
	}

	private void showSchedule() {
		if (reservationManager == null || reservationManager.getGym() == null) {
			System.out.println("No hay turnos cargados.");
			return;
		}

		Map<String, ScheduleBlock> blocks = reservationManager.getGym().getScheduleBlocks();
		if (blocks.isEmpty()) {
			System.out.println("No hay turnos disponibles.");
			return;
		}

		System.out.println("=== HORARIOS DISPONIBLES ===");
		for (Map.Entry<String, ScheduleBlock> entry : new TreeMap<>(blocks).entrySet()) {
			ScheduleBlock block = entry.getValue();
			int maxCapacity = block.getMaxCapacity();
			String capacityLabel = maxCapacity > 0 ? String.valueOf(maxCapacity) : "-";
			System.out.println(
				entry.getKey() + " -> " + block.getStartTime() + " - " + block.getEndTime()
					+ " | Ocupacion: " + block.getConfirmedCount() + "/" + capacityLabel
					+ " | Espera: " + block.getWaitListSize()
			);
		}
	}

	private void adminMenu(Administrator user) {
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
					System.out.println("Generando reporte de usuarios activos...");
					break;
				case 2:
					System.out.println("Generando reporte de horarios...");
					break;
				case 3:
					System.out.println("Modificando aforo de bloque horario...");
					break;
				case 4:
					System.out.println("Mostrando alumnos penalizados...");
					break;
				case 5:
					System.out.println("Revocando penalidad...");
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

	private void registerStudent() {
		System.out.println("=== REGISTRO ESTUDIANTE ===");
		int id = readInt("ID: ");
		String name = readNonEmpty("Nombre: ");
		String username = readNonEmpty("Usuario: ");
		String studentCode = readNonEmpty("Codigo: ");
		String password = readNonEmpty("Contrasena: ");

		RegularStudent student = new RegularStudent(id, username, password, name, studentCode);
		authManager.registerUser(student);

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