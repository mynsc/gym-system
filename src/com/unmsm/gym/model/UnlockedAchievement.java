package src.com.unmsm.gym.model;

import java.time.LocalDateTime;

public class UnlockedAchievement {
    private LocalDateTime unlockDate;
    private Achievement unlocks;



    public UnlockedAchievement(LocalDateTime unlockDate) {
        this(unlockDate, null);
    }

    public UnlockedAchievement(LocalDateTime unlockDate, Achievement unlocks) {
        this.unlockDate = unlockDate;
        this.unlocks = unlocks;
    }

    public void displayNotification() {
        
    }

    public Achievement getUnlocks() { return unlocks; }
    public void setUnlocks(Achievement unlocks) { this.unlocks = unlocks; }
    
}
