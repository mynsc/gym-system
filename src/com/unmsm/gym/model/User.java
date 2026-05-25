package src.com.unmsm.gym.model;

import java.util.ArrayList;
import java.util.List;

import src.com.unmsm.gym.auth.IAccessControl;

public abstract class User implements IAccessControl {
    protected int id;
    protected String username;
    protected String password;
    protected String name;
    protected String studentCode;
    protected boolean hasActivePenalty;
    protected GymProfile hasProfile;
    protected GamificationProfile hasGamification;
    protected List<Penalty> receives;

    public User(int id, String username, String password, String name, String studentCode) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.studentCode = studentCode;
        this.hasActivePenalty = false;
        this.hasProfile = new GymProfile();
        this.hasGamification = new GamificationProfile();
        this.receives = new ArrayList<>();
    }

    @Override
    public abstract boolean canAccess();

    public String getDetails() {
        return "hola";
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getStudentCode() { return studentCode; }
    public void setStudentCode(String studentCode) { this.studentCode = studentCode; }

    public boolean isHasActivePenalty() { return hasActivePenalty; }
    public void setHasActivePenalty(boolean hasActivePenalty) { this.hasActivePenalty = hasActivePenalty; }

    public GymProfile getHasProfile() { return hasProfile; }

    public GamificationProfile getHasGamification() { return hasGamification; }

    public List<Penalty> getReceives() { return receives; }
}