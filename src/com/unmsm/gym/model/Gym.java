package src.com.unmsm.gym.model;

import java.util.HashMap;
import java.util.Map;

public class Gym {
    private String name;
    private int maxCapacityPerBlock;
    private Map<String, ScheduleBlock> scheduleBlocks;

    public Gym(String name, int maxCapacityPerBlock) {
        this.name = name;
        this.maxCapacityPerBlock = maxCapacityPerBlock;
        this.scheduleBlocks = new HashMap<>();
    }

    public void notifiHighOccupancy() {
        
    }

    public ScheduleBlock getBlock(String time) {
        return scheduleBlocks.get(time);
    }


}       