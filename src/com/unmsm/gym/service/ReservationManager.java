package src.com.unmsm.gym.service;

import src.com.unmsm.gym.model.Gym;
import src.com.unmsm.gym.model.Reservation;
import src.com.unmsm.gym.model.User;

public class ReservationManager implements IReservationService {
    private Gym gym;

    @Override
    public boolean createReservation(User user, String time) {
        return true;
    }

    @Override
    public void cancelReservation(User user, Reservation res) {
        return;
    }

    @Override
    public boolean processCheckIn(User user) {
        return true;
    }
}
