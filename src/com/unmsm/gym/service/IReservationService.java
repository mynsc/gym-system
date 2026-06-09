package src.com.unmsm.gym.service;

import src.com.unmsm.gym.model.Reservation;
import src.com.unmsm.gym.model.Persona;

public interface IReservationService {

    boolean createReservation(Persona user, String time);

    void cancelReservation(Persona user, Reservation res);

    boolean processCheckIn(Persona user);
}
