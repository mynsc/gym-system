package src.com.unmsm.gym.models;

public class Exercise {
    private int id;
    private String name;
    private String muscleGroup;

    public Exercise(int id, String name, String muscleGroup) {
        this.id = id;
        this.name = name;
        this.muscleGroup = muscleGroup;
    }
    
    public String getDetails() {
        return "hola";
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getMuscleGroup() { return muscleGroup; }
    public void setMuscleGroup(String muscleGroup) { this.muscleGroup = muscleGroup; }
}
