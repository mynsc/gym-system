package src.com.unmsm.gym.auth;

import java.util.List;

import src.com.unmsm.gym.model.User;

public class AuthManager {
    private List<User> registeredUsers;

    public AuthManager(List<User> registeredUsers) {
        this.registeredUsers = registeredUsers;
    }

    public User login(String username, String password) {
        return null;
    }

    public void registerUser(User user) {
        
    }
}
