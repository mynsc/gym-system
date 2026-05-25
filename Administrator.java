public class Administrator extends User {
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
