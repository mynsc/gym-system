package src.com.unmsm.gym.service;

import java.util.List;

import src.com.unmsm.gym.model.AttendanceRecord;
import src.com.unmsm.gym.model.User;

public interface IReportService {
    void generateActiveUsersReport(List<User> users);
    void generateUsageFrequency(List<AttendanceRecord> records);
}