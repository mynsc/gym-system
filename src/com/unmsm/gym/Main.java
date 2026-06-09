package src.com.unmsm.gym;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import src.com.unmsm.gym.auth.AuthManager;
import src.com.unmsm.gym.model.Gym;
import src.com.unmsm.gym.model.AccessibleUser;
import src.com.unmsm.gym.model.Administrator;
import src.com.unmsm.gym.model.Atleta;
import src.com.unmsm.gym.model.RegularStudent;
import src.com.unmsm.gym.model.ScheduleBlock;
import src.com.unmsm.gym.model.Usuario;
import src.com.unmsm.gym.service.ReservationManager;
import src.com.unmsm.gym.ui.ConsoleMenu;

public class Main {
    public static void main(String args[]) {
        List<Usuario> users = new ArrayList<>();

        users.add(new RegularStudent(1, "regular", "123456", "Matias", "20210001"));
        users.add(new Atleta(2, "atleta", "123456", "Camila", "20210002", "Natacion", "Intermedio", 8));
        users.add(new AccessibleUser(3, "discap", "123456", "Luis", "20210003", "Visual"));
        users.add(new Administrator(4, "admin", "123456", "Rosa", "20210004"));

        int maxCapacityPerBlock = 2;
        Map<String, ScheduleBlock> scheduleBlocks = new HashMap<>();
        scheduleBlocks.put("08:00", new ScheduleBlock("08:00", "09:00", maxCapacityPerBlock));
        scheduleBlocks.put("09:00", new ScheduleBlock("09:00", "10:00", maxCapacityPerBlock));
        scheduleBlocks.put("10:00", new ScheduleBlock("10:00", "11:00", maxCapacityPerBlock));
        scheduleBlocks.put("11:00", new ScheduleBlock("11:00", "12:00", maxCapacityPerBlock));

        Gym gym = new Gym("Gimnasio Central", maxCapacityPerBlock, scheduleBlocks);
        ReservationManager reservationManager = new ReservationManager(gym);

        AuthManager authManager = new AuthManager(users);
        ConsoleMenu menu = new ConsoleMenu(authManager, reservationManager);
        menu.start();
    }
}
