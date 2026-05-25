public class Athlete extends User {
    private String sport;
    private String level;
    private int trainingHours;

    public void train() {

    }

    public void complete() {

    }

    @Override
    public boolean canAccess() {
        return true;
    }
}