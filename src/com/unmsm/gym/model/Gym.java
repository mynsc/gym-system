package src.com.unmsm.gym.model;

import java.util.Map;

public class Gym {
    private String name;
    private int maxCapacityPerBlock;
    private Map<String, ScheduleBlock> scheduleBlocks;

    public Gym(String name, int maxCapacityPerBlock) {
        this.name = name;
        this.maxCapacityPerBlock = maxCapacityPerBlock;
    }

    public void notifiHighOccupancy() {
        
    }

    public ScheduleBlock getBlock(String time) {
        return null;
    }


}       