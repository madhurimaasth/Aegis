// For a student to request a safety companion

import java.time.LocalDateTime;

public class SafetyRequest {
    private static int nextRequestID = 5000;

    private final String requestId;
    private final String requesterEmail;
    private final String requesterName;
    private final String requestedGender;
    private final CampusLocations startLocation;
    private final CampusLocations destination;
    private final LocalDateTime requestTime;

    private String assignedCompanionUserEmail;
    private String status;

    /**
     * Constructor for SafetyRequest class
     * @param requesId-> the unique number of request been made for a companion
     * @param requesterEmail -> email of the student who reuqested a companion
     * @param requesterName -> name of the student who submitted a request
     * @param requestedGender -> M or F for companion, as requested by the student
     * @param startLocation -> where the student wants to begin from
     * @param destination -> where the student want to go to
     * @param requestTime -> time when the request was made
     * @param status -> status of the request
     * @param assignedCompanionUserEmail -> email of the assigned companion
     */

     public SafetyRequest(String requesterEmail, String requesterName, String requestedGender, CampusLocations startLocation, CampusLocations destination) {
        this.requestId = "SR-" + nextRequestID++;
        this.requesterEmail = requesterEmail;
        this.requesterName = requesterName;
        this.requestedGender = requestedGender;
        this.startLocation = startLocation;
        this.destination = destination;
        this.requestTime = LocalDateTime.now();
        this.status = "PENDING";
        this.assignedCompanionUserEmail = null;
     }

     //Getters and setters
     public String getRequestID() { return requestId; }
     public String getRequesterEmail() { return requesterEmail; }
     public String getRequesterName() { return requesterName; }
     public String getRequestedGender() {return requestedGender; }
     public CampusLocations getStartLocation() { return startLocation; }
     public CampusLocations getDestination() { return destination; }
     public String getStatus() { return status; }
     public String getAssignedCompanionEmail() { return assignedCompanionUserEmail; }
     public LocalDateTime getRequestTime() { return requestTime; }

     //Setter
     public void setStatus(String status) {
        this.status = status;
     }
     public void assignCompanion(String CompanionEmail) {
        this.assignedCompanionUserEmail = CompanionEmail;
        this.status = "MATCHED";
     }

     @Override
     public String toString() {
        return String.format("[Request %s] From: %s (%s) \n Start time: %s \n Status: %s \n Assigned: %s \n",
                 requestId, requesterName, requesterEmail, startLocation, status, assignedCompanionUserEmail == null ? "None" : assignedCompanionUserEmail);
     }
} 
