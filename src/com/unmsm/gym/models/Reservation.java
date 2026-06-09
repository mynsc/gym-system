package src.com.unmsm.gym.models;

import java.time.LocalDate;

public class Reservation {
    private int id;
    private LocalDate ScheduleDate;
    private ReservationStatus status;
    private Persona bookedBy;
    private AttendanceRecord validates;


    public Reservation(int id, LocalDate scheduleDate) {
        this.id = id;
        ScheduleDate = scheduleDate;
        this.status = ReservationStatus.PENDING;
    }

    public Reservation(int id, LocalDate scheduleDate, Persona bookedBy, AttendanceRecord validates) {
        this.id = id;
        this.ScheduleDate = scheduleDate;
        this.status = ReservationStatus.PENDING;
        this.bookedBy = bookedBy;
        this.validates = validates;
    }
    public void updateStatus(ReservationStatus newStatus){
        this.status = newStatus;
    }

    public Persona getBookedBy() { return bookedBy; }
    public void setBookedBy(Persona bookedBy) { this.bookedBy = bookedBy; }

    public AttendanceRecord getValidates() { return validates; }
    public void setValidates(AttendanceRecord validates) { this.validates = validates; }

    public int getId() { return id; }
    public LocalDate getScheduleDate() { return ScheduleDate; }
    public ReservationStatus getStatus() { return status; }
    
}
