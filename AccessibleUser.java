public class AccessibleUser extends User {
    private String disabilityType;

    public AccessibleUser(int id, String username, String password, String name, String studentCode, String disabilityType) {
        super(id, username, password, name, studentCode);

        this.disabilityType = disabilityType;
    }

    public boolean requiresAssistance() {
        return true;
    }

    @Override
    public boolean canAccess() {
        return true;
    }
}