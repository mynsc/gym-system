package src.com.unmsm.gym.models;

public class Atleta extends Persona {
    private String sport;
    private String level;
    private int trainingHours;

    public Atleta(int id, String username, String password, String name, String studentCode, String sport, String level, int trainingHours) {
        super(id, username, password, name, studentCode);

        this.sport = sport;
        this.level = level;
        this.trainingHours = trainingHours;
    }

    public void train() {
        trainingHours++;
    }

    public void complete() {
        if (trainingHours > 0) {
            hasGamification.AwardPoints(trainingHours * 5);
            trainingHours = 0;
        }
    }

    @Override
    public boolean canAccess() {
        return !hasActivePenalty;
    }

    public String getSport() { return sport; }
    public void setSport(String sport) { this.sport = sport; }

    public String getLevel() { return level; }
    public void setLevel(String level) { this.level = level; }

    public int getTrainingHours() { return trainingHours; }
    public void setTrainingHours(int trainingHours) { this.trainingHours = trainingHours; }
}