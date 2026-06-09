package src.com.unmsm.gym.service;

import src.com.unmsm.gym.model.Reservation;
import src.com.unmsm.gym.model.Usuario;

public interface IReservationService {

    boolean createReservation(Usuario user, String time);

    void cancelReservation(Usuario user, Reservation res);

    boolean processCheckIn(Usuario user);
}
