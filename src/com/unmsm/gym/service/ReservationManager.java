package src.com.unmsm.gym.service;

import java.time.LocalDate;
import java.time.LocalDateTime;

import src.com.unmsm.gym.models.AttendanceRecord;
import src.com.unmsm.gym.models.Gimnasio;
import src.com.unmsm.gym.models.Persona;
import src.com.unmsm.gym.models.Reservation;
import src.com.unmsm.gym.models.ReservationStatus;
import src.com.unmsm.gym.models.ScheduleBlock;

public class ReservationManager implements IReservationService {
    private Gimnasio gym;
    private static int nextReservationId = 1;

    public ReservationManager(Gimnasio gym) {
        this.gym = gym;
    }

    public Gimnasio getGym() {
        return gym;
    }

    @Override
    public boolean createReservation(Persona user, String time) {
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

        ScheduleBlock block = gym.getBlock(key);
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

        Reservation reservation = buildReservation(user);
        if (block.addReservation(reservation)) {
            System.out.println("Reserva confirmada para " + block.getStartTime() + " - " + block.getEndTime() + ".");
            return true;
        }

        block.addToWaitList(user);
        System.out.println("Aforo lleno. Ingresas a lista de espera...");
        return false;
    }

    @Override
    public void cancelReservation(Persona user, Reservation res) {
        if (user == null) {
            System.out.println("Usuario invalido.");
            return;
        }

        Reservation target = res;
        ScheduleBlock block = null;

        if (target != null) {
            block = findBlockWithReservation(target);
            if (target.getBookedBy() != null && !user.equals(target.getBookedBy())) {
                System.out.println("No puedes cancelar una reserva de otro usuario.");
                return;
            }
        }

        if (block == null) {
            block = findBlockForUser(user);
            if (block != null) {
                target = block.findReservationByUser(user);
            }
        }

        if (block == null || target == null) {
            System.out.println("No tienes reservas activas para cancelar.");
            return;
        }

        if (!block.removeReservation(target)) {
            System.out.println("No se pudo cancelar la reserva.");
            return;
        }

        target.updateStatus(ReservationStatus.CANCELLED);
        System.out.println("Reserva cancelada.");

        Persona promoted = block.pollWaitList();
        if (promoted != null) {
            Reservation newReservation = buildReservation(promoted);
            if (block.addReservation(newReservation)) {
                System.out.println("Cupo liberado. " + promoted.getName() + " ha sido promovido.");
            }
        }
    }

    @Override
    public boolean processCheckIn(Persona user) {
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

    public void cancelReservation(Persona user, String time) {
        if (user == null) {
            System.out.println("Usuario invalido.");
            return;
        }

        String key = time == null ? "" : time.trim();
        if (key.isEmpty()) {
            System.out.println("Debe ingresar una hora valida.");
            return;
        }

        ScheduleBlock block = gym.getBlock(key);
        if (block == null) {
            System.out.println("Turno no encontrado. Verifica la hora ingresada.");
            return;
        }

        Reservation reservation = block.findReservationByUser(user);
        cancelReservation(user, reservation);
    }

    private Reservation buildReservation(Persona user) {
        return new Reservation(nextReservationId++, LocalDate.now(), user, null);
    }

    private Reservation findReservationForUser(Persona user) {
        for (ScheduleBlock block : gym.getScheduleBlocks().values()) {
            Reservation reservation = block.findReservationByUser(user);
            if (reservation != null) {
                return reservation;
            }
        }

        return null;
    }

    private ScheduleBlock findBlockForUser(Persona user) {
        for (ScheduleBlock block : gym.getScheduleBlocks().values()) {
            if (block.findReservationByUser(user) != null) {
                return block;
            }
        }

        return null;
    }

    private ScheduleBlock findBlockWithReservation(Reservation reservation) {
        for (ScheduleBlock block : gym.getScheduleBlocks().values()) {
            if (block.getConfirmedReservations().contains(reservation)) {
                return block;
            }
        }

        return null;
    }
}
