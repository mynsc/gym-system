import java.util.ArrayList;
import java.util.List;

public class Routine {
    private String name;
    private String objective;
    private double weightKg;
    private int bmi;
    private List<RoutineDetail> contains;

    public Routine(String name, String objective, double weightKg, int bmi) {
        this.name = name;
        this.objective = objective;
        this.weightKg = weightKg;
        this.bmi = bmi;
        this.contains = new ArrayList<>();
    }

    public void addDetail(RoutineDetail detail) {
        
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getObjective() { return objective; }
    public void setObjective(String objective) { this.objective = objective; }

    public double getWeightKg() { return weightKg; }
    public void setWeightKg(double weightKg) { this.weightKg = weightKg; }

    public int getBmi() { return bmi; }
    public void setBmi(int bmi) { this.bmi = bmi; }

    public List<RoutineDetail> getContains() { return contains; }
}
