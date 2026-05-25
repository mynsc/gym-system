public class GymProfile {
    private String faculty;
    private String major;
    private String currentCycle;
    private boolean isActiveSemester;

    public void updateAcademicData() {

    }

    public String getFaculty() { return faculty; }
    public void setFaculty(String faculty) { this.faculty = faculty; }

    public String getMajor() { return major; }
    public void setMajor(String major) { this.major = major; }

    public String getCurrentCycle() { return currentCycle; }
    public void setCurrentCycle(String currentCycle) { this.currentCycle = currentCycle; }

    public boolean isActiveSemester() { return isActiveSemester; }
    public void setActiveSemester(boolean activeSemester) { this.isActiveSemester = activeSemester; }
}