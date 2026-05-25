public class AccessibleUser extends User {
    private String disabilityType;

    public boolean requiresAssistance() {
        return true;
    }

    @Override
    public boolean canAccess() {
        return true;
    }
}