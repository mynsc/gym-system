package src.com.unmsm.gym.model;

public class Estudiante extends Persona {
    public Estudiante(int id, String username, String password, String name, String studentCode) {
        super(id, username, password, name, studentCode);
    }

    @Override
    public boolean canAccess() {
        return !hasActivePenalty;
    }
}