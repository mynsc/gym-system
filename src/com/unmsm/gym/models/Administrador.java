package src.com.unmsm.gym.models;

public class Administrador extends Persona {
    public Administrador(int id, String username, String password, String name, String studentCode) {
        super(id, username, password, name, studentCode);
    }

    public void overridePenalty(Persona user) {

    }

    public void manageCapacity(Gimnasio gym, int newCapacity) {

    }

    @Override
    public boolean canAccess() {
        return true;
    }
}