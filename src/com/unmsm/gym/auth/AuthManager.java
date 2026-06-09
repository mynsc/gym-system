package src.com.unmsm.gym.auth;

import java.util.List;

import src.com.unmsm.gym.model.Persona;

public class AuthManager {
    private List<Persona> registeredUsers;

    public AuthManager(List<Persona> registeredUsers) {
        this.registeredUsers = registeredUsers;
    }

    public Persona login(String username, String password) {
        for (Persona user : registeredUsers) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }

        return null;
    }

    public void registerUser(Persona user) {
        if (user != null) {
            registeredUsers.add(user);
        }
    }
}
