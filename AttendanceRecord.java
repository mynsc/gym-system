import java.time.LocalDateTime;


public class AttendanceRecord {
    private LocalDateTime checkInTime;
    private LocalDateTime checkOutTime;


    public AttendanceRecord(LocalDateTime checkInTime) {
        this.checkInTime = checkInTime;
    }

    public void recordExit(){
        
    }

    
}
