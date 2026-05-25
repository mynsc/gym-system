import java.time.LocalDate;

public class Reservation {
    private int id;
    private LocalDate ScheduleDate;
    private ReservationStatus status;


    public Reservation(int id, LocalDate scheduleDate) {
        this.id = id;
        ScheduleDate = scheduleDate;
        this.status = ReservationStatus.CONFIRMED;
    }
    public void UpdateStatus(ReservationStatus newStatus){
        
    }
    
}
