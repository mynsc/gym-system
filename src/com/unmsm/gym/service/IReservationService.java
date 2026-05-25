package src.com.unmsm.gym.service;

import src.com.unmsm.gym.model.Reservation;
import src.com.unmsm.gym.model.User;

public interface IReservationService {

    public boolean CreateReservation(User user, String time);
    

    public void CancelReservation(User user, Reservation res);

    public boolean ProcessCheckIn(User user);
}
