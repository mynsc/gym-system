package src.com.unmsm.gym.models;

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

    public int getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(int totalPoints) {
        this.totalPoints = totalPoints;
    }

    public int getCurrentStreak() {
        return currentStreak;
    }

    public void setCurrentStreak(int currentStreak) {
        this.currentStreak = currentStreak;
    }

    public LocalDate getLastAttendanceDate() {
        return lastAttendanceDate;
    }

    public void setLastAttendanceDate(LocalDate lastAttendanceDate) {
        this.lastAttendanceDate = lastAttendanceDate;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setEarns(List<UnlockedAchievement> earns) {
        this.earns = earns;
    }

}
