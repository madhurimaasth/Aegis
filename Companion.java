import java.util.Scanner;

/**
 * Represents a companion who walks students to different destinations
 * Inherits the User class
 */
public class Companion extends User{
    Scanner input = new Scanner(System.in);

    private  boolean isApproved;
    private boolean isTrained;
    private boolean isAvailable;
    private int walksCompleted;
    private SafetyRequest currentRequest;

    /**
     * @param isApproved -> a companion needs to be approved by the UTA PD
     * @param isTrained -> a companion needs to complete a self-defense training
     * @param isAvailable -> can be toggled on and off; companion online or offline
     * @param numberOfReports -> total number of reports submitted by the user
     * @param currentRequest -> the most recent request for the companion
     */

    /**
     * Constructor for class Companion
     * @param email -> email must be @mavs.uta.edu or @uta.edu; used as a unique identifier
     * @param password -> unique password set by the user and stored in a file
     * @param MAVID -> unique MAV ID of every User (can be used as a key)
     * @param firstname -> first name of the user, entered by user
     * @param lastname -> last name of the user, entered by user
     * @param gender -> the user's self-declared gender
     */
    public Companion(String email, String password, String MAVID, String firstname, String lastname, String gender) {
        super(email, password, MAVID, firstname, lastname, gender);
        //Set everything as false until approved by admins
        this.isApproved = false;
        this.isTrained = false;
        this.isAvailable = false;
        this.walksCompleted = 0;
     }

     //getters and setters
     public boolean isApproved() { return isApproved; }
     public boolean isTrained() { return isTrained; }
     public boolean isAvailable() { return isAvailable; }
     public int getWalksCompleted() { return walksCompleted; }
     public SafetyRequest getCurrentRequest() { return currentRequest; }

     //can only be set by the admins
     public void setApproved(boolean isApproved) {
        this.isApproved = isApproved;
        System.out.println(getFirstname() + "'s approval status has been set to: " + (isApproved ? "APPROVED" : "DENIED"));
     }
     public void setTrained(boolean isTrained) {
        this.isTrained = isTrained;
        System.out.println(getFirstname() + "'s training status has been set to: " + (isTrained ? "COMPLETE" : "INCOMPLETE"));
     }
     public void setAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
        System.out.println(getFirstname() + " is now " + (isAvailable ? "AVAILABLE" : "UNAVAILABLE"));
     }
     public void setWalksCompleted(int walksCompleted) {
      this.walksCompleted = walksCompleted;
     }
     public void incrementWalksCompleted() {
        this.walksCompleted++;
        System.out.println(getFirstname() + " has completed one more walk! Total walks completed: " + this.walksCompleted );
     }
     public void setCurrentRequest(SafetyRequest request) {
      this.currentRequest = request;
     }

     @Override
     public void viewDashboard() {
        System.out.println("*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*");
        System.out.println("*                     Welcome to Aegis, " + getFirstname() + " " + getLastname() + "!                   *");
        System.out.println("*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*\n");
        System.out.println("   Which action would you like to perform?");
        System.out.println("1. View your status");
        System.out.println("2. Change Availability");
        System.out.println("3. View New Requests");
        System.out.println("4. Emergency Intervention");
        System.out.println("5. Exit");
        chooseAction();
      }

      @Override
      public void chooseAction() {
        System.out.println("Enter your choice (1-5) ");
        String actionChoiceStr = input.nextLine().trim();
        int actionChoice = Integer.parseInt(actionChoiceStr);

        switch(actionChoice){
            case 1: viewCompStatus();       break;
            case 2: ChangeAvailability();   break;
            case 3: ViewRequests();         break;
            case 4: EmergencyIntervention();break;
            case 5: System.exit(0);  break;
            default: 
            { System.out.println("Invalid choice."); chooseAction(); break;}
        }
      }

      /** To view the current status of the companion */
      public void viewCompStatus() {
         System.out.println("Approval Status: " + (isApproved ? "APPROVED" : "PENDING/DENIED"));
         System.out.println("Training Status: " + (isTrained ? "TRAINED" : "NOT TRAINED"));
         System.out.println("Availability: " + (isAvailable ? "AVAILABLE" : "NOT AVAILABLE"));
         System.out.println("Walks Completed: " + walksCompleted);
         System.out.println("\n\n");
         viewDashboard();
      }

      /** To toggle the availability on/off as desired by the companion*/
      public void ChangeAvailability() {
         System.out.println("Your current availability is: " + isAvailable);
         System.out.println("What would you like to set your availability as? (available/unavailable)");
         String Answer = input.nextLine().trim().toLowerCase();
         if(Answer.equals("available")) { setAvailable(Boolean.parseBoolean(Answer)); }
         else if(Answer.equals("unavailable")) { setAvailable(Boolean.parseBoolean(Answer)); }
         else { System.out.println(" Incorrect choice. Try Again."); ChangeAvailability(); }
         System.out.println("Availability status has been set to: " + (isApproved ? "AVAILABLE" : "UNAVAILABLE"));
         viewDashboard();
      }

      /** To view incoming companion-walk requests */
      public void ViewRequests() { 
         if(currentRequest == null) {
            System.out.println("No active requests at the moment.");
            viewDashboard();
         } else {
            System.out.println("Request ID: " + currentRequest.getRequestID());
            System.out.println(currentRequest.getRequesterName() + 
                     " is requesting a walk from " + currentRequest.getStartLocation() + 
                     " to " + currentRequest.getDestination());
         }
      }
      
      @Override
      public void EmergencyIntervention() {
         System.out.println("\n *-*-*-*-*-*-* Companion Escalation to Admin*-*-*-*-*-*-*");
         System.out.println("Admin has been notified for assitance. Please follow protocol.");
         System.out.println("*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*");
         System.out.println("\n\n");
         viewDashboard();
      }    
}
