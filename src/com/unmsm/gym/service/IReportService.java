package src.com.unmsm.gym.service;

import java.util.List;

import src.com.unmsm.gym.models.AttendanceRecord;
import src.com.unmsm.gym.models.Persona;

public interface IReportService {
    void generateActiveUsersReport(List<Persona> users);
    void generateUsageFrequency(List<AttendanceRecord> records);
}