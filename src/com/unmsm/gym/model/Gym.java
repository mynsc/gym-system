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

    public Gym(String name, int maxCapacityPerBlock, Map<String, ScheduleBlock> scheduleBlocks) {
        this.name = name;
        this.maxCapacityPerBlock = maxCapacityPerBlock;
        this.scheduleBlocks = scheduleBlocks != null ? scheduleBlocks : new HashMap<>();
    }

    public void notifiHighOccupancy() {
        
    }

    public ScheduleBlock getBlock(String time) {
        return scheduleBlocks.get(time);
    }

    public Map<String, ScheduleBlock> getScheduleBlocks() { return scheduleBlocks; }

    public void addScheduleBlock(String startTime, ScheduleBlock block) {
        if (startTime != null && block != null) {
            scheduleBlocks.put(startTime, block);
        }
    }

    public int getMaxCapacityPerBlock() { return maxCapacityPerBlock; }
    public void setMaxCapacityPerBlock(int maxCapacityPerBlock) { this.maxCapacityPerBlock = maxCapacityPerBlock; }


}       