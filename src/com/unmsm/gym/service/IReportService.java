package src.com.unmsm.gym.service;

import java.util.List;

import src.com.unmsm.gym.model.AttendanceRecord;
import src.com.unmsm.gym.model.Usuario;

public interface IReportService {
    void generateActiveUsersReport(List<Usuario> users);
    void generateUsageFrequency(List<AttendanceRecord> records);
}