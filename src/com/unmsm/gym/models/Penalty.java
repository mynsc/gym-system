package src.com.unmsm.gym.models;

import java.time.LocalDate;

public class Penalty {
    private LocalDate startDate;
    private LocalDate endDate;
    private String reason;
    private boolean isActive;

    public Penalty(LocalDate startDate, LocalDate endDate, String reason) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.reason = reason;
        this.isActive = true;
    }

    public void applyPenalty() {
        
    }
    public void revokePenalty() {
        
    }
    
}
