public abstract class User {
    protected int id;
    protected String username;
    protected String password;
    protected String name;
    protected String studentCode;
    protected boolean hasActivePenalty;

    public boolean canAccess() {
        return true;
    }

    public String getDetails() {
        return "hola";
    }
}