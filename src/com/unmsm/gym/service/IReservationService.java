package src.com.unmsm.gym.service;

import src.com.unmsm.gym.models.Persona;
import src.com.unmsm.gym.models.Reservation;

public interface IReservationService {

    boolean createReservation(Persona user, String time);

    void cancelReservation(Persona user, Reservation res);

    boolean processCheckIn(Persona user);
}
