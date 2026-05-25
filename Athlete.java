public class Athlete extends User {
    private String sport;
    private String level;
    private int trainingHours;

    public Athlete(int id, String username, String password, String name, String studentCode, String sport, String level, int trainingHours) {
        super(id, username, password, name, studentCode);

        this.sport = sport;
        this.level = level;
        this.trainingHours = trainingHours;
    }

    public void train() {

    }

    public void complete() {

    }

    @Override
    public boolean canAccess() {
        return true;
    }
}