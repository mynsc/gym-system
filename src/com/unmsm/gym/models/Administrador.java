package src.com.unmsm.gym.models;

public class Administrador extends Persona {
    public Administrador(int id, String username, String password, String name, String studentCode) {
        super(id, username, password, name, studentCode);
    }

    public void overridePenalty(Persona user) {
        if (user != null) {
            user.clearPenalties();
        }
    }

    public int manageCapacity(int newCapacity) {
        return newCapacity > 0 ? newCapacity : 0;
    }

    @Override
    public boolean canAccess() {
        return true;
    }
}