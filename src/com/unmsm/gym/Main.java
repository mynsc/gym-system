package src.com.unmsm.gym;

import java.util.ArrayList;
import java.util.List;

import src.com.unmsm.gym.auth.AuthManager;
import src.com.unmsm.gym.model.AccessibleUser;
import src.com.unmsm.gym.model.Administrator;
import src.com.unmsm.gym.model.Athlete;
import src.com.unmsm.gym.model.RegularStudent;
import src.com.unmsm.gym.model.User;
import src.com.unmsm.gym.ui.ConsoleMenu;

public class Main {
    public static void main(String args[]) {
        List<User> users = new ArrayList<>();

        users.add(new RegularStudent(1, "regular", "123456", "Matias", "20210001"));
        users.add(new Athlete(2, "atleta", "123456", "Camila", "20210002", "Natacion", "Intermedio", 8));
        users.add(new AccessibleUser(3, "discap", "123456", "Luis", "20210003", "Visual"));
        users.add(new Administrator(4, "admin", "123456", "Rosa", "20210004"));

        AuthManager authManager = new AuthManager(users);
        ConsoleMenu menu = new ConsoleMenu(authManager);
        menu.start();
    }
}
