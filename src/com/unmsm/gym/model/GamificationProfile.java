package src.com.unmsm.gym.model;

import java.time.LocalDate;

public class GamificationProfile {
    private int totalPoints;
    private int currentStreak;
    private LocalDate lastAttendanceDate;
    private int level;


    public GamificationProfile() {
        this.totalPoints = 0;
        this.currentStreak = 0;
        this.lastAttendanceDate = null;
        this.level = 1;
    }

    public void AwardPoints(int points) {

    }

    public void EvaluateStreak(LocalDate toDate) {
        
    }

}
