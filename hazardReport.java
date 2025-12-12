import java.time.LocalDateTime;

/** To send a non-emergency hazard report to the admins for review */
public class hazardReport {
    private static int nextReportId = 1000;

    private final String reportId;
    private final String reporterEmail;
    private final String reporterName;
    private final CampusLocations location;
    private final HazardType hazardType;
    private final String description;
    private final LocalDateTime timestamp;
    private String status;                  //status can be: PENDING, IN_REVIEW, RESOLVED, ESCALATED

    /**
     * Constructor for HazardReport
     * @param reportId -> unique report id for each report submitted
     * @param reporterEmail -> email of the studentt/user who submitted the report
     * @param reporterName -> firstname of the student who submitted the report
     * @param location -> location of the hazard
     * @param hazardType -> Type of the hazard, initialized in the HazardType enum
     * @param description -> user-entered description of the hazard
     * @param timestamp -> time when the report was submitted
     * @param status -> status of the report
     */
     public hazardReport(String reporterEmail, String reporterName, CampusLocations location, HazardType hazardType, String description) {
        this.reportId = "HR-" + nextReportId++;
        this.reporterEmail = reporterEmail;
        this.reporterName = reporterName;
        this.location = location;
        this.hazardType = hazardType;
        this.description = description;
        this.timestamp = LocalDateTime.now();
        this.status = "PENDING";
     }

     //Getters (read-only) 
     public String getReportID() { return reportId; }
     public String getReporterEmail() { return reporterEmail; }
     public String getReporterName() { return reporterName; }
     public CampusLocations getLocation() { return location; }
     public HazardType getHazardType() { return hazardType; }
     public String getDescription() { return description; }
     public LocalDateTime getTimeStamp() { return timestamp; } 
     public String getStatus() { return status; }

     //Setter only for admin
     public void setStatus (String status) {
        this.status = status;
     }

     @Override
     public String toString() {
        return String.format("[Report %s] \n Type: %s \n Location: %s \n Status: %s \n Time: %s \n", 
                                    reportId, hazardType, location, status, timestamp.toLocalTime().withNano(0) );
     }
}
