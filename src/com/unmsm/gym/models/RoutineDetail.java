package src.com.unmsm.gym.models;

public class RoutineDetail {
    private int sets;
    private int reps;
    private int restTimeSeconds;
    private Rutina refersTo;

    public RoutineDetail(int sets, int reps, int restTimeSeconds, Rutina refersTo) {
        this.sets = sets;
        this.reps = reps;
        this.restTimeSeconds = restTimeSeconds;
        this.refersTo = refersTo;
    }

    public void updateProgress() {

    }

    public int getSets() { return sets; }
    public void setSets(int sets) { this.sets = sets; }

    public int getReps() { return reps; }
    public void setReps(int reps) { this.reps = reps; }

    public int getRestTimeSeconds() { return restTimeSeconds; }
    public void setRestTimeSeconds(int restTimeSeconds) { this.restTimeSeconds = restTimeSeconds; }

    public void setRefersTo(Rutina refersTo) { this.refersTo = refersTo; }
}
