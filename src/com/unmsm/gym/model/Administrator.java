package src.com.unmsm.gym.model;

public class Administrator extends User {
    public Administrator(int id, String username, String password, String name, String studentCode) {
        super(id, username, password, name, studentCode);
    }

    public void overridePenalty(User user) {

    }

    // falta añadir parámetro Gym gym
    public void manageCapacity(int newCapacity) {

    }

    @Override
    public boolean canAccess() {
        return true;
    }
}