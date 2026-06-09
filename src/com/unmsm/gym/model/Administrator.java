package src.com.unmsm.gym.model;

public class Administrator extends Persona {
    public Administrator(int id, String username, String password, String name, String studentCode) {
        super(id, username, password, name, studentCode);
    }

    public void overridePenalty(Persona user) {

    }

    public void manageCapacity(Gym gym, int newCapacity) {

    }

    @Override
    public boolean canAccess() {
        return true;
    }
}