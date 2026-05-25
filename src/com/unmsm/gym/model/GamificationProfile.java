package src.com.unmsm.gym.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GamificationProfile {
    private int totalPoints;
    private int currentStreak;
    private LocalDate lastAttendanceDate;
    private int level;
    private List<UnlockedAchievement> earns;


    public GamificationProfile() {
        this.totalPoints = 0;
        this.currentStreak = 0;
        this.lastAttendanceDate = null;
        this.level = 1;
        this.earns = new ArrayList<>();
    }

    public void AwardPoints(int points) {

    }

    public void EvaluateStreak(LocalDate toDate) {
        
    }

    public List<UnlockedAchievement> getEarns() { return earns; }

}
