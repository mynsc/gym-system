package src.com.unmsm.gym.models;

import java.util.ArrayList;
import java.util.List;

public class Rutina {
    private int id;
    private String name;
    private String objective;
    private String muscleGroup;
    private double weightKg;
    private int bmi;
    private List<RoutineDetail> contains;

    public Rutina(int id, String name, String muscleGroup) {
        this.id = id;
        this.name = name;
        this.muscleGroup = muscleGroup;
        this.contains = new ArrayList<>();
    }

    public Rutina(int id, String name, String objective, String muscleGroup, double weightKg, int bmi) {
        this(id, name, muscleGroup);
        this.objective = objective;
        this.weightKg = weightKg;
        this.bmi = bmi;
    }

    public void addDetail(RoutineDetail detail) {
        if (detail != null) {
            contains.add(detail);
            detail.setRefersTo(this);
        }
    }

    public String getDetails() {
        StringBuilder details = new StringBuilder();
        details.append("Rutina #").append(id)
            .append(": ").append(name)
            .append(" | Grupo muscular: ").append(muscleGroup);

        if (objective != null && !objective.isBlank()) {
            details.append(" | Objetivo: ").append(objective);
        }

        if (weightKg > 0) {
            details.append(" | Peso: ").append(weightKg).append(" kg");
        }

        if (bmi > 0) {
            details.append(" | IMC: ").append(bmi);
        }

        details.append(" | Ejercicios: ").append(contains.size());
        return details.toString();
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getObjective() { return objective; }
    public void setObjective(String objective) { this.objective = objective; }

    public String getMuscleGroup() { return muscleGroup; }
    public void setMuscleGroup(String muscleGroup) { this.muscleGroup = muscleGroup; }

    public double getWeightKg() { return weightKg; }
    public void setWeightKg(double weightKg) { this.weightKg = weightKg; }

    public int getBmi() { return bmi; }
    public void setBmi(int bmi) { this.bmi = bmi; }

    public List<RoutineDetail> getContains() { return contains; }
}
