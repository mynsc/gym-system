import java.util.List;

public interface IReportService {
    void generateActiveUsersReport(List<User> users);
    void generateUsageFrequency(List<AttendanceRecord> records);
}