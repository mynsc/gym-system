package src.com.unmsm.gym.service;

import src.com.unmsm.gym.model.Reservation;
import src.com.unmsm.gym.model.User;

public interface IReservationService {

    boolean createReservation(User user, String time);

    void cancelReservation(User user, Reservation res);

    boolean processCheckIn(User user);
}
