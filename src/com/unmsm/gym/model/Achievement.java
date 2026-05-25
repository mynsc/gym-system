package src.com.unmsm.gym.model;

public class Achievement {
    private int id;
    private String name;
    private String description;
    private int requiredPoints;


    public Achievement(int id, String name, String description, int requiredPoints) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.requiredPoints = requiredPoints;
    }

    public boolean isCriteriaMet(int points) {
        return true;
    }
    
}
