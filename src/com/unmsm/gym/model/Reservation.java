package src.com.unmsm.gym.model;

import java.time.LocalDate;

public class Reservation {
    private int id;
    private LocalDate ScheduleDate;
    private ReservationStatus status;


    public Reservation(int id, LocalDate scheduleDate) {
        this.id = id;
        ScheduleDate = scheduleDate;
        this.status = ReservationStatus.PENDING;
    }
    public void updateStatus(ReservationStatus newStatus){
        
    }
    
}
