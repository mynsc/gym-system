package src.com.unmsm.gym.auth;

import java.util.List;

import src.com.unmsm.gym.model.Usuario;

public class AuthManager {
    private List<Usuario> registeredUsers;

    public AuthManager(List<Usuario> registeredUsers) {
        this.registeredUsers = registeredUsers;
    }

    public Usuario login(String username, String password) {
        for (Usuario user : registeredUsers) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }

        return null;
    }

    public void registerUser(Usuario user) {
        if (user != null) {
            registeredUsers.add(user);
        }
    }
}
