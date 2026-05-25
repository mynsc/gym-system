public class RegularStudent extends User {
    public RegularStudent(int id, String username, String password, String name, String studentCode) {
        super(id, username, password, name, studentCode);
    }

    @Override
    public boolean canAccess() {
        return true;
    }
}