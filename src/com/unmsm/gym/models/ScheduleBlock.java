package src.com.unmsm.gym.models;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class ScheduleBlock {
    private String startTime;
    private String endTime;
    private List<Reservation> confirmedReservations;
    private Queue<Persona> waitList;
    private int maxCapacity;

    public ScheduleBlock(String startTime, String endTime){
        this(startTime, endTime, 0);
    }

    public ScheduleBlock(String startTime, String endTime, int maxCapacity){
        this.startTime = startTime;
        this.endTime = endTime;
        this.confirmedReservations = new ArrayList<>();
        this.waitList = new ArrayDeque<>();
        this.maxCapacity = maxCapacity;
    }

    public boolean addReservation(Reservation res){
        if (res == null) {
            return false;
        }

        if (maxCapacity > 0 && confirmedReservations.size() >= maxCapacity) {
            return false;
        }

        confirmedReservations.add(res);
        return true;
    }
    public void addToWaitList(Persona user){
        if (user != null && !waitList.contains(user)) {
            waitList.add(user);
        }
    }
    public Persona pollWaitList(){
        return waitList.poll();
    }

    public boolean removeReservation(Reservation res) {
        return confirmedReservations.remove(res);
    }

    public Reservation findReservationByUser(Persona user) {
        if (user == null) {
            return null;
        }

        for (Reservation reservation : confirmedReservations) {
            if (user.equals(reservation.getBookedBy())) {
                return reservation;
            }
        }

        return null;
    }

    public boolean isUserWaiting(Persona user) {
        return user != null && waitList.contains(user);
    }

    public int getConfirmedCount() { return confirmedReservations.size(); }
    public int getWaitListSize() { return waitList.size(); }

    public List<Reservation> getConfirmedReservations() { return confirmedReservations; }
    public Queue<Persona> getWaitList() { return waitList; }

    public int getMaxCapacity() { return maxCapacity; }
    public String getStartTime() { return startTime; }
    public String getEndTime() { return endTime; }



    
}
