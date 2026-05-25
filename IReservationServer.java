public interface IReservationServer {

    public boolean CreateReservation(User user, String time);
    

    public void CancelReservation(User user, Reservation res);

    public boolean ProcessCheckIn(User user);
}
