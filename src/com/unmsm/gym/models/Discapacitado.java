package src.com.unmsm.gym.models;

public class Discapacitado extends Persona {
    private String disabilityType;

    public Discapacitado(int id, String username, String password, String name, String studentCode, String disabilityType) {
        super(id, username, password, name, studentCode);

        this.disabilityType = disabilityType;
    }

    public boolean requiresAssistance() {
        return true;
    }

    @Override
    public boolean canAccess() {
        return !hasActivePenalty;
    }
}