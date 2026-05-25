package src.com.unmsm.gym.model;

import java.util.List;
import java.util.Queue;

public class ScheduleBlock {
    private String startTime;
    private String endTime;
    private List<Reservation> confirmedReservations;
    private Queue<User> waitList;

    public ScheduleBlock(String startTime, String endTime){
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public boolean addReservation(Reservation res){
        return true;
    }
    public void addToWaitList(User user){
    
    }
    public User pollWaitList(){
        return null;
    }



    
}
